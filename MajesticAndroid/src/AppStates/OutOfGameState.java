/*******************************************************
 * File Name: OutOfGameState.java
 * Author: Franklin Ludgood
 * Date Created: 09-29-2015
 ********************************************************/
package AppStates;

import GameObjects.LevelManager;
import MessageSystem.GameContactListner;
import MessageSystem.MessageCenter;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import Utils.*;
import com.jme3.renderer.Camera;
import org.dyn4j.dynamics.Settings;
import org.dyn4j.geometry.Vector2;
import com.jme3.scene.shape.Box;
import com.jme3.math.Vector3f;
import de.lessvoid.nifty.Nifty;
import MessageSystem.MessageCenter;

//import MessageSystem.GameBroadCast;
//import com.jme3.niftygui.NiftyJmeDisplay;
//import de.lessvoid.nifty.elements.Element;


public class OutOfGameState extends AbstractAppState {
    
    private MessageCenter m_MessageCenter;
    private LevelManager m_LevelManager;
    
     
    
    @Override
    public void update(float tpf) {
       super.update(tpf);
       m_MessageCenter.update(tpf);
       double elapsedTime = (double) tpf;
       m_LevelManager.getWorld().update(elapsedTime);
    }
     
   @Override
   public void cleanup() {
          super.cleanup();
          
     m_LevelManager.ClearInGameObjects();
     m_LevelManager.ClearGameObjects();
     m_LevelManager.ClearSpatials();
     m_LevelManager.ClearPaths();
     m_LevelManager.ClearEvents();
     m_LevelManager.ClearPhysics();
     m_LevelManager.ClearScene();
             
     m_MessageCenter.ClearAll();
     
     m_MessageCenter = null;
     m_LevelManager = null;
   }
      
      
   @Override
   public void initialize(AppStateManager stateManager, Application app) {
       super.initialize(stateManager, app);
       
      m_MessageCenter = MessageCenter.GetInstance();
      m_LevelManager = LevelManager.GetInstance();
       
      Settings worldSettings = new Settings();
      worldSettings.setStepFrequency((1/60.0));
      
      worldSettings.setAngularTolerance(0.05);
      worldSettings.setLinearTolerance(0.05);
      worldSettings.setAutoSleepingEnabled(true);
      worldSettings.setSleepAngularVelocity(0.1);
      worldSettings.setSleepLinearVelocity(0.1);
      
      GameContactListner listner = new GameContactListner();
      
      m_LevelManager.getWorld().setGravity(new Vector2(0.0, -10.0));
      m_LevelManager.getWorld().setSettings(worldSettings);
      m_LevelManager.getWorld().addListener(listner);
      
      Nifty nifty = m_LevelManager.getNiftyJmeDisplay().getNifty();
      nifty.fromXml("Interface/Screens/IntroScreen.xml", "IntroScreen");
      nifty.addXml("Interface/Screens/SplashScreen.xml");
      nifty.addXml("Interface/Screens/StartMenu.xml");
      nifty.addXml("Interface/Screens/OutOfGameScreens.xml");
      
      
      setCamera();
      setGameBoundries();
      setBricks();
      setPlayer();
      
   }
     
     
   private void setCamera(){
   
      Camera  gameCamera = m_LevelManager.getCamera("DefaultCam");
              gameCamera.setLocation(new Vector3f(25.5f, 12.5f, 35.0f));
              gameCamera.lookAt(new Vector3f(25.0f, 12.5f, 0.0f), Vector3f.UNIT_Y);
   }
   
    private void setGameBoundries(){
        
        Box horizontalBox = new Box(13.0f, 0.5f, 0.5f);
        Box verticalBox = new Box(0.5f, 12.5f, 0.5f);
    
        CreateGameObjects.CreateBeninBlockStatic("floor", "Boundries", new Vector3f(13.0f, 0.5f, 0.0f), horizontalBox);
        CreateGameObjects.CreateBeninBlockStatic("ceiling", "Boundries", new Vector3f(13.0f, 25.5f, 0.0f), horizontalBox);
        CreateGameObjects.CreateBeninBlockStatic("leftSide", "Boundries", new Vector3f(0.5f, 12.5f, 0.0f), verticalBox);
        CreateGameObjects.CreateBeninBlockStatic("rightSide", "Boundries", new Vector3f(25.5f, 12.5f, 0.0f), verticalBox);
    }
    
    private void setPlayer(){
    
        CreateGameObjects.MakePlayerBall("PlayerBall", new Vector3f(13.0f, 19.0f, 0.0f), 1.0f);
    }
    
    
    
    private void setBricks(){
        
        Box smallBox = new Box(2.0f, 0.5f, 0.5f);
        Box bigBox = new Box(5.0f, 2.0f, 0.5f);
    
        CreateGameObjects.CreateBeninBlockStatic("Block1", "Benin", new Vector3f(13.0f, 2.0f, 0.0f), bigBox);
        CreateGameObjects.CreateBeninBlockStatic("Block2", "Benin", new Vector3f(3.0f, 5.0f, 0.0f), smallBox);
        CreateGameObjects.CreateBeninBlockStatic("Block3", "Benin", new Vector3f(24.0f, 5.0f, 0.0f), smallBox);
        CreateGameObjects.CreateBeninBlockStatic("Block4", "Benin", new Vector3f(13.0f, 10.0f, 0.0f), bigBox);
        
    }

}
