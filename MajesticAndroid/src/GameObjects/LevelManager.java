/*******************************************************
 * File Name: LevelManager.java
 * Author: Franklin Ludgood
 * Date Created: 08-30-2015
 *******************************************************/
package GameObjects;


import com.jme3.app.Application;
import java.util.HashMap;
import java.util.List;
import com.jme3.scene.Spatial;
import com.jme3.renderer.Camera;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.scene.Node;
import java.util.ArrayList;
import org.dyn4j.dynamics.World;
import com.jme3.renderer.RenderManager;

//TODO: Finish this class.
public class LevelManager implements BaseGameEntity {
    
    private HashMap<String, List<Dyn4RigidBodyControl>> m_inGameObjects;
    private HashMap<String, List<BaseGameEntity>> m_gameObjects;
    private HashMap<String, List<Spatial>> m_Spatials;
    private HashMap<String, Camera> m_Cameras;
    private HashMap<String, MotionPath> m_Paths;
    private HashMap<String, MotionEvent> m_Events;
    private Application m_Application;
    private World m_2Dworld;
    private Node m_SceneNode;
    private RenderManager m_RenderManager;
    
    
    private static LevelManager m_levelManager;
    private static final String Name = "LevelManager";
    private static final int levelID = -2;
    private LevelManager() {
        
        m_inGameObjects = new HashMap<String, List<Dyn4RigidBodyControl>>();
        m_gameObjects = new HashMap<String, List<BaseGameEntity>>();
        m_Spatials = new HashMap<String, List<Spatial>>();
        m_Cameras = new HashMap<String, Camera>();
        m_Paths = new HashMap<String, MotionPath>();
        m_Events = new HashMap<String, MotionEvent>();
        m_2Dworld = new World();
    }
    
    
    public static LevelManager GetInstance(){
        
        if(m_levelManager == null)
            m_levelManager = new LevelManager();
    
        return m_levelManager;
    }
    
    public void setApplication(Application app){m_Application = app;}
    public Application getApplication(){return m_Application;}
    
    public World getWorld(){return m_2Dworld;}
    
    public void setSceneNode(Node node){m_SceneNode = node;}
    public Node getNode(){return m_SceneNode;}
    
    
    //TODO: finish this function
    public void LoadLevel(String fileName){
    
    }
    
    public void update(float tpf){
        m_2Dworld.update((double) tpf);
    }
    
    public void RegisterObject(String groupName, BaseGameEntity entity){
        
        if(m_gameObjects.containsKey(groupName) == true){
            List<BaseGameEntity> list = m_gameObjects.get(groupName);
            if(list.contains(entity) == false)
                list.add(entity);
            
        } else {
        
            List<BaseGameEntity> list = new ArrayList<BaseGameEntity>();
            list.add(entity);
            m_gameObjects.put(groupName, list);
        }
    }
    
    
    public void RemoveObject(String groupName, BaseGameEntity entity){
        if(m_gameObjects.containsKey(groupName) == true){
          List<BaseGameEntity> list = m_gameObjects.get(groupName);
          list.remove(entity);
        }
    }
    
    public List<BaseGameEntity> getBaseEntitys(String groupName){
    
        if(m_gameObjects.containsKey(groupName) == true)
            return m_gameObjects.get(groupName);
    
        return null;
    }
    
    //TODO: Finish Regestering Objects
    //Dyn4RigidBodyControl need to add
    //to the 2D world as well as BaseEntitys.
    //and Spacial.
    public void RegisterObject(String groupName, Dyn4RigidBodyControl body){
        
        
    
    }
    

    @Override
    public ObjectType getObjectType() {return BaseGameEntity.ObjectType.LEVEL_MANAGER;}

    @Override
    public int getObjectID() {return levelID;}

    @Override
    public String getObjectName() {return Name;}
    
}
