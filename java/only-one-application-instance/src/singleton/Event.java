/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package singleton;

import java.io.Serializable;

/**
 *
 * @author adBesnard
 */
public class Event<T extends Event.Type> implements Serializable {

    public static class Type implements Serializable {
        private int id = 0x000000;
        public Type(int id) {
            super();
            this.id = id;
        }

        public boolean equals(Object object) {
            if (object != null && object instanceof Type) {
                Type that = (Type) object;
                return that.id == this.id;
            }
            
            return false;
        }
    }

    private T type;

    public Event(T type) {
        super();
        this.type = type;
    }

    public T getType() {
        return type;
    }

}
