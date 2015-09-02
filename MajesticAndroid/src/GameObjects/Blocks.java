/*******************************************************
 * File Name: Blocks.java
 * Author: Franklin Ludgood
 * Date Created: 08-31-2015
 *******************************************************/
package GameObjects;
import MessageSystem.CollisionEvent;
import MessageSystem.CollisionResponse;
import MessageSystem.GameBroadCast;
import com.jme3.material.Material;
import com.jme3.scene.Spatial;
import org.dyn4j.dynamics.Body;

//TODO: finish this function;

public class Blocks extends Dyn4RigidBodyControl implements BaseGameEntity, CollisionResponse {
    
    
    public static Material yellowMaterial;
    public static Material blueMaterial;
    public static Material borderMaterial;
    public static final int basePointBlock = 3;
    
    private String m_Name;
    private int m_ID;
    private float m_delay;
    private float m_timer;
    private boolean m_enableTimer;
    private BaseGameEntity.ObjectType m_type;

    public Blocks(){
        super();
        m_timer = m_delay = 0.0f;
        m_enableTimer = false;
        m_type = BaseGameEntity.ObjectType.BENIGN;
    }
    
    public Blocks(float delay, Spatial spatial, Body body, boolean enableTimer,
                    BaseGameEntity.ObjectType type) {
        super(spatial, body);
        m_delay = delay;
        m_timer = 0.0f;
        m_enableTimer = enableTimer;
        m_type = type;
        
        if(m_type == BaseGameEntity.ObjectType.PLAYER || m_type == BaseGameEntity.ObjectType.YELLOW_BUMPER
                || m_type == BaseGameEntity.ObjectType.BLUE_BUMPER){
            m_type = BaseGameEntity.ObjectType.BENIGN;
        }
    }
    
    @Override
    public void update(float tpf){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ObjectType getObjectType() {return m_type;}

    @Override
    public int getObjectID() {return m_ID;}

    @Override
    public String getObjectName() {return m_Name;}

    public void beginCollisionEvent(CollisionEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void persistCollisionEvent(CollisionEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void endCollisionEvent(CollisionEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public BaseGameEntity getEntityID() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
}
