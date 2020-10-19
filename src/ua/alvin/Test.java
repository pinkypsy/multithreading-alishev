package ua.alvin;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        WaitAndNotify waitAndNotify = new WaitAndNotify();

        Thread producerThread = new Thread(waitAndNotify::producer);
        Thread consumerThread = new Thread(waitAndNotify::consumer);
//        Thread floodThread = new Thread(waitAndNotify::flood);

//        producerThread.start();
//        consumerThread.start();
//        floodThread.start();
        //====================================================================================
        ProdAndConsWithWaitAndNotify prodAndConsWithWaitAndNotify = new ProdAndConsWithWaitAndNotify();


        Thread producerThread1 = new Thread(prodAndConsWithWaitAndNotify::producer);

        Thread consumerThread2 = new Thread(prodAndConsWithWaitAndNotify::consumer);

        Thread offThread = new Thread(prodAndConsWithWaitAndNotify::off);

        producerThread1.start();
        consumerThread2.start();
        offThread.start();

    }
}

class WaitAndNotify {
    final Object lock = new Object();

    public void producer() {

        System.out.println("Producer method started");
        try {
            synchronized (lock) {
                lock.wait();//wait отдает монитор на другой синхронизирующий блок и ждет оповещения notify,
                // а также выхода из синхро блокадля возврата монитора
                System.out.println("Producer method resumed");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void consumer() {

        try {
            Thread.sleep(100);
            System.out.println("Consumer method started. Press Enter to return in producer method");

            new Scanner(System.in).nextLine();

            synchronized (lock) {
                lock.notifyAll();//запускает приостановленные потоки, но не отдает монитор, как это делает wait.
                // Монитор вернется после выхода из блока
                for (int i = 2; i > 0; i--) {
                    System.out.println("Wait for it " + i + " sec...");
                    Thread.sleep(1000);
                }
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void flood() {

        try {
            synchronized (this) {
                wait();
                while (true) {

                    Thread.sleep(300);
                    System.out.println("Flood");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class ProdAndConsWithWaitAndNotify {

    final int LIMIT = 10;
    final Object lock = new Object();
    Queue<Integer> queue = new LinkedList<>();
    int value;

    void producer() {

        try {
            while (true) {
                synchronized (lock) {

                    while (queue.size() == LIMIT) {
                        lock.wait();
                    }
                    queue.offer(value++);
                    System.out.println("value: " + value + " size in producer: " + queue.size());
                    lock.notify();

                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    void consumer()   {
        try {
            while (true) {
                synchronized (lock) {

                    while (queue.size() == 0) {
                        lock.wait();
                    }
                    System.out.println("poll " + queue.poll() + " size in consumer: " + queue.size());
                    lock.notify();
                }
                Thread.sleep(300);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    void off() {
        while (true) {
            new Scanner(System.in).nextLine();
            System.exit(0);

        }
    }
}