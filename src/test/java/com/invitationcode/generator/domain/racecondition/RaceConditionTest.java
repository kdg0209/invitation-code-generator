package com.invitationcode.generator.domain.racecondition;

import org.junit.jupiter.api.Test;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;

public class RaceConditionTest {

    private static final ExecutorService executor = Executors.newFixedThreadPool(2);

    @Test
    void example() throws InterruptedException {

        Counter counter = new Counter();
        int threadCount = 1000;
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            CompletableFuture.runAsync(() -> {
                try {
                    counter.increment();
                } finally {
                    latch.countDown();
                }
            }, executor);
        }
        latch.await();

        assertThat(counter.getCount()).isEqualTo(threadCount);
    }
}
