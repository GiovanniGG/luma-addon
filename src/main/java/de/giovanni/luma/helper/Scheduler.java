package de.giovanni.luma.helper;

import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Scheduler {
    private static Timer timer = new Timer();
    private static final Random random = new Random();
    private static final HashMap<Integer, TimerTask> tasks = new HashMap<>();

    public static int scheduleRepeatingTask(final Runnable runnable, long period, long delay) {
        TimeUnit unitdelay = TimeUnit.MILLISECONDS;
        TimeUnit unitperiod = TimeUnit.MILLISECONDS;
        final int id = generateTaskId();
        timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                runnable.run();
            }

            public boolean cancel() {
                boolean ret = super.cancel();
                if (ret) {
                    Scheduler.tasks.remove(id);
                }

                return ret;
            }
        };
        tasks.put(id, task);
        timer.scheduleAtFixedRate(task, unitdelay.toMillis(delay), unitperiod.toMillis(period));
        return id;
    }

    public static void killTask(int id) {
        if (tasks.containsKey(id)) {
            tasks.get(id).cancel();
        }
    }

    private static int generateTaskId() {
        int id = random.nextInt(Integer.MAX_VALUE);
        return !tasks.containsKey(id) ? id : generateTaskId();
    }
}
