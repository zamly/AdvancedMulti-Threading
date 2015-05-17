package Volatile;

import java.util.Scanner;

/**
 * Created by zamly on 08/01/2015.
 */

class Runner extends Thread{

    /**
     * Check without the volatile keyword.
     * For Code optimization the running variable will be cached.
     * volatile keyword is used to prevent that.
     * so in every iteration in the loop, the state of the varialble
     * will be checked.
     */
    private volatile boolean running = true;

    @Override
    public void run() {

        while(running) {
            System.out.println("Hello World");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutdown() {
        running = false;
    }
}

public class App {
    public static void main(String[] args) {

        System.out.println("Thread Started....");
        Runner t1 = new Runner();
        t1.start();

        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        t1.shutdown();

    }
}
