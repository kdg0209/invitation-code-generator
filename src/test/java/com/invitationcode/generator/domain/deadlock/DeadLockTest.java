package com.invitationcode.generator.domain.deadlock;

import org.junit.jupiter.api.Test;

public class DeadLockTest {

    @Test
    void test() {
        Resource resourceA = new Resource();
        Resource resourceB = new Resource();

        ThreadA threadA = new ThreadA(resourceA, resourceB);
        ThreadB threadB = new ThreadB(resourceA, resourceB);

        threadA.start();
        threadB.start();
    }
}
