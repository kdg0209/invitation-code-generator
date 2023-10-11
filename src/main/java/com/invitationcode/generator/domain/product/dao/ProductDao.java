package com.invitationcode.generator.domain.product.dao;

import com.invitationcode.generator.domain.product.domain.Product;
import com.invitationcode.generator.global.exception.BusinessException;
import com.invitationcode.generator.global.exception.ErrorCode;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.invitationcode.generator.domain.product.domain.QProduct.product;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductDao {

    private final JPAQueryFactory queryFactory;

    public Product findByIdx(long productIdx) {
        Product result = queryFactory
                .selectFrom(product)
                .where(
                        product.idx.eq(productIdx)
                )
                .fetchFirst();

        return Optional.ofNullable(result)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND_EXCEPTION));
    }
}
