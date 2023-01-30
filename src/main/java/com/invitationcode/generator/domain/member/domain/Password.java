package com.invitationcode.generator.domain.member.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {

    @Comment(value = "비밀번호")
    @Column(name = "password", nullable = false)
    private String password;

    public Password(String password, PasswordEncoder passwordEncoder) {
        setPassword(password, passwordEncoder);
    }

    public void changePassword(String oldPwd, String newPwd, PasswordEncoder passwordEncoder) {
        if (!matchPassword(oldPwd, passwordEncoder)) {
            throw new IllegalArgumentException();
        }
        setPassword(newPwd, passwordEncoder);
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String password, PasswordEncoder passwordEncoder) {
        if (!StringUtils.hasText(password)) {
            throw new IllegalArgumentException();
        }
        this.password = passwordEncoder.encode(password);
    }

    private boolean matchPassword(String inputPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(inputPassword, this.password);
    }
}
