package com.invitationcode.generator.domain.deadlock;

public class ThreadB extends Thread {

    private Resource resourceA;
    private Resource resourceB;

    public ThreadB(Resource resourceA, Resource resourceB) {
        this.resourceA = resourceA;
        this.resourceB = resourceB;
    }

    @Override
    public void run() {
        synchronized (resourceA) {
            System.out.println("ThreadB가 resourceA 자원을 획득하여 사용중입니다.");

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("ThreadB가 resourceB 자원을 획득하기 위해 기다리고 있습니다.");

            synchronized (resourceB) {
                System.out.println("ThreadB가 resourceA, resourceB 자원을 획득하여 사용중입니다.");
            }
        }
    }
}
