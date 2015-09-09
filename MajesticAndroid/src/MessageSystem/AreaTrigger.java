/*******************************************************
 * File Name: AreaTrigger.java
 * Author: Franklin Ludgood
 * Date Created: 08-23-2015
 *******************************************************/
package MessageSystem;
import com.jme3.bounding.BoundingVolume;
import java.util.List;
import java.util.ArrayList;
import com.jme3.scene.Spatial;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;


public class AreaTrigger {
    
    private BoundingVolume m_volume;
    private String m_filter;
    private Vector2f m_position;
    private boolean m_isActive;
    
    
    public AreaTrigger(){
    
        m_volume = null;
        m_filter = null;
        m_position = null;
        
    }
    
    public AreaTrigger(BoundingVolume volume, Vector2f position, String filter, boolean isActive){
        
        m_volume = volume;
        m_position = position;
        m_filter = filter;
        m_isActive = isActive;
        
        if(m_volume != null && m_position != null)
            m_volume.setCenter(new Vector3f(m_position.x, m_position.y, 0.0f));
        
       
        
    }
    

    public void update(AreaTriggered trigger) {
         
        if(m_isActive == false)
            return;
        
      
        if(m_volume != null){
            if(m_volume.contains(trigger.getSpatial().getWorldTranslation())){
                trigger.onTriggered(this);
            }
        }
    }
    
    
    public void setPosition(Vector2f position){
        m_position = position;
        if(m_volume != null && m_position != null)
            m_volume.setCenter(new Vector3f(m_position.x, m_position.y, 0.0f));
        
    }
    public Vector2f getPosition(){return m_position;}
    
    
    public void setFilter(String filter){m_filter = filter;}
    public String getFilter(){return m_filter;}
    
    public void setVolume(BoundingVolume volume){m_volume = volume;}
    public BoundingVolume getVolume(){return m_volume;}

    public boolean isActive() {return m_isActive;}

}
