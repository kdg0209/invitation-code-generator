package com.invitationcode.generator.domain.member.domain;


import com.invitationcode.generator.domain.coupon.domain.Coupon;
import com.invitationcode.generator.domain.memberinvitation.domain.MemberInvitation;
import com.invitationcode.generator.domain.orders.domain.Orders;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Comment(value = "아이디")
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Embedded
    private Password password;

    @Comment(value = "사용자 명")
    @Column(name = "name", nullable = false)
    private String name;

    @Comment(value = "닉네임")
    @Column(name = "nick_name", nullable = false)
    private String nickName;

    @Embedded
    private Email email;

    @Comment(value = "탈퇴 여부")
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<MemberInvitation> memberInvitations = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Orders> orders = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberHasCoupon> memberHasCoupons = new ArrayList<>();

    @Builder
    public Member(String id, Password password, String name, Email email, String nickName) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.nickName = nickName;
        this.isDeleted = false;
    }

    public void updateNickName(String nickName) {
        this.nickName = nickName;
    }

    public void changeablePassword(String oldPwd, String newPwd, PasswordEncoder passwordEncoder) {
        this.password.changePassword(oldPwd, newPwd, passwordEncoder);
    }

    public void delete() {
        this.isDeleted = true;
    }

    public String getEmail() {
        return this.email.getEmail();
    }

    public void addCoupon(Coupon coupon, int stock) {
        MemberHasCoupon memberHasCoupon = MemberHasCoupon.builder()
                .member(this)
                .coupon(coupon)
                .couponName(coupon.getName())
                .couponSalePrice(coupon.getMoney())
                .couponStock(stock)
                .couponExpirationDateTime(coupon.getExpirationDateTime())
                .build();
        this.memberHasCoupons.add(memberHasCoupon);
    }

    public String passwordToString() {
        return this.password.getPassword();
    }
}
