/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package singleton;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author adbesnard
 */
public class Threads {

    private static ExecutorService executor;

    public static int MAX_THREAD_COUNT=5;

    static {
        init();
    }

    public static void init() {
        executor = Executors.newFixedThreadPool(MAX_THREAD_COUNT);
    }

    public static void start(Runnable runnable) {
        executor.execute(runnable);
    }

    public static void interrupt() {
        List<Runnable> awaitingRunnables = executor.shutdownNow();
        Log.inform("Il restait " + awaitingRunnables.size() + " tâches à réaliser. ");
    }

}
