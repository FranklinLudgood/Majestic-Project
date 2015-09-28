/*******************************************************
 * File Name: Block.java
 * Author: Franklin Ludgood
 * Date Created: 08-31-2015
 *******************************************************/
package GameObjects;
import MessageSystem.CollisionEvent;
import MessageSystem.CollisionResponse;
import States.BlockInterface;
import com.jme3.material.Material;
import com.jme3.scene.Spatial;
import org.dyn4j.dynamics.Body;
import GameObjects.LevelManager;


public class Block extends Dyn4RigidBodyControl implements BaseGameEntity, CollisionResponse {
    
    
    public static Material yellowMaterial;
    public static Material blueMaterial;
    public static Material borderMaterial;
    public static Material gravityMaterial;
    public static final int basePointBlock = 3;
    
    protected BlockInterface m_state; 
    protected String m_Name;
    protected int m_ID;
    protected float m_delay;
    protected float m_timer;
    protected BaseGameEntity.ObjectType m_type;

    public Block(){
        super();
        m_timer = m_delay = 0.0f;
        m_type = BaseGameEntity.ObjectType.BENIGN;
        m_state = null;
    }
    
    public Block(float delay, Spatial spatial, Body body, boolean enableTimer,
                    BaseGameEntity.ObjectType type, BlockInterface state) {
        super(spatial, body);
        m_delay = delay;
        m_timer = 0.0f;
        m_state = state;
        m_type = type;
        
        if(m_type == BaseGameEntity.ObjectType.PLAYER || m_type == BaseGameEntity.ObjectType.YELLOW_BUMPER
                || m_type == BaseGameEntity.ObjectType.BLUE_BUMPER){
            m_type = BaseGameEntity.ObjectType.BENIGN;
        }
    }
    
    public void setState(BlockInterface state){m_state = state;}
    public BlockInterface getState(){return m_state;}
    
    public void setDelay(float delay){m_delay = delay;}
    public float getDelay(){return m_delay;}
    
    public void setTimer(float timer){m_timer = timer;}
    public float getTimer(){return m_timer;}
    
    
    @Override
    public void update(float tpf){
        super.update(tpf);
        if(m_state != null){
       
            BlockInterface newState = m_state.Update(this, tpf);
            if(newState != null){
                m_state.Exit(this, newState);   
                newState.Enter(this, m_state);   
                m_state = newState;
            }
        
        }
        
    }

    @Override
    public ObjectType getObjectType() {return m_type;}
    public void setObjectType(ObjectType type){m_type = type;}

    @Override
    public int getObjectID() {return m_ID;}

    @Override
    public String getObjectName() {return m_Name;}

    public void beginCollisionEvent(CollisionEvent event) {
        
        BaseGameEntity entity = event.getBaseEntity();
        if(entity != null)
            ResolveCollision(entity.getObjectType());
        
    }

    public void persistCollisionEvent(CollisionEvent event) {
        BaseGameEntity entity = event.getBaseEntity();
        if(entity != null)
           ResolveCollision(entity.getObjectType());
            
    }

    @Override
    public void endCollisionEvent(CollisionEvent event) {
         
    }

    public BaseGameEntity getEntityID() { return this; }
    
    
    private void ResolveCollision(BaseGameEntity.ObjectType type){
    
        switch(type){
            case BLUE_BALL:
                    if(m_type == BaseGameEntity.ObjectType.BLUE_BLOCK){
                      LevelManager.GetInstance().RemoveObjectFromAll(this);
                    }
                  break;
                    
                case YELLOW_BALL:
                    if(m_type == BaseGameEntity.ObjectType.YELLOW_BLOCK){
                       LevelManager.GetInstance().RemoveObjectFromAll(this);
                    }
                   break;       
        }
    }
        
}
