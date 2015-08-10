/*******************************************************
 * File Name: BallRolling.java
 * Author: Franklin Ludgood
 * Date Created: 08-10-2015
 *******************************************************/
package States;

import GameObjects.PlayerControl;
import com.jme3.input.event.TouchEvent;

/**
 * TODO: Finish this class
 */
public class BallRolling implements StateInterface {
    
    private static BallRolling m_state;
    
    private BallRolling(){ }
    
    public static BallRolling getState(){
        
        if(m_state == null)
            m_state = new BallRolling();
        
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
