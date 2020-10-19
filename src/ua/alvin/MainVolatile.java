package ua.alvin;

public class MainVolatile {
    int counter;

    public static void main(String[] args) throws InterruptedException {

MainVolatile mainVolatile = new MainVolatile();
mainVolatile.doWork();

    }

    public void doWork() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                increment();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(counter);
    }

    private synchronized void increment() {
        counter++;
        System.out.println(Thread.currentThread());
    }
}
