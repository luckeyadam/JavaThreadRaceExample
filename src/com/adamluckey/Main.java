package com.adamluckey;

/**
 * Race condition example
 */
public class Main {

    public static void main(String[] args) {
        Countdown countdown = new Countdown();

        CountdownThread thread1 = new CountdownThread(countdown);
        thread1.setName("Thread 1");
        CountdownThread thread2 = new CountdownThread(countdown);
        thread2.setName("Thread 2");

        thread1.start();
        thread2.start();
    }
}

class Countdown {

    private int i;

    // could add synchronized to the method to avoid a race condition
    // this makes sure that the method is only used by 1 thread at a time
    // public synchronized void doCountDown() {
    public void doCountDown() {
        String color;
        switch (Thread.currentThread().getName()) {
            case "Thread 1":
                color = ThreadColor.ANSI_CYAN;
                break;
            case "Thread 2":
                color = ThreadColor.ANSI_PURPLE;
                break;
            default:
                color = ThreadColor.ANSI_GREEN;
        }

        // changing i from a local variable ex: for (int i = 10; i > 0; i--) allows each thread
        // to have an instance of i to keep integers printing individual values for each thread
        // the current iteration may cause singular and duplicate results depending on when
        // each thread is suspended such as during the decrement or the println, i.e race condition

        // you could add a synchronized statement here as well to enforce synchronization
        //synchronized (this) {
            for (i = 10; i > 0; i--) {
                System.out.println(color + Thread.currentThread().getName() + ": i = " + i);
            }
       // }
    }
}

class CountdownThread extends Thread {
    private Countdown threadCountdown;

    public CountdownThread(Countdown countdown) {
        threadCountdown = countdown;
    }

    public void run() {
        threadCountdown.doCountDown();
    }
}
