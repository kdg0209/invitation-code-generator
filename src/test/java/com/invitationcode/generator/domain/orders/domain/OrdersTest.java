package com.invitationcode.generator.domain.orders.domain;

import com.invitationcode.generator.domain.member.domain.Email;
import com.invitationcode.generator.domain.member.domain.Member;
import com.invitationcode.generator.domain.member.domain.Password;
import com.invitationcode.generator.global.exception.BusinessException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.math.BigDecimal;

class OrdersTest {

	private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
	private Member member;

	@BeforeEach
	void setUp() {
		String id = "test";
		Password password = new Password("12345", PASSWORD_ENCODER);
		String name = "홍길동";
		Email email = new Email("test@naver.com");
		String nickName = "홍길동";

		member = Member.builder()
			.id(id)
			.password(password)
			.name(name)
			.email(email)
			.nickName(nickName)
			.build();
	}

	@Test
	void 정상적인_값을_사용하여_객체를_생성할_수_있다() {

		// given
		OrdersTotalMoney ordersTotalMoney = new OrdersTotalMoney(10000L);
		ShippingInfo shippingInfo = createShippingInfo();

		// when
		Orders result = Orders.builder()
			.member(member)
			.ordersTotalMoney(ordersTotalMoney)
			.shippingInfo(shippingInfo)
			.build();

		// then
		assertThat(result).isNotNull();
	}

	@Test
	void 주문을하여_주문목록에_추가할_수_있다() {

		// given
		long productIdx = 1L;
		BigDecimal productMoney = new BigDecimal("10000");
		String productName = "mac book";
		int quantity = 10;

		OrdersTotalMoney ordersTotalMoney = new OrdersTotalMoney(10000L);
		ShippingInfo shippingInfo = createShippingInfo();
		Orders orders = Orders.builder()
			.member(member)
			.ordersTotalMoney(ordersTotalMoney)
			.shippingInfo(shippingInfo)
			.build();

		// when
		orders.addOrdersLine(productIdx, productMoney, productName, quantity);

		// then
		assertThat(orders.getOrdersLines().size()).isEqualTo(1);
	}

	@Test
	void 주문의_구매금액을_총_금액을_입금을하였을_경우_주문의_상태를_입금_완료상태로_변경할_수_있다() {

		// given
		OrdersTotalMoney ordersTotalMoney = new OrdersTotalMoney(10000L);
		ShippingInfo shippingInfo = createShippingInfo();
		Orders orders = Orders.builder()
			.member(member)
			.ordersTotalMoney(ordersTotalMoney)
			.shippingInfo(shippingInfo)
			.build();

		// when
		OrdersTotalMoney totalMoney = new OrdersTotalMoney(10000L);
		orders.statusChangeByPurchase(totalMoney);

		// then
		assertThat(orders.getStatus()).isEqualTo(OrderStatus.PAY_COMPLETED);
	}

	@Test
	void 주문의_구매금액을_총_금액보다_적게_입금을하였을_경우_주문의_상태를_입금_전상태로_변경할_수_있다() {

		// given
		OrdersTotalMoney ordersTotalMoney = new OrdersTotalMoney(10000L);
		ShippingInfo shippingInfo = createShippingInfo();
		Orders orders = Orders.builder()
			.member(member)
			.ordersTotalMoney(ordersTotalMoney)
			.shippingInfo(shippingInfo)
			.build();

		// when
		OrdersTotalMoney totalMoney = new OrdersTotalMoney(5000L);
		orders.statusChangeByPurchase(totalMoney);

		// then
		assertThat(orders.getStatus()).isEqualTo(OrderStatus.PAY_WAITING);
	}

	@Test
	void 주문의_구매금액을_총_금액보다_많이_입금을하였을_경우_예외가_발생한다() {

		// given
		OrdersTotalMoney ordersTotalMoney = new OrdersTotalMoney(10000L);
		ShippingInfo shippingInfo = createShippingInfo();
		Orders orders = Orders.builder()
			.member(member)
			.ordersTotalMoney(ordersTotalMoney)
			.shippingInfo(shippingInfo)
			.build();

		// when && then
		OrdersTotalMoney totalMoney = new OrdersTotalMoney(50000L);
		assertThatThrownBy(() -> orders.statusChangeByPurchase(totalMoney))
			.isInstanceOf(BusinessException.class);
	}

	private ShippingInfo createShippingInfo() {
		Integer zipcode = 100;
		String address = "Seoul";
		String addressDetail = "강남구";
		String message = "배송전 연락바랍니다.";

		return ShippingInfo.builder()
			.zipcode(zipcode)
			.address(address)
			.addressDetail(addressDetail)
			.message(message)
			.build();
	}
}