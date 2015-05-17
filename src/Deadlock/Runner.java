package Deadlock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zamly on 10/01/2015.
 */
public class Runner {

    Account acc1 = new Account();
    Account acc2 = new Account();
    Random random = new Random();

    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();

    public void firstThread() throws InterruptedException {

        for (int i=0;i<10000;i++) {
            lock1.lock();
            lock2.lock();
            try {
                Account.transfer(acc1, acc2, random.nextInt(100));
            }finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void secondThread() throws InterruptedException {

        for (int i=0;i<10000;i++) {
            /**
             * Note the order of the lock methods are different from
             * the first thread method.
             */
            lock2.lock();
            lock1.lock();
            try {
                Account.transfer(acc2, acc1, random.nextInt(100));
            }finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void finished() {
        System.out.println("Account 1 balance: "+acc1.getBalance());
        System.out.println("Account 2 balance: "+acc2.getBalance());
        System.out.println("Total: "+(acc2.getBalance()+acc1.getBalance()));
    }
}
