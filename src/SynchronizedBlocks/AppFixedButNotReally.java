package SynchronizedBlocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by zamly on 08/01/2015.
 */
public class AppFixedButNotReally {
    static Random random = new Random();
    private static List<Integer> list1 = new ArrayList<Integer>();
    private static List<Integer> list2 = new ArrayList<Integer>();

    public synchronized static void pushList1() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        list1.add(random.nextInt(100));
    }

    public synchronized static void pushList2() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        list2.add(random.nextInt(100));
    }

    public static void process() {
        for (int i=0; i<1000; i++) {
            pushList1();
            pushList2();
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting....");
        long start = System.currentTimeMillis();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                process();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                process();
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

        long end = System.currentTimeMillis();

        /**
         * Note the execution time has taken more than 2 sec (4 sec).
         * This is because synchronization makes the thread acquire the
         * intrinsic lock of the CLASS OBJECT.
         * So when the method pushList1 is locked there is no accessibility
         * for the pushList2.
         */
        System.out.println("Execution Time: "+(end-start));
        System.out.println("List1: "+ list1.size() +" | List2 : "+list2.size() );
    }
}
