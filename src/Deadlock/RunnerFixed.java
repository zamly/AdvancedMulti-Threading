package Deadlock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zamly on 10/01/2015.
 */
public class RunnerFixed {

    Account acc1 = new Account();
    Account acc2 = new Account();
    Random random = new Random();

    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();

    private void aquireLock(Lock lock1, Lock lock2) {
        while (true) {
            boolean getFirstLock = false;
            boolean getSecondLock = false;

            try {
                /**
                 * true if the lock was free
                 * and was acquired by the current thread,
                 * or the lock was already held by the current thread;
                 * and false otherwise
                 */
                getFirstLock = lock1.tryLock();
                getSecondLock = lock2.tryLock();
            }finally {
                if (getFirstLock && getSecondLock) {
                    return;
                }
                if (getFirstLock) lock1.unlock();
                if (getSecondLock) lock2.unlock();
            }
        }
    }

    public void firstThread() throws InterruptedException {

        for (int i=0;i<10000;i++) {
            aquireLock(lock1, lock2);
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
            aquireLock(lock2, lock1);
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
