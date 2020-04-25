package team.hedonism.monitor;
import team.hedonism.Main;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Philosopher implements Runnable {
    private final int id;
    private final Fork leftFork;
    private final Fork rightFork;

    private final State state;
    public final AtomicBoolean stopped = new AtomicBoolean();

    public AtomicInteger eatCount = new AtomicInteger();

    protected Philosopher(int id, Fork leftFork, Fork rightFork, State state) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.state = state;
    }

    @Override
    public void run() {
        try {
            while (!stopped.get()) {
                think();
                state.takeForks(id, leftFork, rightFork);
                eat();
                state.putForks(id, leftFork, rightFork);
            }
        } catch (InterruptedException ignored) { }
    }

    public void stop() {
        stopped.set(true);
    }

    private void think() throws InterruptedException {
        long t = System.nanoTime();
        if (Main.DEBUG) {
            System.out.println(t + ": " + Thread.currentThread().getName() + " is thinking");
        }
        if (Main.MAX_WAIT_MS > 0) {
            Thread.sleep(getRandomInt());
        }
    }

    private void eat() throws InterruptedException {
        long t = System.nanoTime();
        if (Main.DEBUG) {
            System.out.println(t + ": " + Thread.currentThread().getName() + " is eating");
        }
        if (Main.MAX_WAIT_MS > 0) {
            Thread.sleep(getRandomInt());
        }
        eatCount.incrementAndGet();
    }

    private int getRandomInt() {
        return (int) (Math.random() * Main.MAX_WAIT_MS);
    }
}
