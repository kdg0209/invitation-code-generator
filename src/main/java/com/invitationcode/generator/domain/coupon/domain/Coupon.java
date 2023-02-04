package com.invitationcode.generator.domain.coupon.domain;

import com.invitationcode.generator.global.exception.BusinessException;
import com.invitationcode.generator.global.exception.ErrorCode;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Embedded
    private Money money;

    @Comment(value = "쿠폰명")
    @Column(name = "name", nullable = false)
    private String name;

    @Comment(value = "재고")
    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Comment(value = "만료일자")
    @Column(name = "expiration_datetime", nullable = false)
    private LocalDateTime expirationDateTime;

    @Builder
    public Coupon(Money money, @NonNull String name, Integer stock, @NonNull LocalDateTime expirationDateTime) {
        this.money = money;
        this.name = name;
        this.expirationDateTime = expirationDateTime;
        setStock(stock);
    }

    public BigDecimal getMoney() {
        return money.getSalePrice();
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateStock(Integer stock) {
        setStock(stock);
    }

    public void updateMoney(Money money) {
        this.money = money;
    }

    public void updateExpirationDateTime(LocalDateTime expirationDateTime) {
        LocalDateTime now = LocalDateTime.now();
        if (expirationDateTime.isBefore(now)) {
            throw new BusinessException(ErrorCode.COUPON_EXPIRATION_DATETIME_EXCEPTION);
        }
        this.expirationDateTime = expirationDateTime;
    }

    public void decreaseStock(Integer stock) {
        if (stock == null || stock < 0 || this.stock < stock) {
            throw new BusinessException(ErrorCode.BIG_REQUEST_STOCK_THAN_COUPON_STOCK_EXCEPTION);
        }
        this.stock -= stock;
    }

    public void verifyExpirationDateTimeValidator() {
        LocalDateTime now = LocalDateTime.now();
        if (this.expirationDateTime.isBefore(now)) {
            throw new BusinessException(ErrorCode.COUPON_EXPIRATION_DATETIME_GIVEN_EXCEPTION);
        }
    }

    private void setStock(Integer stock) {
        if (stock == null || stock < 0) {
            throw new IllegalArgumentException();
        }
        this.stock = stock;
    }
}
