package com.invitationcode.generator.domain.product.controller;

import com.invitationcode.generator.domain.product.dto.ProductCreateRequestDto;
import com.invitationcode.generator.domain.product.dto.ProductUpdateRequestDto;
import com.invitationcode.generator.domain.product.service.ProductService;
import com.invitationcode.generator.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.invitationcode.generator.global.response.ResponseStatus.CODE_200;
import static com.invitationcode.generator.global.response.ResponseStatus.CODE_201;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public BaseResponse<Long> create(@Valid @RequestBody ProductCreateRequestDto requestDto) {
        Long response = productService.create(requestDto);
        return new BaseResponse<>(CODE_201, response);
    }

    @PutMapping("/{productIdx}")
    public BaseResponse<Long> update(@PathVariable(value = "productIdx") Long productIdx, @Valid @RequestBody ProductUpdateRequestDto requestDto) {
        Long response = productService.update(productIdx, requestDto);
        return new BaseResponse<>(CODE_200, response);
    }

    @DeleteMapping("/{productIdx}")
    public BaseResponse<Void> delete(@PathVariable(value = "productIdx") Long productIdx) {
        productService.delete(productIdx);
        return new BaseResponse<>(CODE_200);
    }
}
