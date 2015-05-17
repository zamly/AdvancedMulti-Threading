package ProducerConsumerLowLevelSync;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by zamly on 09/01/2015.
 */
public class Processor {

    private LinkedList<Integer> linkedList = new LinkedList<Integer>();
    private int LIMIT = 10;
    private Object lock = new Object();

    public void produce() throws InterruptedException{
        int value = 0;
        while (true) {
            synchronized (lock) {
                while (linkedList.size() == LIMIT) {
                    lock.wait();
                }
                linkedList.add(value++);
                lock.notify();
            }
        }
    }

    public void consume() throws InterruptedException{
        while (true) {
            synchronized (lock) {
                while (linkedList.size() == 0) {
                    lock.wait();
                }
                System.out.print("List Size:" + linkedList.size());
                int value = linkedList.removeFirst();
                System.out.println(";value: "+value);
                lock.notify();
            }

            Thread.sleep(new Random().nextInt(1000));
        }
    }
}
