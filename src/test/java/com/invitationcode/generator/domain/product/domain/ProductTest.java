package com.invitationcode.generator.domain.product.domain;

import com.invitationcode.generator.domain.product.dao.ProductDao;
import com.invitationcode.generator.domain.product.repository.ProductRepository;
import com.invitationcode.generator.domain.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class ProductTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductService productService;

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

    @Test
    @Transactional
    void 동시에_재고_차감() throws InterruptedException {
        int threadCount = 10;
        CountDownLatch latch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            CompletableFuture.runAsync(() -> {
                try {
                    productService.decreaseStock(10);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        Product product = productDao.findByIdx(1L);
        assertThat(product.getStock()).isEqualTo(0);
    }

}