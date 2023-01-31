package com.invitationcode.generator.domain.member.domain;

import com.invitationcode.generator.domain.member.dao.MemberDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
class PasswordTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberDao memberDao;

    @Test
    void 비밀번호_생성() {

        // given
        Password password = new Password("12345", passwordEncoder);

        // when
        String encodedPassword = passwordEncoder.encode(password.getPassword());

        // then
        assertThat(passwordEncoder.matches(password.getPassword(), encodedPassword)).isTrue();
    }

    @Test
    void 잘못된_비밀번호_입력으로_인한_변경_실패() {

        // given
        Long memberIdx = 12L;
        String oldPassword = "123456";
        String newPassword = "123456789";

        // when
        Member member = memberDao.findByIdx(memberIdx);


        // then
        assertThatThrownBy(() -> {
            member.changeablePassword(oldPassword, newPassword, passwordEncoder);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 비밀번호_변경_성공() {

        // given
        Long memberIdx = 12L;
        String oldPassword = "12345";
        String newPassword = "123456789";

        // when
        Member member = memberDao.findByIdx(memberIdx);
        member.changeablePassword(oldPassword, newPassword, passwordEncoder);

        // then
        assertThat(member.getIdx()).isEqualTo(12L);
    }
}