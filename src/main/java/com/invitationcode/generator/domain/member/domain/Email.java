package com.invitationcode.generator.domain.member.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email {

    private static final String REGEX = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";

    @Comment(value = "이메일")
    @Column(name = "email", unique = true)
    private String email;

    public Email(String email) {
        setEmail(email);
    }

    private void setEmail(String email) {
        if (!email.matches(REGEX)) {
            throw new IllegalArgumentException();
        }
        this.email = email;
    }
}
