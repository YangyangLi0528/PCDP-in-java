package edu.coursera.parallel;

import java.util.concurrent.Phaser;

/**
 * 测试
 * Author liyangyang
 * 2018/4/2
 */
public class PhaserTest {

    public static void main(String...args){
        Phaser phaser = new Phaser(5);
        for(int i =0;i<5;i++){
            Task task = new Task(phaser);
            Thread thread = new Thread(task,"PhaseTest_"+i);
            thread.start();
        }
        try {
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        phaser.arrive();

    }

    static class Task implements Runnable{
        private final Phaser phaser;
        Task(Phaser phaser){
            this.phaser = phaser;
        }

        @Override
        public void run() {
            phaser.awaitAdvance(phaser.getPhase());//countDownLatch.await()
            System.out.println(Thread.currentThread().getName() + "\"执行任务...");
        }
    }
}
