package ReEntrantLocks;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zamly on 09/01/2015.
 */
public class Runner {

    private int count = 0;

    private Lock lock = new ReentrantLock();
    private Condition cond = lock.newCondition();

    public void increament() {
        for (int i=0;i<1000;i++) {
            count++;
        }
    }

    public void firstThread() throws InterruptedException {
        lock.lock();

        System.out.println("First thread waiting...");
        cond.await();
        System.out.println("Resumed..");

        try{
            increament();
        }finally {
            lock.unlock();
        }

    }

    public void secondThread() throws InterruptedException {
        Thread.sleep(1000);
        lock.lock();

        System.out.println("Press enter: ");
        new Scanner(System.in).nextLine();
        System.out.println("Key pressed.");

        cond.signal();
        try{
            increament();
        }finally {
            /**
             * Even though the thread signaled, the lock has to be
             * unlocked for the first thread to resume from the
             * wait point.
             *
             * TRY without the unlock
             */
            lock.unlock();
        }
    }

    public void print() {
        System.out.println("Count: "+ count);
    }
}
