package com.invitationcode.generator.domain.orders.service;

import com.invitationcode.generator.domain.member.dao.MemberDao;
import com.invitationcode.generator.domain.member.domain.Member;
import com.invitationcode.generator.domain.orders.domain.Money;
import com.invitationcode.generator.domain.orders.domain.Orders;
import com.invitationcode.generator.domain.orders.domain.ShippingInfo;
import com.invitationcode.generator.domain.orders.dto.OrdersPurchaseRequestDto;
import com.invitationcode.generator.domain.orders.dto.ProductPurchaseListRequestDto;
import com.invitationcode.generator.domain.orders.repository.OrdersRepository;
import com.invitationcode.generator.domain.orderslist.domain.PurchaseMoney;
import com.invitationcode.generator.domain.product.dao.ProductDao;
import com.invitationcode.generator.domain.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrdersService {

    private final MemberDao memberDao;
    private final ProductDao productDao;
    private final OrdersRepository ordersRepository;

    public Long purchase(OrdersPurchaseRequestDto requestDto) {
        Member member = memberDao.findByIdx(requestDto.getMemberIdx());

        ShippingInfo shippingInfo = ShippingInfo.builder()
                .zipcode(requestDto.getZipcode())
                .address(requestDto.getAddress())
                .addressDetail(requestDto.getAddressDetail())
                .message(requestDto.getMessage())
                .build();

        Orders orders = Orders.builder()
                .member(member)
                .depositPrice(requestDto.getDepositPrice())
                .shippingInfo(shippingInfo)
                .build();

        Money zeroMoney = Money.ZERO;
        for (ProductPurchaseListRequestDto productPurchaseDTO : requestDto.getProductPurchaseList()) {
            Product product = productDao.findByIdx(productPurchaseDTO.getProductIdx());
            PurchaseMoney purchaseMoney = orders.addOrdersLineAndReturnPurchaseTotalMoney(product, productPurchaseDTO.getProductBuyQuantity());
            zeroMoney = zeroMoney.plus(purchaseMoney.getPurchaseMoney());
        }

        orders.statusChangeByPurchase(zeroMoney);
        ordersRepository.save(orders);
        return orders.getIdx();
    }
}
