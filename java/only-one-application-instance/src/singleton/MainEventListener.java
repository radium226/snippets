/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package singleton;

/**
 *
 * @author adBesnard
 */
public interface MainEventListener extends EventListener<MainEvent> {

    public void handleEvent(MainEvent event);

}
