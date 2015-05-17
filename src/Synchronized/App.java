package Synchronized;

/**
 * Created by zamly on 08/01/2015.
 */
public class App {

    private int count = 0;

    public static void main(String[] args) {

        App app = new App();
        app.execute();
    }

    private void execute() {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i=0 ; i< 10000; i++) {

                    /**
                     * this is equal to count = count + 1;
                     * So both the threads have to read and write to the same
                     * variable.
                     * if both threads read and write to the variable at the same time
                     * The result will not be as expected.
                     */
                    count++;
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i=0 ; i< 10000; i++) {
                    count++;
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
