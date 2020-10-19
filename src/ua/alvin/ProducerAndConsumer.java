package ua.alvin;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerAndConsumer {

     BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(10);

    public static void main(String[] args) throws InterruptedException {

        ProducerAndConsumer producerAndConsumer = new ProducerAndConsumer();

        Thread producerThread = new Thread(() -> producerAndConsumer.producer());

        Thread consumerThread = new Thread(() -> producerAndConsumer.consumer());

        producerThread.start();
        consumerThread.start();

//        producerThread.join();
//        consumerThread.join();
    }

     void producer() {

        Random random = new Random();

        while (true) {
            try {
                Thread.sleep(100);
                System.out.println(" " + blockingQueue.size());

                blockingQueue.put(random.nextInt(100));


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

     void consumer() {
         System.out.println("hi");
        while (true) {
            try {
                Thread.sleep(150);
                System.out.println(" " + blockingQueue.size());
                    System.out.print(blockingQueue.take());


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
