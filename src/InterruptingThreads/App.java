package InterruptingThreads;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by zamly on 10/01/2015.
 */
public class App {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Starting Thread...");

        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<?> fu = executorService.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {

                for (int i=0;i<1E8; i++){
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("Interrupted..");
                        break;
                    }
                    Math.atan(new Random().nextDouble());
                }
                return null;
            }
        });

        executorService.shutdown();

        Thread.sleep(500);

        /**
         * checkout the following interrupt commands.
         */
        //executorService.shutdownNow();
        //fu.cancel(true);

        executorService.awaitTermination(1, TimeUnit.DAYS);

        System.out.println("Ending Thread...");
    }
}
