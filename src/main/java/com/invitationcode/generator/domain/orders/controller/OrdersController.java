package com.invitationcode.generator.domain.orders.controller;

import com.invitationcode.generator.domain.orders.dto.OrdersPurchaseRequestDto;
import com.invitationcode.generator.domain.orders.service.OrdersService;
import com.invitationcode.generator.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.invitationcode.generator.global.response.ResponseStatus.CODE_201;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;

    @PostMapping
    public BaseResponse<Long> purchase(@Valid @RequestBody OrdersPurchaseRequestDto requestDto) {
        Long response = ordersService.purchase(requestDto);
        return new BaseResponse<>(CODE_201, response);
    }
}
