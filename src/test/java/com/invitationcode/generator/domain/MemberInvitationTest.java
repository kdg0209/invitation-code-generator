package com.invitationcode.generator.domain;

import com.invitationcode.generator.domain.member.domain.Member;
import com.invitationcode.generator.domain.member.domain.Password;
import com.invitationcode.generator.domain.member.repository.MemberRepository;
import com.invitationcode.generator.domain.memberinvitation.domain.InviteCode;
import com.invitationcode.generator.domain.memberinvitation.domain.MemberInvitation;
import com.invitationcode.generator.domain.memberinvitation.repository.MemberInvitationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class MemberInvitationTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberInvitationRepository memberInvitationRepository;

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    private static final Executor executor = Executors.newFixedThreadPool(150);

    private Member member;

    @BeforeEach
    void memberInit() {
        Password password = new Password("12345", PASSWORD_ENCODER);
        member = Member.builder()
                .name("홍길동")
                .password(password)
                .build();

        memberRepository.save(member);
    }

    @Test
    void 멤버코드_생성() {

        // given
        InviteCode inviteCode = new InviteCode();

        MemberInvitation memberInvitation = MemberInvitation.builder()
                .receiverEmail("test1@naver.com")
                .inviteCode(inviteCode)
                .member(member)
                .build();

        // when
        memberInvitationRepository.save(memberInvitation);

        // then
        assertThat(memberInvitation.getIdx()).isEqualTo(3L);
    }

    @Test
    @DisplayName("동시에 100명 초대시 중복된 초대 코드 발생")
    void 동시에_100명_초대() throws InterruptedException {
        List<CompletableFuture<MemberInvitation>> completableFutures = new ArrayList<>();

        int threadCount = 100;
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            CompletableFuture<MemberInvitation> future = CompletableFuture.supplyAsync(() -> {
                try {
                    InviteCode inviteCode = new InviteCode();
                    MemberInvitation memberInvitation = MemberInvitation.builder()
                            .receiverEmail("test1@naver.com")
                            .inviteCode(inviteCode)
                            .member(member)
                            .build();
                    return memberInvitationRepository.save(memberInvitation);
                } finally {
                    latch.countDown();
                }
            }, executor);
            completableFutures.add(future);
        }
        latch.await();

        List<MemberInvitation> createdMemberInvitations = completableFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        assertThat(createdMemberInvitations.size()).isEqualTo(100);
    }

    @Test
    @DisplayName("동시에 1000명 초대시 중복된 초대 코드 발생")
    void 동시에_1000명_초대() throws InterruptedException {
        List<CompletableFuture<MemberInvitation>> completableFutures = new ArrayList<>();

        int threadCount = 1000;
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            CompletableFuture<MemberInvitation> future = CompletableFuture.supplyAsync(() -> {
                try {
                    InviteCode inviteCode = new InviteCode();
                    MemberInvitation memberInvitation = MemberInvitation.builder()
                            .receiverEmail("test1@naver.com")
                            .inviteCode(inviteCode)
                            .member(member)
                            .build();
                    return memberInvitationRepository.save(memberInvitation);
                } finally {
                    latch.countDown();
                }
            }, executor);
            completableFutures.add(future);
        }
        latch.await();

        List<MemberInvitation> createdMemberInvitations = completableFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        assertThat(createdMemberInvitations.size()).isEqualTo(1000);
    }

    @Test
    @DisplayName("동시에 10000명 초대시 중복된 초대 코드 발생")
    void 동시에_10000명_초대() throws InterruptedException {
        List<CompletableFuture<MemberInvitation>> completableFutures = new ArrayList<>();

        int threadCount = 10000;
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            CompletableFuture<MemberInvitation> future = CompletableFuture.supplyAsync(() -> {
                try {
                    InviteCode inviteCode = new InviteCode();
                    MemberInvitation memberInvitation = MemberInvitation.builder()
                            .receiverEmail("test1@naver.com")
                            .inviteCode(inviteCode)
                            .member(member)
                            .build();
                    return memberInvitationRepository.save(memberInvitation);
                } finally {
                    latch.countDown();
                }
            }, executor);
            completableFutures.add(future);
        }
        latch.await();

        List<MemberInvitation> createdMemberInvitations = completableFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        assertThat(createdMemberInvitations.size()).isEqualTo(10000);
    }
}