/*******************************************************
 * File Name: PlayerControl.java
 * Author: Franklin Ludgood
 * Date Created: 08-03-2015
 *******************************************************/
package GameObjects;
import GameInput.GameOrientationListener;
import GameInput.GameTouchListner;
import com.jme3.input.event.TouchEvent;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import States.StateInterface;


/**
 * TODO:Finish this class
 * make sure state interface is intialized.
 * Hax:
 */
public class PlayerControl extends Dyn4RigidBodyControl implements GameOrientationListener, GameTouchListner {
    
    private StateInterface m_state;
    
   
    
    public void setState(StateInterface state){m_state = state;}
    public StateInterface getState(){return m_state;}

     @Override
    public void onChangedOrientation(float EulerX, float EulerY, float EulerZ) {
         m_state.onChangedOrientation(EulerX, EulerY, EulerZ);
    }

     @Override
    public void onTouch(TouchEvent event, float tpf) {
        m_state.onTouch(event, tpf); 
    }
     
     //TODO: add custom render code here.
      @Override
     public void render(RenderManager rm, ViewPort vp) {
        
    }
      
     @Override
    public void update(float tpf) {
      super.update(tpf);
      
      StateInterface newState = m_state.Update(this, tpf);
      if(newState != null){
          m_state.ExitState(this, newState);
          newState.EnterState(this, m_state);
          m_state = newState;
      }
    }  
    
}
