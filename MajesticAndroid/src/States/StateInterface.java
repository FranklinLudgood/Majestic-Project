/*******************************************************
 * File Name: StateInterface.java
 * Author: Franklin Ludgood
 * Date Created: 08-09-2015
 *******************************************************/
package States;

import GameObjects.PlayerControl;
import com.jme3.input.event.TouchEvent;


public interface StateInterface {
    
    public void EnterState(PlayerControl control, StateInterface exitState);
    public void ExitState(PlayerControl control, StateInterface enterState);
    public StateInterface Update(PlayerControl control, float tpf);
    public void onChangedOrientation(float EulerX, float EulerY, float EulerZ);
    public void onTouch(TouchEvent event, float tpf);
    
    
}
