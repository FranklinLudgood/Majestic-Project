/*******************************************************
 * File Name: BallSlowing.java
 * Author: Franklin Ludgood
 * Date Created: 08-10-2015
 *******************************************************/
package States;

import GameObjects.PlayerControl;
import GameObjects.PlayerProfile;
import MessageSystem.CollisionEvent;
import com.jme3.input.event.TouchEvent;
import com.jme3.math.Vector2f;
import org.dyn4j.geometry.Vector2;


public class BallSlowing implements StateInterface {
    
    private static BallSlowing m_state;
    private BallSlowing(){}
    
    public static BallSlowing GetInstance(){
        
        if(m_state == null)
            m_state = new BallSlowing();
    
        return m_state;
    }

    @Override
    public void EnterState(PlayerControl control, StateInterface exitState) {
        
    }

    @Override
    public void ExitState(PlayerControl control, StateInterface enterState) {
        
    }

    @Override
    public StateInterface Update(PlayerControl control, float tpf) {
        
       float yRot = control.getDeviceOrientation().y;
       PlayerProfile profile = PlayerProfile.GetInstance();
        
       Vector2 drag =  new Vector2(control.getBody().getLinearVelocity());
       drag.normalize();
       drag.multiply(-1.0 * profile.stop_coefficient);
       control.getBody().applyImpulse(drag);
             
        
        if(control.getTouchedOccured() == true && Math.abs(control.getJumpVector().length()) > 0.0f){
            control.setTouchedOccured(false);
            double jumpX = (double) control.getJumpVector().x;
            double jumpY = (double) control.getJumpVector().y;
            Vector2 impulse = new Vector2(jumpX, jumpY);
            impulse.setMagnitude(control.jumpScale);
            control.getBody().applyImpulse(impulse);
            return BallFalling.GetInstance();
        }
        
        if(Math.abs(control.getJumpVector().length()) <= profile.isZero)
            return BallFalling.GetInstance();
        
        if(profile.tilt_coefficient < Math.abs(yRot))
             return BallRolling.GetInstance();
        
          
        return null;
    }

    @Override
    public void onChangedOrientation(PlayerControl control, float EulerX, float EulerY, float EulerZ) {
        control.setDeviceOrientation(EulerX, EulerY, EulerZ);
    }

    @Override
    public void onTouch(PlayerControl control, TouchEvent event, float tpf) {
       if(TouchEvent.Type.DOWN == event.getType())
             control.setTouchedOccured(true);
    }

     @Override
    public void beginCollisionEvent(PlayerControl control, CollisionEvent event) {
         Vector2f normal = event.getCollisionNormal();
         control.setJumpNormal(new Vector2f(-1.0f * normal.x , -1.0f * normal.y));
         
    }

     @Override
    public void persistCollisionEvent(PlayerControl control, CollisionEvent event) {
        Vector2f normal = event.getCollisionNormal();
        control.setJumpNormal(new Vector2f(-1.0f * normal.x , -1.0f * normal.y));    
    }

     @Override
    public void endCollisionEvent(PlayerControl control, CollisionEvent event) {
            control.setJumpNormal(new Vector2f(Vector2f.ZERO));
    }
    
}
