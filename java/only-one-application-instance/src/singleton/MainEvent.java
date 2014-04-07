package singleton;

public class MainEvent extends Event<MainEvent.Type> {

    public static class Type extends Event.Type {

        public static MainEvent.Type INVOKATION = new MainEvent.Type(0x000001);

        public Type(int id) {
            super(id);
        }

    }

    public MainEvent(MainEvent.Type type) {
        super(type);
    }

}
