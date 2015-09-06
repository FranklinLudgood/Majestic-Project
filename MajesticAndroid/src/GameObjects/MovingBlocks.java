/*******************************************************
 * File Name: MovingBlocks.java
 * Author: Franklin Ludgood
 * Date Created: 09-05-2015
 *******************************************************/
package GameObjects;
import com.jme3.scene.Spatial;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.MotionPathListener;
import com.jme3.cinematic.events.MotionEvent;
import org.dyn4j.dynamics.Body;


public class MovingBlocks extends Dyn4RigidBodyControl {
    
    private MotionPath m_path;
    private MotionEvent m_event;
    
    public MovingBlocks(){
    
        m_path = null;
        m_event = null;
    }
    
    public MovingBlocks(Spatial spatial, Body body, MotionPath path, MotionEvent event){
        super(spatial, body);
        m_path = path;
        m_event = event;
    
    }
    
    
    
}
