package Semaphores;

import java.util.concurrent.Semaphore;

/**
 * Created by zamly on 10/01/2015.
 */
public class Connections {

    private static Connections instance = new Connections();
    private int connections = 0;

    /**
     * Used to control access of resources
     */
    //true parameter will maintain the order of semaphore aquisition (first come first serve)
    Semaphore semaphore = new Semaphore(10, true);

    private Connections() {

    }

    public static Connections getInstance() {
        return instance;
    }

    public void connect() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            doConnect();
        }finally {
            semaphore.release();
        }
    }

    public void doConnect() {
        synchronized (this) {
            connections++;
            System.out.println("Current Connections: "+connections );
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (this) {
            connections--;
        }
    }
}
