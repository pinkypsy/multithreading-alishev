package ua.alvin;

import javax.swing.*;
import java.util.Random;
import java.util.concurrent.Callable;

public class InterruptTest {

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 1_000_000_000; i++) {

                if (Thread.interrupted()) {
                    System.out.println("is interrupted");
                    break;
                }
                int num = random.nextInt(100);
                System.out.println("sin(" + num + ") is: " + Math.sin(num));
            }
        });

        thread.start();
        System.out.println("Thread is started");
        Thread.sleep(2000);
        thread.interrupt();

        thread.join();


    }

}
