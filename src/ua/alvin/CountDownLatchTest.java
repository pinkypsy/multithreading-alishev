package ua.alvin;

import org.w3c.dom.ls.LSOutput;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        CountDownLatch countDownLatch = new CountDownLatch(2);
        System.out.println("main stops");
        for (int i = 0; i < 6; i++) {
            executorService.submit(new Worker(i, countDownLatch));
        }


        executorService.shutdown();

        countDownLatch.await();


        System.out.println("main resumes");

    }


    static class Worker implements Runnable {

        int id;
        CountDownLatch countDownLatch;

        public Worker(int id, CountDownLatch countDownLatch) {
            this.id = id;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {

            System.out.println("Worker " + id + " has started processing");
            try {
                Thread.sleep(200);
                System.out.println(id + " ends processing");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                countDownLatch.countDown();
            }



        }
    }
}

