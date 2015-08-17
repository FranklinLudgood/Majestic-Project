/*******************************************************
 * File Name: StateInterface.java
 * Author: Franklin Ludgood
 * Date Created: 08-09-2015
 *******************************************************/
package States;

import GameObjects.PlayerControl;
import MessageSystem.CollisionEvent;
import com.jme3.input.event.TouchEvent;


public interface StateInterface {
    
    public void EnterState(PlayerControl control, StateInterface exitState);
    public void ExitState(PlayerControl control, StateInterface enterState);
    public StateInterface Update(PlayerControl control, float tpf);
    public void onChangedOrientation(PlayerControl control, float EulerX, float EulerY, float EulerZ);
    public void onTouch(PlayerControl control, TouchEvent event, float tpf);
    public void beginCollisionEvent(PlayerControl control, CollisionEvent event);
    public void persistCollisionEvent(PlayerControl control, CollisionEvent event);
    public void endCollisionEvent(PlayerControl control, CollisionEvent event);
    
}
