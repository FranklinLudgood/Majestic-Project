/*******************************************************
 * File Name: OutOfGameState.java
 * Author: Franklin Ludgood
 * Date Created: 09-29-2015
 ********************************************************/
package AppStates;

import GameObjects.LevelManager;
import GameObjects.PlayerControl;
import MessageSystem.MessageCenter;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.renderer.Camera;


public class OutOfGameState extends AbstractAppState {
    
    private MessageCenter m_MessageCenter;
    private LevelManager m_LevelManager;
    private Camera m_playerCamera;
    private PlayerControl m_Control;
     
    
     @Override
    public void update(float tpf) {}
     
      @Override
   public void cleanup() {}
      
      
     @Override
   public void initialize(AppStateManager stateManager, Application app) {}
     
     
   private void setCamera(){}
   
    private void setGameBoundries(){}
    
    private void setPlayer(){}
    
    private void setBricks(){}
}
