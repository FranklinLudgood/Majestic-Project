/*******************************************************
 * File Name: Bumper.java
 * Author: Franklin Ludgood
 * Date Created: 09-15-2015
 *******************************************************/
package GameObjects;

import MessageSystem.CollisionEvent;
import MessageSystem.CollisionResponse;
import com.jme3.scene.Spatial;
import org.dyn4j.dynamics.Body;
import States.BumperInterface;


public class Bumper extends Dyn4RigidBodyControl implements BaseGameEntity, CollisionResponse  {
    
    private String m_name;
    private int m_ID;
    private BumperInterface m_state;
    private BaseGameEntity.ObjectType m_type;
    
    
    public Bumper(Spatial spatial, Body body, BaseGameEntity.ObjectType type, String name,
                  int id, BumperInterface state){
        
        super(spatial, body);
        m_name = name;
        m_ID = id;
        m_state = state;
        
        if(type != ObjectType.YELLOW_BUMPER && type != ObjectType.BLUE_BUMPER)
            m_type = ObjectType.BLUE_BUMPER;
        else
            m_type = type;
    
    }
    
    @Override
    public void update(float tpf){
         if(m_state != null){
       
            BumperInterface newState = m_state.Update(this, tpf);
            if(newState != null){
                m_state.Exit(this, newState);   
                newState.Enter(this, m_state);   
                m_state = newState;
            }
        
        }
    }
    
    public void setState(BumperInterface state){m_state = state;}
    public BumperInterface getState(){return m_state;}
            
    public void setObjectType(ObjectType type){m_type = type;}

    @Override
    public ObjectType getObjectType() {return m_type;}

    @Override
    public int getObjectID() { return m_ID;}

    @Override
    public String getObjectName() {return m_name;}

    public void beginCollisionEvent(CollisionEvent event) {
        //TODO: Add effects and animation
    }

    public void persistCollisionEvent(CollisionEvent event) {
          //TODO: Add effects and animation
    }

    public void endCollisionEvent(CollisionEvent event) {
         //TODO: Add effects and animation
    }

    @Override
    public BaseGameEntity getEntityID() {return this;}
    
}
