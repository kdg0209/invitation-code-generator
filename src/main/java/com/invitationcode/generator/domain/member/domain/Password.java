package com.invitationcode.generator.domain.member.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Comment(value = "비밀번호")
    @Column(name = "password", nullable = false)
    private String password;

    public Password(String password) {
        setPassword(password);
    }

    public void changePassword(String oldPwd, String newPwd) {
        if (!matchPassword(oldPwd)) {
            throw new IllegalArgumentException();
        }
        setPassword(newPwd);
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        if (!StringUtils.hasText(password)) {
            throw new IllegalArgumentException();
        }
        this.password = PASSWORD_ENCODER.encode(password);
    }

    private boolean matchPassword(String inputPassword) {
        return PASSWORD_ENCODER.matches(inputPassword, this.password);
    }
}
