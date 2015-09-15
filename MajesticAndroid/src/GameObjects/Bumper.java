/*******************************************************
 * File Name: Bumper.java
 * Author: Franklin Ludgood
 * Date Created: 09-15-2015
 *******************************************************/
package GameObjects;

import com.jme3.scene.Spatial;
import org.dyn4j.dynamics.Body;
import States.BumperInterface;


public class Bumper extends Dyn4RigidBodyControl implements BaseGameEntity  {
    
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
    
    public void setObjectType(ObjectType type){m_type = type;}

    @Override
    public ObjectType getObjectType() {return m_type;}

    @Override
    public int getObjectID() { return m_ID;}

    @Override
    public String getObjectName() {return m_name;}
    
}
