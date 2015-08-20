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
         PlayerProfile profile = PlayerProfile.GetInstance();
         
          if(Math.abs(speed) >= control.maxSpeed){
                  Vector2 linearVel = new Vector2(control.getBody().getLinearVelocity());
                  linearVel.normalize();
                  linearVel.setMagnitude(control.maxSpeed);
                  control.getBody().setLinearVelocity(linearVel);   
                }
          else {
              control.getBody().applyImpulse(new Vector2(yRot * control.scale, 0.0));
          }
          
          if(profile.tilt_coefficient > Math.abs(yRot))
             return BallSlowing.GetInstance();
          
          if(control.getTouchedOccured() == true && Math.abs(control.getJumpVector().length()) > profile.isZero){
            control.setTouchedOccured(false);
            double jumpX = (double) control.getJumpVector().x;
            double jumpY = (double) control.getJumpVector().y;
            Vector2 impulse = new Vector2(jumpX, jumpY);
            impulse.setMagnitude(control.jumpScale);
            control.getBody().applyImpulse(impulse);
            return BallFalling.GetInstance();
        }
          
          if(Math.abs(control.getJumpVector().length()) < profile.isZero)
            return BallFalling.GetInstance();

         /*
         PlayerProfile profile = PlayerProfile.GetInstance();
         if(profile.tilt_coefficient < Math.abs(yRot))
             return BallSlowing.GetInstance();
         
         
         
        
        
        
        
        
        */
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
