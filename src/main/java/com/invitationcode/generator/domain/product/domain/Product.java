package com.invitationcode.generator.domain.product.domain;

import com.invitationcode.generator.global.exception.BusinessException;
import com.invitationcode.generator.global.exception.ErrorCode;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Comment(value = "상품명")
    @Column(name = "name", nullable = false)
    private String name;

    @Comment(value = "재고")
    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Embedded
    private Money money;

    @Comment(value = "삭제 여부")
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Builder
    public Product(String name, int stock, Integer money) {
        this.name = name;
        this.money = new Money(money);
        setStock(stock);
    }

    public BigDecimal getMoney() {
        return money.getPrice();
    }

    public void updateName(@NonNull String name) {
        this.name = name;
    }

    public void updateStock(@NonNull Integer stock) {
        setStock(stock);
    }

    public void decreaseStock(Integer productBuyQuantity) {
        if (productBuyQuantity == null || this.stock < productBuyQuantity) {
            throw new BusinessException(ErrorCode.BIG_REQUEST_QUANTITY_THAN_PRODUCT_STOCK_EXCEPTION);
        }
        this.stock -= productBuyQuantity;
    }

    public void updateMoney(Integer price) {
        this.money = new Money(price);
    }

    public void delete() {
        this.isDeleted = true;
    }

    private void setStock(Integer stock) {
        if (stock == null || stock < 0) {
            throw new IllegalArgumentException();
        }
        this.stock = stock;
    }
}
