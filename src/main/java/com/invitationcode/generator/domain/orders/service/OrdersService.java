package com.invitationcode.generator.domain.orders.service;

import com.invitationcode.generator.domain.member.dao.MemberDao;
import com.invitationcode.generator.domain.member.dao.MemberHasCouponDao;
import com.invitationcode.generator.domain.member.domain.Member;
import com.invitationcode.generator.domain.member.domain.MemberHasCoupon;
import com.invitationcode.generator.domain.orders.domain.Orders;
import com.invitationcode.generator.domain.orders.domain.OrdersMoney;
import com.invitationcode.generator.domain.orders.domain.ShippingInfo;
import com.invitationcode.generator.domain.orders.dto.CouponUsedRequestDto;
import com.invitationcode.generator.domain.orders.dto.OrdersPurchaseRequestDto;
import com.invitationcode.generator.domain.orders.dto.ProductPurchaseListRequestDto;
import com.invitationcode.generator.domain.orders.repository.OrdersRepository;
import com.invitationcode.generator.domain.orderslist.domain.PurchaseMoney;
import com.invitationcode.generator.domain.product.dao.ProductDao;
import com.invitationcode.generator.domain.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Long purchase(OrdersPurchaseRequestDto requestDto) {
        Member member = memberDao.findByIdx(requestDto.getMemberIdx());
        OrdersMoney ordersDiscountMoney = usedCouponAndReturnOrdersDiscountMoney(member.getIdx(), requestDto.getCouponUsedList());

        ShippingInfo shippingInfo = ShippingInfo.builder()
                .zipcode(requestDto.getZipcode())
                .address(requestDto.getAddress())
                .addressDetail(requestDto.getAddressDetail())
                .message(requestDto.getMessage())
                .build();

        Orders orders = Orders.builder()
                .member(member)
                .depositOrdersMoney(new OrdersMoney(requestDto.getDepositPrice()))
                .ordersDiscountMoney(ordersDiscountMoney)
                .shippingInfo(shippingInfo)
                .build();

        OrdersMoney zeroOrdersMoney = OrdersMoney.ZERO;
        for (ProductPurchaseListRequestDto productPurchaseDTO : requestDto.getProductPurchaseList()) {
            Product product = productDao.findByIdx(productPurchaseDTO.getProductIdx());
            PurchaseMoney purchaseMoney = orders.addOrdersLineAndReturnPurchaseTotalMoney(product, productPurchaseDTO.getProductBuyQuantity());
            zeroOrdersMoney = zeroOrdersMoney.plus(purchaseMoney.getPurchaseMoney());
        }

        orders.statusChangeByPurchase(zeroOrdersMoney);
        ordersRepository.save(orders);
        return orders.getIdx();
    }

    /**
     * @param memberIdx            클라이언트의 아이디
     * @param couponUsedRequestDTO 사용하고자 하는 내가 가지고 있는 쿠폰의 아이디와 사용하고자 하는 쿠폰의 갯수
     * @return 총 할인 금액
     * @description 클라이언트로부터 사용하고자 하는 쿠폰을 입력받아 해당 쿠폰의 유효성을 검증하고 각 쿠폰의 할인 금액을 누적하여 반환하는 메서드입니다.
     */
    private OrdersMoney usedCouponAndReturnOrdersDiscountMoney(Long memberIdx, List<CouponUsedRequestDto> couponUsedRequestDTO) {
        Map<Long, Integer> hasCouponIdxAndUsedCouponStockMap = usedCouponAndUsedStockToMap(couponUsedRequestDTO);
        List<MemberHasCoupon> memberHasCoupons = memberHasCouponDao.findByMemberIdx(memberIdx, hasCouponIdxAndUsedCouponStockMap.keySet());

        OrdersMoney ordersDiscountMoney = OrdersMoney.ZERO;
        for (MemberHasCoupon memberHasCoupon : memberHasCoupons) {
            if (hasCouponIdxAndUsedCouponStockMap.containsKey(memberHasCoupon.getIdx())) {
                memberHasCoupon.usedCoupon(hasCouponIdxAndUsedCouponStockMap.get(memberHasCoupon.getIdx()));
                ordersDiscountMoney = ordersDiscountMoney.plus(memberHasCoupon.getCouponSalePrice());
            }
        }
        return ordersDiscountMoney;
    }

    private Map<Long, Integer> usedCouponAndUsedStockToMap(List<CouponUsedRequestDto> couponUsedRequest) {
        return couponUsedRequest.stream()
                .collect(toMap(
                        CouponUsedRequestDto::getHasCouponIdx,
                        CouponUsedRequestDto::getUsedStock
                ));
    }
}
