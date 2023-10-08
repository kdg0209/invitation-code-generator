package com.invitationcode.generator.domain.memberinvitation.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class InviteCodeTest {

    @Test
    void 정상적인_초대코드를_생성할_수_있다() {

        // given && when
        InviteCode result = new InviteCode();

        // then
        assertThat(result).isNotNull();
    }

    @Test
    void 동시에_N명이_요청으로_중복되지_않은_초대코드를_생성할_수_있다() throws InterruptedException {

        // given
        int threadCount = 1000;
        CountDownLatch latch = new CountDownLatch(threadCount);
        List<CompletableFuture<String>> futures = new ArrayList<>();

        // when
        for (int i = 0; i < threadCount; i++) {
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                try {
                    InviteCode result = new InviteCode();
                    return result.getInviteCode();
                } finally {
                    latch.countDown();
                }
            });
            futures.add(future);
        }
        latch.await();

        Set<String> result = futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toSet());

        // then
        assertThat(result.size()).isEqualTo(1000);
    }

}