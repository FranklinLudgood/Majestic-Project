/*******************************************************
 * File Name: BallRolling.java
 * Author: Franklin Ludgood
 * Date Created: 08-10-2015
 *******************************************************/
package States;

import GameObjects.PlayerControl;
import GameObjects.PlayerProfile;
import com.jme3.input.event.TouchEvent;
import java.lang.Math.*;
import org.dyn4j.geometry.Vector2;

/**
 * TODO: Finish this class
 */
public class BallRolling implements StateInterface {
    
    private static BallRolling m_state;
    public static final float scale = 10.0f;
    public static final float maxSpeed = 40.0f;
    public static final float jumpScale = 60.0f;
    
    private BallRolling(){ }
    
    public static BallRolling getState(){
        
        if(m_state == null)
            m_state = new BallRolling();
        
        return m_state;
    }

    //TODO:Finish this function.
    public void EnterState(PlayerControl control, StateInterface exitState) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //TODO:Finish this function
    public void ExitState(PlayerControl control, StateInterface enterState) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public StateInterface Update(PlayerControl control, float tpf) {
        
        PlayerProfile profile = PlayerProfile.GetInstance();
        
          if(control.getTouchedOccured() == true && Math.abs(control.getJumpVector().length()) > profile.isZero)
            return BallFalling.getState();
        
        if(control.getDeviceOrientation() != null){
            float yRot = control.getDeviceOrientation().y;
            float speed = (float) control.getBody().getLinearVelocity().getMagnitude();
            if(profile.tilt_coefficient < Math.abs(yRot)){
                
                if(Math.abs(speed) >= maxSpeed){
                    control.getBody().getLinearVelocity().normalize();
                    control.getBody().getLinearVelocity().setMagnitude(maxSpeed);
                }
                else if(Math.abs(speed) < profile.isZero){
                        control.getBody().setLinearVelocity(new Vector2(yRot * scale, 0.0));
                }
                else if(Math.abs(speed) > profile.isZero && Math.abs(speed) < maxSpeed)
                    control.getBody().applyImpulse(new Vector2(yRot * scale, 0.0));
            }else if(profile.tilt_coefficient > Math.abs(yRot) && Math.abs(speed) > profile.isZero){
                     Vector2 DragCoefficient = control.getBody().getLinearVelocity();
                     DragCoefficient.setMagnitude(-1.0 * profile.stop_coefficient);
                     control.getBody().applyForce(DragCoefficient);
            } else if(profile.tilt_coefficient > Math.abs(yRot) && Math.abs(speed) < profile.isZero)
                        control.getBody().getLinearVelocity().set(0.0, 0.0);
            
        }
        
                      
        return null;
    }

     @Override
    public void onChangedOrientation(PlayerControl control, float EulerX, float EulerY, float EulerZ) {
        control.setDeviceOrientation(EulerX, EulerY, EulerZ);
    }

    public void onTouch(PlayerControl control, TouchEvent event, float tpf) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
