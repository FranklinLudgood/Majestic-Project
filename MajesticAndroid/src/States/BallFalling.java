/*******************************************************
 * File Name: BallFalling.java
 * Author: Franklin Ludgood
 * Date Created: 08-11-2015
 *******************************************************/
package States;

import GameObjects.PlayerControl;
import com.jme3.input.event.TouchEvent;
import com.jme3.math.Vector2f;
import GameObjects.PlayerProfile;
import java.lang.Math.*;

/**
 *
 * TODO:Finish this state
 */
public class BallFalling implements StateInterface {
    
    private static BallFalling m_state;
    private BallFalling(){}
    
    public static BallFalling getState(){
        
        if(m_state == null)
            m_state = new BallFalling();
    
        return m_state;
    }
    
    @Override
    public void EnterState(PlayerControl control, StateInterface exitState) {
         control.setTouchedOccured(false);
         control.setJumpNormal(Vector2f.ZERO);     
    }

    @Override
    public void ExitState(PlayerControl control, StateInterface enterState) {
         
    }

    @Override
    public StateInterface Update(PlayerControl control, float tpf) {
         
        if(Math.abs(control.getJumpVector().length()) > PlayerProfile.GetInstance().isZero)
            return BallSlowing.GetInstance();
        
        return null;
    }

    @Override
    public void onChangedOrientation(PlayerControl control, float EulerX, float EulerY, float EulerZ) {
        control.setDeviceOrientation(EulerX, EulerY, EulerZ);
    }

    @Override
    public void onTouch(PlayerControl control, TouchEvent event, float tpf) {
        
    }
    
}
