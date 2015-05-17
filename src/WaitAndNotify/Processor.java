package WaitAndNotify;

import java.util.Scanner;

/**
 * Created by zamly on 08/01/2015.
 */
public class Processor {

    public void produce() throws InterruptedException {
        //synchronizes on the Processor Object itself
        synchronized (this) {
            System.out.println("Producer Thread running....");
            wait(); //at this point the thread looses control of the lock. and will not resume
            System.out.println("Resumed.");
        }
    }

    public void consume() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        Thread.sleep(2000);
        synchronized (this) {
            System.out.println("Waiting for Return Key:");
            scanner.nextLine();
            System.out.println("Return key pressed.");
            /**
             * can only be called within synchronized code block.
             * Doesnt release the lock until it reaches the end of the
             * code block.
             */
            notify();
            /**
             * Try without the below line to see the difference.
             */
            Thread.sleep(2000);
        }
    }
}
