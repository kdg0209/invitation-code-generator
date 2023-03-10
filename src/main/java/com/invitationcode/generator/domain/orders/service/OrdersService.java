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

        OrdersMoney totalPurchaseOrdersMoney = addOrdersLineAndReturnPurchaseTotalMoney(orders, requestDto.getProductPurchaseList());
        orders.statusChangeByPurchase(totalPurchaseOrdersMoney);
        ordersRepository.save(orders);
        return orders.getIdx();
    }

    /**
     * @param orders                        ?????????
     * @param productPurchaseListRequestDTO ????????????????????? ????????? ????????? ??????
     * @return ??? ?????? ??????
     * @description ?????????????????? ??????????????? ?????? ????????? ?????? ????????? ????????????, ????????? ??? ????????? ???????????? ??????????????????.
     */
    private OrdersMoney addOrdersLineAndReturnPurchaseTotalMoney(Orders orders, List<ProductPurchaseListRequestDto> productPurchaseListRequestDTO) {
        OrdersMoney result = OrdersMoney.ZERO;
        for (ProductPurchaseListRequestDto productPurchaseDTO : productPurchaseListRequestDTO) {
            Product product = productDao.findByIdx(productPurchaseDTO.getProductIdx());
            PurchaseMoney purchaseMoney = orders.addOrdersLineAndReturnPurchaseTotalMoney(product, productPurchaseDTO.getProductBuyQuantity());
            result = result.plus(purchaseMoney.getPurchaseMoney());
        }
        return result;
    }

    /**
     * @param memberIdx            ?????????????????? ?????????
     * @param couponUsedRequestDTO ??????????????? ?????? ?????? ????????? ?????? ????????? ???????????? ??????????????? ?????? ????????? ??????
     * @return ??? ?????? ??????
     * @description ???????????????????????? ??????????????? ?????? ????????? ???????????? ?????? ????????? ???????????? ???????????? ??? ????????? ?????? ????????? ???????????? ???????????? ??????????????????.
     */
    private OrdersMoney usedCouponAndReturnOrdersDiscountMoney(Long memberIdx, List<CouponUsedRequestDto> couponUsedRequestDTO) {
        Map<Long, Integer> hasCouponIdxAndUsedCouponStockMap = usedCouponAndUsedStockToMap(couponUsedRequestDTO);
        List<MemberHasCoupon> memberHasCoupons = memberHasCouponDao.findByMemberIdx(memberIdx, hasCouponIdxAndUsedCouponStockMap.keySet());

        OrdersMoney result = OrdersMoney.ZERO;
        for (MemberHasCoupon memberHasCoupon : memberHasCoupons) {
            if (hasCouponIdxAndUsedCouponStockMap.containsKey(memberHasCoupon.getIdx())) {
                memberHasCoupon.usedCoupon(hasCouponIdxAndUsedCouponStockMap.get(memberHasCoupon.getIdx()));
                result = result.plus(memberHasCoupon.getCouponSalePrice());
            }
        }
        return result;
    }

    private Map<Long, Integer> usedCouponAndUsedStockToMap(List<CouponUsedRequestDto> couponUsedRequest) {
        return couponUsedRequest.stream()
                .collect(toMap(
                        CouponUsedRequestDto::getHasCouponIdx,
                        CouponUsedRequestDto::getUsedStock,
                        (oldVal, newVal) -> newVal
                ));
    }
}
