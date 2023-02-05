package com.invitationcode.generator.domain.orders.domain;

import com.invitationcode.generator.domain.member.dao.MemberDao;
import com.invitationcode.generator.domain.member.domain.Member;
import com.invitationcode.generator.domain.orders.repository.OrdersRepository;
import com.invitationcode.generator.domain.product.dao.ProductDao;
import com.invitationcode.generator.domain.product.domain.Product;
import com.invitationcode.generator.global.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
class OrdersTest {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private OrdersRepository ordersRepository;

    /*
     고려사항
     1. 상품의 주문 갯수를 차감한다.
       -> 주문 갯수가 상품의 재고보다 많으면 예외가 발생한다. [o]
     2. 입금 금액은 주문 상품의 총 금액을 넘을 수 없다. 넘는다면 예외가 발생한다.
     3. 입금 금액에 따라 주문의 상태가 달라진다.
     */
    @Test
    void 단일_주문생성() {

        // given
        Member member = memberDao.findByIdx(12L);
        Product product = productDao.findByIdx(1L);
        Integer productQuantity = 10;
        ShippingInfo shippingInfo = ShippingInfo.builder()
                .zipcode(123)
                .address("서울")
                .addressDetail("특별시")
                .message("현관문 앞에 놓고가주세요.")
                .build();

        // when
        Orders orders = Orders.builder()
                .member(member)
                .ordersDiscountMoney(new OrdersMoney(10000))
                .shippingInfo(shippingInfo)
                .build();

        orders.addOrdersLineAndReturnPurchaseTotalMoney(product, productQuantity);
        product.decreaseStock(productQuantity);
        Orders save = ordersRepository.save(orders);

        // then
        assertThat(save.getIdx()).isEqualTo(9L);
    }

    @Test
    void 단일_주문생성중_총상품_금액보다_입금금액이_큰_경우() {

        // given
        Member member = memberDao.findByIdx(12L);
        Product product = productDao.findByIdx(1L);
        Integer productQuantity = 10;
        ShippingInfo shippingInfo = ShippingInfo.builder()
                .zipcode(123)
                .address("서울")
                .addressDetail("특별시")
                .message("현관문 앞에 놓고가주세요.")
                .build();

        // then
        assertThatThrownBy(() -> {

            // when
            Orders orders = Orders.builder()
                    .member(member)
                    .ordersDiscountMoney(new OrdersMoney(1000000000))
                    .shippingInfo(shippingInfo)
                    .build();

            orders.addOrdersLineAndReturnPurchaseTotalMoney(product, productQuantity);
            product.decreaseStock(productQuantity);
            Orders save = ordersRepository.save(orders);
        }).isInstanceOf(BusinessException.class);
    }
}