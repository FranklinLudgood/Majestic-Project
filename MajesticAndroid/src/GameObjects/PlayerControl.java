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
import com.jme3.math.Vector3f;
import com.jme3.math.Vector2f;
import States.StateInterface;


/**
 * TODO:Finish this class
 * make sure state interface is intialized.
 * Hax:
 */
public class PlayerControl extends Dyn4RigidBodyControl implements GameOrientationListener, GameTouchListner {
    
    private StateInterface m_state;
    private Vector3f m_DeviceOrientation;
    private Vector2f m_jumpNormal;
    private boolean m_TouchOccured;
    
    public PlayerControl(){
        m_DeviceOrientation = new Vector3f();
        m_jumpNormal = new Vector2f();
        m_jumpNormal.set(Vector2f.ZERO);
        m_TouchOccured = false;
    }
    
 
    public void setDeviceOrientation(Vector3f orientation){
        m_DeviceOrientation = orientation;
    }
    
    public void setDeviceOrientation(float x, float y, float z){
    
        if(m_DeviceOrientation == null)
            m_DeviceOrientation = new Vector3f(x, y, z);
        else
            m_DeviceOrientation.set(x, y, z);
    } 
    
    public Vector3f getDeviceOrientation(){return m_DeviceOrientation;}
    
    public void setJumpNormal(float x, float y){
        if(m_jumpNormal == null)
             m_jumpNormal = new Vector2f();
        
        m_jumpNormal.set(x, y);
    }
    
    public void setJumpNormal(Vector2f jump){
        m_jumpNormal = jump;
    }
    
    public Vector2f getJumpVector(){return m_jumpNormal;}
    
    public void setState(StateInterface state){m_state = state;}
    public StateInterface getState(){return m_state;}
    
    public void setTouchedOccured(boolean touch){m_TouchOccured = touch;}
    public boolean getTouchedOccured(){return m_TouchOccured;}

     @Override
    public void onChangedOrientation(float EulerX, float EulerY, float EulerZ) {
         m_state.onChangedOrientation(this, EulerX, EulerY, EulerZ);
    }

     @Override
    public void onTouch(TouchEvent event, float tpf) {
        m_state.onTouch(this, event, tpf); 
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
