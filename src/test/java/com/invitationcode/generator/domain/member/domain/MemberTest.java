package com.invitationcode.generator.domain.member.domain;

import com.invitationcode.generator.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class MemberTest {

    @Autowired
    private MemberRepository memberRepository;

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Test
    void create() {

        // given
        Member member = Member.builder()
                .id("test")
                .password(new Password("12345", PASSWORD_ENCODER))
                .name("홍길동")
                .email(new Email("test@naver.com"))
                .build();

        // when
        Member save = memberRepository.save(member);

        // then
        assertThat(save.getIdx()).isEqualTo(1L);
    }
}