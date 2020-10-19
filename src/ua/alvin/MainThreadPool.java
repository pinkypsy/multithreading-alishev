package ua.alvin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainThreadPool {

    public static void main(String[] args) {

        long before = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            executorService.submit(new Work(i));
        }

        executorService.shutdown();

        long after = System.currentTimeMillis();

        System.out.println(after - before + " ms.");
    }

    }

class Work implements Runnable{

    int id;

    public Work(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("task " + id + " has been completed by " + Thread.currentThread());
    }
}