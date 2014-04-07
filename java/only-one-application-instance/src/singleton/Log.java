package singleton;

public class Log {

    public static void inform(String message) {
        System.out.println("[INFO][" + Thread.currentThread().getId() + "] " + message);
    }

}
