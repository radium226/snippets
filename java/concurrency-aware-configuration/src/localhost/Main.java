/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package localhost;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author adBesnard
 */
public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.go();
    }

    public Main() {
        super();
        config.put("key1", "value1");
        config.put("key2", "value2");
    }

    private Map<String, String> config = new HashMap<String, String>();
    private Lock configLock = new ReentrantLock(true);

    public static void printMap(Map<String, String> map) {
        for (Entry<String, String> mapEntry : map.entrySet()) {
            System.out.println("[" + Thread.currentThread().getId() + "] " + mapEntry.getKey() + " <-- " + mapEntry.getValue());
            sleep(1);
        }
    }

    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Log.inform("Bon... On arrête les frais.");
        }

    }

    public void modifyConfig(String key, String value) {
        System.out.print("[" + Thread.currentThread().getId() + "] Modifying configuration... ");
        sleep(5);
        config.put(key, value);
        System.out.println("Done! ");
    }

    public void go() {
        Thread firstThread = new Thread(new Runnable() {

            public void run() {
                while (true) {
                    Map<String, String> configCopy = null;
                    configLock.lock();
                    try {
                        configCopy = new HashMap<String, String>(config);
                        printMap(configCopy);
                    } finally {
                        configLock.unlock();
                    }
                    
                }
            }

        });

        Runnable modifyConfigTask = new Runnable() {

            public void run() {
                int i = 3;
                while (true) {
                    Random random = new Random();
                    sleep(random.nextInt(10) + 5);
                    configLock.lock();
                    try {
                        modifyConfig("key" + i, "value" + i);
                    } finally {
                        configLock.unlock();
                    }
                    i++;
                }
            }

        };

        Thread secondThread = new Thread(modifyConfigTask);
        Thread thirdThread = new Thread(modifyConfigTask);

        firstThread.start();
        secondThread.start();
        thirdThread.start();
    }

}
