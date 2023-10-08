package com.invitationcode.generator.domain.product.domain;

import com.invitationcode.generator.global.exception.BusinessException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

    @Test
    void 정상적인_값을_사용하여_상품_객체를_생성할_수_있다() {

        // given
        String name = "mac book";
        int stock = 10;
        Money money = new Money(1000);

        // when
        Product result = new Product(name, stock, money);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("mac book");
        assertThat(result.getStock()).isEqualTo(10);
    }

    @Test
    void 재고가_음수인_값을_사용하여_상품을_만드는경우_예외가_발생한다() {

        // given
        String name = "mac book";
        int stock = -100;
        Money money = new Money(1000);

        // when && then
        assertThatThrownBy(() -> new Product(name, stock, money))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 정수의_재고값을_사용하여_상품의_재고를_업데이트할_수_있다() {

        // given
        String name = "mac book";
        int stock = 10;
        Money money = new Money(1000);
        Product product = new Product(name, stock, money);

        // when
        int updateStock = 100;
        product.updateStock(updateStock);

        // then
        assertThat(product.getStock()).isEqualTo(100);
    }

    @Test
    void 음수의_재고값을_사용하여_상품의_재고를_업데이트_한다면_예외가_발생한다() {

        // given
        String name = "mac book";
        int stock = 10;
        Money money = new Money(1000);
        Product product = new Product(name, stock, money);

        // when && then
        int updateStock = -1000;
        assertThatThrownBy(() -> product.updateStock(updateStock))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 정상적인_값을_사용하여_상품의_이름를_업데이트할_수_있다() {

        // given
        String name = "mac book";
        int stock = 10;
        Money money = new Money(1000);
        Product product = new Product(name, stock, money);

        // when
        String updateName = "mac book update";
        product.updateName(updateName);

        // then
        assertThat(product.getName()).isEqualTo("mac book update");
    }

    @Test
    void 빈_문자열을_사용하여_상품의_이름을_업데이트_하는_경우_예외가_발생한다() {

        // given
        String name = "mac book";
        int stock = 10;
        Money money = new Money(1000);
        Product product = new Product(name, stock, money);

        // when && then
        String updateName = " ";
        assertThatThrownBy(() -> product.updateName(updateName))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void NULL을_사용하여_상품의_이름을_업데이트_하는_경우_예외가_발생한다() {

        // given
        String name = "mac book";
        int stock = 10;
        Money money = new Money(1000);
        Product product = new Product(name, stock, money);

        // when && then
        String updateName = null;
        assertThatThrownBy(() -> product.updateName(updateName))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 정상적인_값을_사용하여_상품의_재고를_감소시킬_수_있다() {

        // given
        String name = "mac book";
        int stock = 10;
        Money money = new Money(1000);
        Product product = new Product(name, stock, money);

        // when
        Integer productBuyQuantity = 5;
        product.decreaseStock(productBuyQuantity);

        // then
        assertThat(product.getStock()).isEqualTo(5);
    }

    @Test
    void 현재_남은_재고보다_더_많은_수량으로_재고를_감소시킬_경우_예외가_발생한다() {

        // given
        String name = "mac book";
        int stock = 10;
        Money money = new Money(1000);
        Product product = new Product(name, stock, money);

        // when && then
        Integer productBuyQuantity = 1000;
        assertThatThrownBy(() -> product.decreaseStock(productBuyQuantity))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void NULL인_값으로_재고를_감소시킬_경우_예외가_발생한다() {

        // given
        String name = "mac book";
        int stock = 10;
        Money money = new Money(1000);
        Product product = new Product(name, stock, money);

        // when && then
        Integer productBuyQuantity = null;
        assertThatThrownBy(() -> product.decreaseStock(productBuyQuantity))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void 상품의_가격을_업데이트할_수_있다() {

        // given
        String name = "mac book";
        int stock = 10;
        Money money = new Money(1000);
        Product product = new Product(name, stock, money);

        // when
        Money updateMoney = new Money(20000);
        product.updateMoney(updateMoney);

        // then
        assertThat(product.getMoney()).isEqualTo("20000");
    }

    @Test
    void 상품의_상태를_업데이트할_수_있다() {

        // given
        String name = "mac book";
        int stock = 10;
        Money money = new Money(1000);
        Product product = new Product(name, stock, money);

        // when
        product.delete();

        // then
        assertThat(product.getIsDeleted()).isTrue();
    }
}