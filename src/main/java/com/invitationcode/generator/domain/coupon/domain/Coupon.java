package com.invitationcode.generator.domain.coupon.domain;

import com.invitationcode.generator.global.exception.BusinessException;
import com.invitationcode.generator.global.exception.ErrorCode;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.util.StringUtils;

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
    private int stock;

    @Comment(value = "만료일자")
    @Column(name = "expiration_datetime", nullable = false)
    private LocalDateTime expirationDateTime;

    @Builder
    public Coupon(Money money, String name, int stock, LocalDateTime expirationDateTime) {
        this.money = money;
        setName(name);
        setExpirationDateTime(expirationDateTime);
        setStock(stock);
    }

    public BigDecimal getMoney() {
        return money.getSalePrice();
    }

    public void updateName(String name) {
        setName(name);
    }

    public void updateStock(int stock) {
        setStock(stock);
    }

    public void updateMoney(Money money) {
        this.money = money;
    }

    public void updateExpirationDateTime(LocalDateTime expirationDateTime) {
        setExpirationDateTime(expirationDateTime);
    }

    public void decreaseStock(int stock) {
        if (stock < 0 || this.stock < stock) {
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

    private void setName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    private void setExpirationDateTime(LocalDateTime expirationDateTime) {
        LocalDateTime now = LocalDateTime.now();
        if (expirationDateTime == null || expirationDateTime.isBefore(now)) {
            throw new BusinessException(ErrorCode.COUPON_EXPIRATION_DATETIME_EXCEPTION);
        }
        this.expirationDateTime = expirationDateTime;
    }

    private void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException();
        }
        this.stock = stock;
    }
}
