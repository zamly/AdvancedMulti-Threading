package Semaphores;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Created by zamly on 10/01/2015.
 */
public class App {
    public static void main(String[] args) throws InterruptedException {

        ExecutorService service = Executors.newCachedThreadPool();

        for (int i=0; i<200; i++) {
            service.submit(new Runnable() {
                @Override
                public void run() {
                    Connections.getInstance().connect();
                }
            });
        }

        service.shutdown();
        service.awaitTermination(5, TimeUnit.SECONDS);
    }
}
