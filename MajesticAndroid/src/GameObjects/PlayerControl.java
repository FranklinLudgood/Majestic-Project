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
//import com.jme3.math.Vector3f;

/**
 *
 * TODO:Finish this class
 * Hax:
 */
public class PlayerControl extends Dyn4RigidBodyControl implements GameOrientationListener, GameTouchListner {
    
    
    public PlayerControl(){
        
    }

     @Override
    public void onChangedOrientation(float EulerX, float EulerY, float EulerZ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

     @Override
    public void onTouch(TouchEvent event, float tpf) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     
      @Override
     public void render(RenderManager rm, ViewPort vp) {
        
    }
      
      @Override
    public void update(float tpf) {
      super.update(tpf);
      
      
      }  
    
}
