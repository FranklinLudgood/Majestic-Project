/*******************************************************
 * File Name: Main.java
 * Author: Franklin Ludgood
 * Date Created: 08-03-2015
 ********************************************************/
package mygame;

import com.jme3.app.SimpleApplication;
//import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import AppStates.InGameAppState;
import AppStates.OutOfGameState;
import com.jme3.input.controls.TouchTrigger;
import GameInput.GameInputManager;
import GameObjects.LevelManager;
import com.jme3.niftygui.NiftyJmeDisplay;


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
        flyCam.setEnabled(false);
        //flyCam.setMoveSpeed(5.0f);
        
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager,
                                                          inputManager,
                                                          audioRenderer,
                                                          guiViewPort);
        
        guiViewPort.addProcessor(niftyDisplay);
        
        
        LevelManager.GetInstance().setCamera("DefaultCam", cam);
        LevelManager.GetInstance().setApplication(this);
        LevelManager.GetInstance().setRenderManager(renderManager);
        LevelManager.GetInstance().setSceneNode(rootNode);
        LevelManager.GetInstance().setAssetManger(assetManager);
        
        
        
        //Create and intialize InGameAppState
        //InGameAppState state = new InGameAppState();
        //stateManager.attach(state);
        
        OutOfGameState state = new OutOfGameState();
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
