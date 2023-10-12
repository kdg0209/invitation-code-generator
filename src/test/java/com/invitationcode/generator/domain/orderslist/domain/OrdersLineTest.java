package com.invitationcode.generator.domain.orderslist.domain;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.invitationcode.generator.domain.member.domain.Email;
import com.invitationcode.generator.domain.member.domain.Member;
import com.invitationcode.generator.domain.member.domain.Password;
import com.invitationcode.generator.domain.orders.domain.Orders;
import com.invitationcode.generator.domain.orders.domain.OrdersTotalMoney;
import com.invitationcode.generator.domain.orders.domain.ShippingInfo;

class OrdersLineTest {

	private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
	private Orders orders;

	@BeforeEach
	void setUp() {

		String id = "test";
		Password password = new Password("12345", PASSWORD_ENCODER);
		String name = "홍길동";
		Email email = new Email("test@naver.com");
		String nickName = "홍길동";

		Member member = Member.builder()
			.id(id)
			.password(password)
			.name(name)
			.email(email)
			.nickName(nickName)
			.build();

		ShippingInfo shippingInfo = ShippingInfo.builder().build();

		orders = Orders.builder()
			.member(member)
			.ordersTotalMoney(OrdersTotalMoney.ZERO)
			.shippingInfo(shippingInfo)
			.build();
	}

	@Test
	void 정상적인_값을_사용하여_객체를_생성할_수_있다() {

		// given
		Long productIdx = 1L;
		BigDecimal purchaseMoney = new BigDecimal("10000");
		String productName = "mac book";
		int quantity = 1;

		// when
		OrdersLine result = OrdersLine.builder()
			.orders(orders)
			.productIdx(productIdx)
			.productName(productName)
			.purchaseMoney(purchaseMoney)
			.quantity(quantity)
			.build();

		// then
		assertThat(result).isNotNull();
	}
	
	@Test
	void 상품번호가_NULL인_값을_사용하여_객체를_생성하는_경우_예외가_발생한다() {

		// given
		Long productIdx = null;
		BigDecimal purchaseMoney = new BigDecimal("10000");
		String productName = "mac book";
		int quantity = 1;

		// when && then
		assertThatThrownBy(() -> OrdersLine.builder()
			.orders(orders)
			.productIdx(productIdx)
			.productName(productName)
			.purchaseMoney(purchaseMoney)
			.quantity(quantity)
			.build()
		).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void 상품번호가_음수인_값을_사용하여_객체를_생성하는_경우_예외가_발생한다() {

		// given
		Long productIdx = -1L;
		BigDecimal purchaseMoney = new BigDecimal("10000");
		String productName = "mac book";
		int quantity = 1;

		// when && then
		assertThatThrownBy(() -> OrdersLine.builder()
			.orders(orders)
			.productIdx(productIdx)
			.productName(productName)
			.purchaseMoney(purchaseMoney)
			.quantity(quantity)
			.build()
		).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void 상품명이_NULL인_값을_사용하여_객체를_생성하는_경우_예외가_발생한다() {

		// given
		Long productIdx = 1L;
		BigDecimal purchaseMoney = new BigDecimal("10000");
		String productName = null;
		int quantity = 1;

		// when && then
		assertThatThrownBy(() -> OrdersLine.builder()
			.orders(orders)
			.productIdx(productIdx)
			.productName(productName)
			.purchaseMoney(purchaseMoney)
			.quantity(quantity)
			.build()
		).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void 상품명이_공백인_값을_사용하여_객체를_생성하는_경우_예외가_발생한다() {

		// given
		Long productIdx = 1L;
		BigDecimal purchaseMoney = new BigDecimal("10000");
		String productName = "          ";
		int quantity = 1;

		// when && then
		assertThatThrownBy(() -> OrdersLine.builder()
			.orders(orders)
			.productIdx(productIdx)
			.productName(productName)
			.purchaseMoney(purchaseMoney)
			.quantity(quantity)
			.build()
		).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void 상품수량이_음수인_값을_사용하여_객체를_생성하는_경우_예외가_발생한다() {

		// given
		Long productIdx = 1L;
		BigDecimal purchaseMoney = new BigDecimal("10000");
		String productName = "mac book";
		int quantity = -1;

		// when && then
		assertThatThrownBy(() -> OrdersLine.builder()
			.orders(orders)
			.productIdx(productIdx)
			.productName(productName)
			.purchaseMoney(purchaseMoney)
			.quantity(quantity)
			.build()
		).isInstanceOf(IllegalArgumentException.class);
	}
}