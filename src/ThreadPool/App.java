package ThreadPool;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by zamly on 08/01/2015.
 */

class processor implements Runnable{

    private int id;

    public processor(int id) {
        this.id = id;
    }
    @Override
    public void run() {
        System.out.println("Starting Thread:" + id);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Ending Thread:" + id);
    }
}

public class App {

    public static void main(String[] args) throws InterruptedException {

        /**
         * Only two task will be executed at the same time.
         * change the parameter to find different result.
         */
        ExecutorService service = Executors.newFixedThreadPool(2);
        for (int i=0;i<5;i++) {
            service.submit(new processor(i));
        }

        service.shutdown();
        System.out.println("Task Submitted.");
        service.awaitTermination(15, TimeUnit.SECONDS);
        System.out.println("All task completed.");
    }
}
