package Synchronized;

/**
 * Created by zamly on 08/01/2015.
 */
public class AppFixed {

    private int count = 0;

    public static void main(String[] args) {

        AppFixed app = new AppFixed();
        app.execute();
    }

    public synchronized void increment() {
        count++;
    }

    private void execute() {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i=0 ; i< 10000; i++) {

                    increment();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i=0 ; i< 10000; i++) {
                    increment();
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

        System.out.println(count);
    }
}
