package com.invitationcode.generator.domain.member.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email {

    @Comment(value = "이메일")
    @Column(name = "email", unique = true)
    private String email;


    public Email(String email) {
        this.email = email;
    }
}
