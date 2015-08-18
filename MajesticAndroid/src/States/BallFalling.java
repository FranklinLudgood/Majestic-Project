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
import MessageSystem.CollisionEvent;
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
         
        if(Math.abs(control.getJumpVector().length()) > 0.0f)
            return BallSlowing.GetInstance();
        
        return null;
    }

    @Override
    public void onChangedOrientation(PlayerControl control, float EulerX, float EulerY, float EulerZ) {
        control.setDeviceOrientation(0.0f, 0.0f, 0.0f);
    }

    @Override
    public void onTouch(PlayerControl control, TouchEvent event, float tpf) {
        
    }

    @Override
    public void beginCollisionEvent(PlayerControl control, CollisionEvent event) {
        
        Vector2f normal = event.getCollisionNormal();
        control.setJumpNormal(new Vector2f(normal));
         
    }

    @Override
    public void persistCollisionEvent(PlayerControl control, CollisionEvent event) {
         
    }

    @Override 
    public void endCollisionEvent(PlayerControl control, CollisionEvent event) {
         
    }
    
}
