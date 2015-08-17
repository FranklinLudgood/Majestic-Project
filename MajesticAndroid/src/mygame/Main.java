/*******************************************************
 * File Name: Main.java
 * Author: Franklin Ludgood
 * Date Created: 08-03-2015
 ********************************************************/
package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import AppStates.InGameAppState;
import com.jme3.input.controls.TouchTrigger;
import GameInput.GameInputManager;


public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        
        //Adding touch into to Jmonkey
        inputManager.addMapping("Touch", new TouchTrigger(0));
        inputManager.addListener(GameInputManager.GetInstance(), new String[]{"Touch"});

       
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
