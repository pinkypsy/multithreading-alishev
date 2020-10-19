package ua.alvin;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLockResolving {
    public static void main(String[] args) throws InterruptedException {

        long before = System.currentTimeMillis();
        Runner runner = new Runner();

        Thread thread1 = new Thread(runner::processFirst);
        Thread thread2 = new Thread(runner::processSecond);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        runner.result();
        long after = System.currentTimeMillis();

        System.out.println("Time elapsed: " + (after - before) + " ms.");

    }
}

class Runner{
    Random random = new Random();

    Account account1 = new Account();
    Account account2 = new Account();

    final Lock lock1 = new ReentrantLock();
    final Lock lock2 = new ReentrantLock();

    void lockAttempt (Lock lock1, Lock lock2){
        boolean isLocked1 = false;
        boolean isLocked2 = false;

        while (true) {
            try {
                isLocked1 = lock1.tryLock();
                isLocked2 = lock2.tryLock();
            } finally {

                if (isLocked1 && isLocked2) {
                    return;
                }
                if (isLocked1) {
                    lock1.unlock();
                }
                if (isLocked2) {
                    lock2.unlock();
                }
            }
        }
    }

     void processFirst(){
        for (int i = 0; i < 10000; i++) {
            lockAttempt(lock1, lock2);
            try {
                Account.transfer(account1,account2,random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

     void processSecond(){
         for (int i = 0; i < 10000; i++) {
             lockAttempt(lock2, lock1);
             try {
                 Account.transfer(account2,account1,random.nextInt(100));
             } finally {
                 lock2.unlock();
                 lock1.unlock();
             }
         }
    }

    void result(){
        System.out.println(account1.getBalance());
        System.out.println(account2.getBalance());
        System.out.println("Total: " + (account1.getBalance() + account2.getBalance()));
    }

}


class Account{
    int balance = 10000;

    void deposit (int amount){
        balance += amount;
    }

    void withdraw (int amount){
        balance -= amount;
    }

    int getBalance (){
        return balance;
    }

    static void transfer (Account accountSource, Account accountTarget, int amount){
        accountSource.withdraw(amount);
        accountTarget.deposit(amount);
    }
}