/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package States;

import GameObjects.PlayerControl;
import com.jme3.input.event.TouchEvent;

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
    

    public void EnterState(PlayerControl control, StateInterface exitState) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void ExitState(PlayerControl control, StateInterface enterState) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public StateInterface Update(PlayerControl control, float tpf) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void onChangedOrientation(float EulerX, float EulerY, float EulerZ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void onTouch(TouchEvent event, float tpf) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
