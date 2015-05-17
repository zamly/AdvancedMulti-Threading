package CountdownLatches;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by zamly on 08/01/2015.
 */
class processor implements Runnable {
    private CountDownLatch latch;

    public  processor(CountDownLatch latch) {
        this.latch = latch;
    }
    @Override
    public void run() {
        System.out.println("Started...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        latch.countDown();
    }
}
public class App {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(3);
        ExecutorService service = Executors.newFixedThreadPool(1);

        for (int i=0;i<3;i++) {
            service.submit(new processor(countDownLatch));
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Completed.");
        service.shutdown();
        service.awaitTermination(5, TimeUnit.MINUTES);
    }
}
