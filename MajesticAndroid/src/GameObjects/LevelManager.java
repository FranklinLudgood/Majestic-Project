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
import com.jme3.asset.AssetManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
//import java.util.Collection;
import java.util.Set;

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
    private AssetManager m_AssetManager;
    private NiftyJmeDisplay m_niftyDisplay;
    private ViewPort m_guiViewPort;
    
    
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
    
    public void setRenderManager(RenderManager manager){m_RenderManager = manager;}
    public RenderManager getRenderManager(){return m_RenderManager;}
    
    public void setAssetManger(AssetManager manager){m_AssetManager = manager;}
    public AssetManager getAssetManager(){return m_AssetManager;}
    
    
    public void setNiftyJmeDisplay(NiftyJmeDisplay display){m_niftyDisplay = display;}
    public NiftyJmeDisplay getNiftyJmeDisplay(){return m_niftyDisplay;}
    
    public void setGuiViewPort(ViewPort guiViewPort){ m_guiViewPort = guiViewPort;}
    public ViewPort getGuiViewPort(){return m_guiViewPort;}
    
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
    
    
    public void RegisterObject(String groupName, Dyn4RigidBodyControl body){
        
             BaseGameEntity entity = (BaseGameEntity)body;
             if(entity != null){
                if(m_gameObjects.containsKey(groupName) == true){
                    List<BaseGameEntity> list = m_gameObjects.get(groupName);
                    if(list.contains(entity) == false){
                        list.add(entity);
                    }
                } else {  
                     List<BaseGameEntity> list = new ArrayList<BaseGameEntity>();
                     list.add(entity);
                     m_gameObjects.put(groupName, list);
                }
             }
             
             if(m_inGameObjects.containsKey(groupName) == true){
                 List<Dyn4RigidBodyControl> list = m_inGameObjects.get(groupName);
                 if(list.contains(body) == false){
                     list.add(body);
                 }
             } else {  
               List<Dyn4RigidBodyControl> list = new ArrayList<Dyn4RigidBodyControl>();
               list.add(body);
               m_inGameObjects.put(groupName, list);
             }
             
             if(m_Spatials.containsKey(groupName) == true){
                 List<Spatial> list = m_Spatials.get(groupName);
                 if(list.contains(body.getSpatial()) == false){
                     list.add(body.getSpatial());
                 }
             } else {
                 List<Spatial> list = new ArrayList<Spatial>();
                 list.add(body.getSpatial());
                 m_Spatials.put(groupName, list);
             }
             
             m_2Dworld.addBody(body.getBody());
             m_SceneNode.attachChild(body.getSpatial());
    }
    
    public void RemoveObject(String groupName, Dyn4RigidBodyControl body){
        
        m_2Dworld.removeBody(body.getBody());
        m_SceneNode.detachChild(body.getSpatial());
        if(m_gameObjects.containsKey(groupName) == true){
          BaseGameEntity entity = (BaseGameEntity)body;
          if(entity != null){
            List<BaseGameEntity> list = m_gameObjects.get(groupName);
            list.remove(entity);
          }
        }
        
        if(m_inGameObjects.containsKey(groupName) == true){
           List<Dyn4RigidBodyControl> list = m_inGameObjects.get(groupName);
           list.remove(body);
        }
        
        if(m_Spatials.containsKey(groupName) == true){
            List<Spatial> list = m_Spatials.get(groupName);
            list.remove(body.getSpatial());
        }
    }
    
    public void RemoveObjectFromAll(Dyn4RigidBodyControl body){
    
        m_2Dworld.removeBody(body.getBody());
        m_SceneNode.detachChild(body.getSpatial());
        
        BaseGameEntity entity = (BaseGameEntity) body;
       if(m_gameObjects.containsValue(entity) == true){
           Set<String> list =  m_gameObjects.keySet();
           for(int i = 0; i < list.size(); ++i)
               m_gameObjects.get((String)list.toArray()[i]).remove(entity);
          }
       
    
      if(m_inGameObjects.containsValue(body) == true){
          Set<String> list = m_inGameObjects.keySet();
          for(int i = 0; i < list.size(); ++i)
              m_inGameObjects.get((String) list.toArray()[i]).remove(body);
      }
      
      
      if(m_Spatials.containsValue(body.getSpatial()) == true){
          Set<String> list = m_Spatials.keySet();
          for(int i = 0; i < list.size(); ++i)
              m_Spatials.get((String) list.toArray()[i]).remove(body.getSpatial());
      }
      
    }
    
    public List<Dyn4RigidBodyControl> getRigidBodys(String groupName){
        
        if(m_inGameObjects.containsKey(groupName) == true){
           return m_inGameObjects.get(groupName);
        }
        
        return null;
    }
    
    
            
    public void setSpatial(String groupName, Spatial spatial){
        
        if(m_Spatials.containsKey(groupName) == true){
            List<Spatial> list = m_Spatials.get(groupName);
            if(list.contains(spatial) == false){
                list.add(spatial);
            }
        } else{
             List<Spatial> list = new ArrayList<Spatial>();
             list.add(spatial);
             m_Spatials.put(groupName, list);
        }      
    }        
    
    
    public List<Spatial> getSpatials(String groupName){
    
        if(m_Spatials.containsKey(groupName) == true)
            return m_Spatials.get(groupName);
    
        return null;
    }
    
   
           
    public boolean setCamera(String groupName, Camera camera){
    
        if(m_Cameras.containsKey(groupName) == false){
            m_Cameras.put(groupName, camera);
            return true;
        }
        
        return false;
    }
    
    public Camera getCamera(String groupName){
    
        if(m_Cameras.containsKey(groupName) == true)
            return m_Cameras.get(groupName);
    
        return null;
    }
    
    
    public boolean setMotionPath(String groupName, MotionPath path){
    
        if(m_Paths.containsKey(groupName) == false){
             m_Paths.put(groupName, path);
             return true;
        }
        
        return false;
    }
    
    
    public MotionPath getMotionPath(String groupName){
    
        if(m_Paths.containsKey(groupName) == true)
            return m_Paths.get(groupName);
        
        return null;
    }
    
    
    public boolean setMotionEvent(String groupName, MotionEvent event){
        
       if(m_Events.containsKey(groupName) == false){
           m_Events.put(groupName, event);
           return false;
       }
    
        return false;
    }
    
    public MotionEvent getMotionEvent(String groupName){
    
        if(m_Events.containsKey(groupName) == true)
            return m_Events.get(groupName);
    
        return null;
    }
            

    @Override
    public ObjectType getObjectType() {return BaseGameEntity.ObjectType.LEVEL_MANAGER;}

    @Override
    public int getObjectID() {return levelID;}

    @Override
    public String getObjectName() {return Name;}
    
}
