package team.hedonism.naive;

import team.hedonism.BaseRunner;
import team.hedonism.BaseFork;
import team.hedonism.Main;

public class Runner extends BaseRunner {
    public static void main(String[] args) {
        System.out.println(System.nanoTime() + ": Dinner is started");

        BaseFork[] forks = new BaseFork[Main.PHILOSOPHERS_COUNT];
        for (int i = 0; i < forks.length; i++) {
            forks[i] = new BaseFork(i + 1);
        }

        Philosopher[] philosophers = new Philosopher[Main.PHILOSOPHERS_COUNT];
        for (int i = 0; i < philosophers.length; i++) {
            BaseFork leftFork = forks[i];
            BaseFork rightFork = forks[(i + 1) % forks.length];

            philosophers[i] = new Philosopher(leftFork, rightFork);

            threads[i] = new Thread(philosophers[i], "Philosopher " + (i + 1));
            threads[i].start();
        }

        waitMillis(Main.DINNER_DURATION_IN_MS);
        stopThreads(threads);

        System.out.println(System.nanoTime() + ": Dinner is finished");
    }
}
