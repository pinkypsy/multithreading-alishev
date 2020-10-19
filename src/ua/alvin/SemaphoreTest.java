package ua.alvin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    public static void main(String[] args) {

        int threadsNum = 10000;

        ExecutorService executorService = Executors.newFixedThreadPool(threadsNum);
        Connection connection = Connection.getConnection();

        for (int i = 0; i < threadsNum; i++) {
            executorService.submit(new Thread(connection::work));
        }

        executorService.shutdown();
    }
}

class Connection {
    Semaphore semaphore = new Semaphore(300);
    private static Connection connection = new Connection();
    private int connectionCount;

    public static Connection getConnection() {
        return connection;
    }

    public void work() {
        try {
            semaphore.acquire();
            doWork();
        } catch (InterruptedException e) {
            /*NOP*/
        } finally {
            semaphore.release();
        }
    }

    private void doWork() {

        try {
            synchronized (this) {
                connectionCount++;
                System.out.println(connectionCount);
            }

            Thread.sleep(3000);
            synchronized (this) {
                connectionCount--;
            }
//            System.out.println(connectionCount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
