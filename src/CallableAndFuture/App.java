package CallableAndFuture;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by zamly on 10/01/2015.
 */
public class App {

    public static void main(String[] args) {

        ExecutorService service = Executors.newCachedThreadPool();

        Future<Integer> future = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int duration = new Random().nextInt(2000);
                if (duration > 1000) {
                    throw new IOException("Duration High!!");
                }
                System.out.println("Starting....");
                Thread.sleep(duration);
                System.out.println("Stopped...");

                return duration;
            }
        });

        service.shutdown();

        //service.awaitTermination();

        /**
         * future.get method will wait until the thread terminates.
         */
        try {
            System.out.println("Result : "+ future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            IOException ex = (IOException) e.getCause();

            System.out.println(ex.getMessage());
        }

        System.out.println(future.isDone());

    }
}
