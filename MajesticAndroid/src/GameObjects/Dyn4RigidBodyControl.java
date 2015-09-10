/*******************************************************
 * File Name: Dyn4RigidBodyControl.java
 * Author: Franklin Ludgood
 * Date Created: 08-03-2015
 *******************************************************/
package GameObjects;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import java.io.IOException;
import org.dyn4j.dynamics.Body;



public class Dyn4RigidBodyControl implements Control {
    
   
    protected Body m_2Dbody;
    protected Spatial m_3dSpatial;
    protected Quaternion m_Orientation;
    
    
    public Dyn4RigidBodyControl(){
        m_2Dbody = null;
        m_3dSpatial = null;
        m_Orientation = new Quaternion();
    }
    
    
    public Dyn4RigidBodyControl(Spatial spatial, Body body){
    
        m_2Dbody = body;
        m_3dSpatial = spatial;
        m_Orientation = new Quaternion();
        
    }

    public Control cloneForSpatial(Spatial spatial) {
        return null;
    }

    public void setSpatial(Spatial spatial) {
        m_3dSpatial = spatial;
    }
    
    public Spatial getSpatial(){return m_3dSpatial;}
    
    
    public void setBody(Body body){
        m_2Dbody = body;
    }
    
    public Body getBody(){return m_2Dbody;}
    
    public void setControl(Body body, Spatial spatial){
    
        m_2Dbody = body;
        m_3dSpatial = spatial;
    }

    
    public void update(float tpf) {
        
     double xPos =  m_2Dbody.getTransform().getTranslationX();
     double yPos = m_2Dbody.getTransform().getTranslationY();
     double zRot =   m_2Dbody.getTransform().getRotation();
     m_Orientation.fromAngleAxis((float) zRot, Vector3f.UNIT_Z);
     
     m_3dSpatial.setLocalTranslation((float) xPos, (float) yPos, 0.0f);
     m_3dSpatial.setLocalRotation(m_Orientation);
        
    }

    public void render(RenderManager rm, ViewPort vp) {
        
    }

    public void write(JmeExporter ex) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void read(JmeImporter im) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
