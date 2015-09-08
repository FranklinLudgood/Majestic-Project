/*******************************************************
 * File Name: MovingBlock.java
 * Author: Franklin Ludgood
 * Date Created: 09-06-2015
 *******************************************************/
package GameObjects;
import States.BlockInterface;
import com.jme3.animation.LoopMode;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import org.dyn4j.dynamics.Body;



public class MovingBlock extends Block {
    
    private MotionPath m_path;
    private MotionEvent m_event;
    
    public MovingBlock(){
        super();
        m_path = null;
        m_event = null;
    }
    
    public MovingBlock(float delay, Spatial spatial, Body body, boolean enableTimer,
                    BaseGameEntity.ObjectType type, BlockInterface state, 
                    float pathDuration, float speed, LoopMode mode){
    
        super(delay, spatial, body, enableTimer, type, state);
       m_path = new MotionPath();
       m_event = new MotionEvent(spatial, m_path, pathDuration, mode);
       m_event.setInitialDuration(pathDuration);
       m_event.setSpeed(speed);
       m_event.setLoopMode(mode);
    
    }
    
    public void setPath(MotionPath path){
        m_path = path;
        m_event.setPath(path);
    }
    
    public MotionPath getPath(){return m_path;}
    
    public MotionEvent getEvent(){return m_event;}
    
     @Override
    public void update(float tpf){
      
        if(m_state != null){
       
            BlockInterface newState = m_state.Update(this, tpf);
            if(newState != null){
                m_state.Exit(this, newState);   
                newState.Enter(this, m_state);   
                m_state = newState;
            }
        
        }
        
        float[] euler = new float[3];
         Vector3f position = m_3dSpatial.getLocalTransform().getTranslation();
         m_3dSpatial.getLocalTransform().getRotation(m_Orientation);
         m_Orientation.toAngles(euler);
         
         m_2Dbody.getTransform().setTranslation((double) position.x,(double) position.y);
         m_2Dbody.getTransform().setRotation((double) euler[2]);
        
    }
}
