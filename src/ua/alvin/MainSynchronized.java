package ua.alvin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainSynchronized {

    public static void main(String[] args) throws InterruptedException {
        new Worker().go();
    }

}

class Worker{

    Object lock1 = new Object();
    Object lock2 = new Object();

    List<Integer> list1 = new ArrayList<>();
    List<Integer> list2 = new ArrayList<>();

    public void go() throws InterruptedException {
        long before = System.currentTimeMillis();

        Thread thread1 = new Thread(this::doWork);
        Thread thread2 = new Thread(this::doWork);

        thread1.start();
        thread2.start();


        thread1.join();
        thread2.join();

        long after = System.currentTimeMillis();

        System.out.println(list1.size());
        System.out.println(list2.size());

        System.out.println(after - before + " ms has passed");
    }

     void doWork(){

        Random random = new Random();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


//         synchronized (this) {
        synchronized (lock1) {
            for (int i = 0; i < 1000; i++) {
                list1.add(random.nextInt(100));
                System.out.println(Thread.currentThread());
            }
        }


        synchronized (lock2) {
            for (int i = 0; i < 1000; i++) {
                    list2.add(random.nextInt(100));
                    System.out.println(Thread.currentThread());
                }
        }
//         }

     }

}
