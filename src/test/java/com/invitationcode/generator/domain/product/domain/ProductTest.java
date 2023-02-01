package com.invitationcode.generator.domain.product.domain;

import com.invitationcode.generator.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class ProductTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void 비정상적인_상품_생성() {

        // then
        assertThatThrownBy(() -> {
            // when
            Product product = Product.builder()
                    .money(150050)
                    .name("MacBook")
                    .stock(-1)
                    .build();
        }).isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void 정상적인_상품_생성() {

        // given
        Product product = Product.builder()
                .money(150050)
                .name("MacBook")
                .stock(50)
                .build();

        // when
        Product save = productRepository.save(product);

        // then
        assertThat(save.getIdx()).isEqualTo(1L);
    }

}