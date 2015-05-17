package ProducerConsumer;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by zamly on 08/01/2015.
 */
public class App {

    private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);

    private static void producer() throws InterruptedException {
        Random random = new Random();
        while (true) {
            /**
             * puts values into the queue only when the size of
             * the queue is less than 10. It waits until the condition appears.
             */
            queue.put(random.nextInt(100));
        }
    }

    private static void consumer() throws InterruptedException {
        Random random = new Random();

        while (true) {
            Thread.sleep(100);
            if (random.nextInt(10) == 0) {
                /**
                 * Takes values only when the queue is not empty.
                 * If the queue is empty at the time it tries to take a value,
                 * it waits until a value is pushed to the queue.
                 */
                Integer value = queue.take();
                System.out.println("Value Taken: "+value+" |queue Size: "+queue.size());
            }
        }
    }

    public static void main(String[] args) {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    producer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    consumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
