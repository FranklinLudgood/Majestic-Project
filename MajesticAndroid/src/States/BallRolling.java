/*******************************************************
 * File Name: BallRolling.java
 * Author: Franklin Ludgood
 * Date Created: 08-10-2015
 *******************************************************/
package States;

import GameObjects.PlayerControl;
import GameObjects.PlayerProfile;
import MessageSystem.CollisionEvent;
import com.jme3.input.event.TouchEvent;
import java.lang.Math.*;
import org.dyn4j.geometry.Vector2;
import com.jme3.math.Vector2f;


public class BallRolling implements StateInterface {
    
    private static BallRolling m_state;
    
    
    private BallRolling(){ }
    
    public static BallRolling GetInstance(){
        
        if(m_state == null)
            m_state = new BallRolling();
        
        return m_state;
    }
    

    @Override
    public void EnterState(PlayerControl control, StateInterface exitState) {
        float yRot = control.getDeviceOrientation().y;
        control.getBody().setLinearVelocity(new Vector2(yRot * control.scale , 0.0));  
    }

    @Override
    public void ExitState(PlayerControl control, StateInterface enterState) {
        
    }

    @Override
    public StateInterface Update(PlayerControl control, float tpf) {
        
         float speed = (float) control.getBody().getLinearVelocity().getMagnitude();
         float yRot = -1.0f * control.getDeviceOrientation().y;
         control.getBody().applyImpulse(new Vector2(yRot * control.scale, 0.0));

         
         PlayerProfile profile = PlayerProfile.GetInstance();
         if(profile.tilt_coefficient < Math.abs(yRot))
             return BallSlowing.GetInstance();
         
          if(Math.abs(speed) >= control.maxSpeed){
                    control.getBody().getLinearVelocity().normalize();
                    control.getBody().getLinearVelocity().setMagnitude(control.maxSpeed);
                }
         
        
        if(control.getTouchedOccured() == true && Math.abs(control.getJumpVector().length()) > profile.isZero){
            Vector2f jump = control.getJumpVector();
            jump.multLocal(control.jumpScale);
            Vector2 impulse = new Vector2(jump.x, jump.y);
            control.getBody().applyImpulse(impulse);
            return BallFalling.getState();
        }
        
         if(Math.abs(control.getJumpVector().length()) < profile.isZero)
            return BallFalling.getState();
        
        control.setTouchedOccured(false);
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
         control.setJumpNormal(new Vector2f(normal));
    }

     @Override
    public void persistCollisionEvent(PlayerControl control, CollisionEvent event) {
         Vector2f normal = event.getCollisionNormal();
         control.setJumpNormal(new Vector2f(normal));       
    }

     @Override
    public void endCollisionEvent(PlayerControl control, CollisionEvent event) {
         
         if(event.getClosingSpeed() < 0.0f)
            control.setJumpNormal(new Vector2f(Vector2f.ZERO)); 
         
    }
    
}
