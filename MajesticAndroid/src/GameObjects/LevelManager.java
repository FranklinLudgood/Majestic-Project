/*******************************************************
 * File Name: LevelManager.java
 * Author: Franklin Ludgood
 * Date Created: 08-30-2015
 *******************************************************/
package GameObjects;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

//TODO: Finish this class.
public class LevelManager {
    
    private HashMap<String, List<Dyn4RigidBodyControl>> m_inGameObjects;
    private HashMap<String, List<BaseGameEntity>> m_gameObjects;
    
    private static LevelManager m_levelManager;
    private LevelManager() {
        
        m_inGameObjects = new HashMap<String, List<Dyn4RigidBodyControl>>();
        m_gameObjects = new HashMap<String, List<BaseGameEntity>>();
        
    }
    
    public static LevelManager GetInstance(){
        
        if(m_levelManager == null)
            m_levelManager = new LevelManager();
    
        return m_levelManager;
    }
    
    public void LoadLevel(String fileName){
    
    }
    
    public void update(float tpf){
    
    }
    
    public void RegisterObject(String groupName, BaseGameEntity entity){
    
    
    }
    
    public void RegisterObject(String groupName, Dyn4RigidBodyControl body){
    
    }
    
    public void getStatus(){}
    
}
