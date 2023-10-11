package com.invitationcode.generator.domain.orders.service;

import com.invitationcode.generator.domain.member.dao.MemberDao;
import com.invitationcode.generator.domain.member.dao.MemberHasCouponDao;
import com.invitationcode.generator.domain.member.domain.Member;
import com.invitationcode.generator.domain.member.domain.MemberHasCoupon;
import com.invitationcode.generator.domain.orders.domain.Orders;
import com.invitationcode.generator.domain.orders.domain.OrdersTotalMoney;
import com.invitationcode.generator.domain.orders.domain.ShippingInfo;
import com.invitationcode.generator.domain.orders.dto.CouponUsedRequest;
import com.invitationcode.generator.domain.orders.dto.OrdersPurchaseRequest;
import com.invitationcode.generator.domain.orders.dto.ProductPurchaseRequest;
import com.invitationcode.generator.domain.orders.repository.OrdersRepository;
import com.invitationcode.generator.domain.product.dao.ProductDao;
import com.invitationcode.generator.domain.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Service
@Transactional
@RequiredArgsConstructor
public class OrdersService {

    private final MemberDao memberDao;
    private final ProductDao productDao;
    private final MemberHasCouponDao memberHasCouponDao;
    private final OrdersRepository ordersRepository;

    public Long purchase(OrdersPurchaseRequest request) {
        Member member = memberDao.findByIdx(request.getMemberIdx());
        OrdersTotalMoney ordersDiscountMoney = ordersDiscountMoney(member.getIdx(), request.getCouponUsedList());
        OrdersTotalMoney ordersDepositMoney = new OrdersTotalMoney(request.getDepositPrice());
        OrdersTotalMoney ordersTotalMoney = ordersDepositMoney.minus(ordersDiscountMoney.getTotalPrice());

        ShippingInfo shippingInfo = ShippingInfo.builder()
                .zipcode(request.getZipcode())
                .address(request.getAddress())
                .addressDetail(request.getAddressDetail())
                .message(request.getMessage())
                .build();

        Orders orders = Orders.builder()
                .member(member)
                .ordersTotalMoney(ordersTotalMoney)
                .shippingInfo(shippingInfo)
                .build();

        OrdersTotalMoney purchaseTotalMoney = purchaseTotalMoney(orders, request.getProductPurchaseList());
        orders.statusChangeByPurchase(purchaseTotalMoney);
        ordersRepository.save(orders);
        return orders.getIdx();
    }

    /**
     * @param orders                 주문자
     * @param productPurchaseRequest 주문하고자하는 상품의 번호와 갯수
     * @return                       총 구매 금액
     * @description 클라이언트가 구입하고자 하는 상품을 주문 목록에 등록하고, 상품의 총 금액을 반환하는 메서드입니다.
     */
    private OrdersTotalMoney purchaseTotalMoney(Orders orders, List<ProductPurchaseRequest> productPurchaseRequest) {
        OrdersTotalMoney result = OrdersTotalMoney.ZERO;
        for (ProductPurchaseRequest request : productPurchaseRequest) {
            Product product = productDao.findByIdx(request.getProductIdx());
            product.decreaseStock(request.getProductBuyQuantity());
            orders.addOrdersLine(product.getIdx(), product.getMoney(), product.getName(), request.getProductBuyQuantity());
            BigDecimal purchaseTotalMoney = orders.purchaseTotalMoney(product.getMoney(), request.getProductBuyQuantity());
            result = result.plus(purchaseTotalMoney);
        }
        return result;
    }

    /**
     * @param memberIdx         클라이언트의 아이디
     * @param couponUsedRequest 내가 가지고 있는 쿠폰의 아이디와 사용하고자 하는 쿠폰의 갯수
     * @return                  총 할인 금액
     * @description 클라이언트로부터 사용하고자 하는 쿠폰을 입력받아 해당 쿠폰의 유효성을 검증하고 각 쿠폰의 할인 금액을 누적하여 반환하는 메서드입니다.
     */
    private OrdersTotalMoney ordersDiscountMoney(long memberIdx, List<CouponUsedRequest> couponUsedRequest) {
        Map<Long, Integer> usedCouponGroup = usedCouponGroup(couponUsedRequest);
        List<MemberHasCoupon> memberHasCoupons = memberHasCouponDao.findByMemberIdx(memberIdx, usedCouponGroup.keySet());

        OrdersTotalMoney result = OrdersTotalMoney.ZERO;
        for (MemberHasCoupon memberHasCoupon : memberHasCoupons) {
            if (usedCouponGroup.containsKey(memberHasCoupon.getIdx())) {
                int usedCouponStock = usedCouponGroup.get(memberHasCoupon.getIdx());
                memberHasCoupon.usedCoupon(usedCouponStock);
                result = result.plus(memberHasCoupon.getCouponSalePrice());
            }
        }
        return result;
    }

    private Map<Long, Integer> usedCouponGroup(List<CouponUsedRequest> couponUsedRequest) {
        if (couponUsedRequest == null) {
            return new HashMap<>();
        }
        return couponUsedRequest.stream()
                .collect(toMap(
                        CouponUsedRequest::getHasCouponIdx,
                        CouponUsedRequest::getUsedStock,
                        (oldVal, newVal) -> newVal
                ));
    }
}
