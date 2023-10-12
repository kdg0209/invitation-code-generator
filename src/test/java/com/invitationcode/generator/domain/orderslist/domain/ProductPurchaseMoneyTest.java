package com.invitationcode.generator.domain.orderslist.domain;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class ProductPurchaseMoneyTest {

	@Test
	void 정수값을_사용하여_객체를_생성할_수_있다() {

		// given
		BigDecimal money = new BigDecimal("10000");

		// when
		ProductPurchaseMoney result = new ProductPurchaseMoney(money);

		// then
		assertThat(result).isNotNull();
	}

	@Test
	void 음수값을_사용하여_객체를_생성하는_경우_예외가_발생한다() {

		// given
		BigDecimal money = new BigDecimal("-10000");

		// when && then
		assertThatThrownBy(() -> new ProductPurchaseMoney(money))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void NULL값을_사용하여_객체를_생성하는_경우_예외가_발생한다() {

		// given
		BigDecimal money = null;

		// when && then
		assertThatThrownBy(() -> new ProductPurchaseMoney(money))
			.isInstanceOf(IllegalArgumentException.class);
	}
}