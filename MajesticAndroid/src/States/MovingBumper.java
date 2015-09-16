/*******************************************************
 * File Name: MovingBumper.java
 * Author: Franklin Ludgood
 * Date Created: 09-15-2015
 *******************************************************/
package States;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.events.MotionEvent;
import GameObjects.Bumper;
import com.jme3.animation.LoopMode;
import com.jme3.math.Vector3f;
import com.jme3.math.Quaternion;


public class MovingBumper implements BumperInterface {
    
    private MotionPath m_path;
    private MotionEvent m_event;
    
    public MovingBumper(MotionPath path, Bumper bumper, LoopMode loop){
        m_path = path;
        m_event = new MotionEvent(bumper.getSpatial(), m_path, loop);
    }
    
    
    public void setPath(MotionPath path){m_path = path;}
    public MotionPath getPath(){return m_path;}
    
    public void setEvent(MotionEvent event){m_event = event;}
    public MotionEvent getEvent(){return m_event;}
    

    @Override
    public void Enter(Bumper bumper, BumperInterface state) {
        
    }

    @Override
    public BumperInterface Update(Bumper bumper, float tpf) {
        
        float[] euler = new float[3];
        Quaternion Orientation = new Quaternion();
        Vector3f position = bumper.getSpatial().getLocalTransform().getTranslation();
        bumper.getSpatial().getLocalTransform().getRotation(Orientation);
        Orientation.toAngles(euler);
         
         bumper.getBody().getTransform().setTranslation((double) position.x,(double) position.y);
         bumper.getBody().getTransform().setRotation((double) euler[2]);
        
        return null;
    }

    @Override
    public void Exit(Bumper bumper, BumperInterface state) {
        
    }
    
}
