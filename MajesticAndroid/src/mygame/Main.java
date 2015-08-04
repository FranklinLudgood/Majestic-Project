package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import AppStates.InGameAppState;
import org.dyn4j.dynamics.World;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
       
        //setting fly camera
         cam.setLocation(new Vector3f(0.0f, 15.0f, 10.0f));
        cam.lookAt(new Vector3f(0.0f, 0.0f, 0.0f), Vector3f.UNIT_Y);
        flyCam.setEnabled(true);
        flyCam.setMoveSpeed(5.0f);
        
        //Create and intialize InGameAppState
        InGameAppState state = new InGameAppState();
        state.setNode(rootNode);
        stateManager.attach(state);
        
        
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
