package singleton;

import java.io.Serializable;

public interface EventListener<T extends Event> extends Serializable {

    public void handleEvent(T event);
}
