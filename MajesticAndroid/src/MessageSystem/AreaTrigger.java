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


public class AreaTrigger implements Triggerable {
    
    private List<Spatial> m_actors;
    private Triggered m_Triggered;
    private BoundingVolume m_volume;
    private String m_filter;
    private Vector2f m_position;
    private boolean m_isActive;
    
    
    public AreaTrigger(){
    
        m_volume = null;
        m_filter = null;
        m_position = null;
        
        m_actors = new ArrayList<Spatial>();
    
    }
    
    public AreaTrigger(BoundingVolume volume, Triggered triggered, Vector2f position, String filter, boolean isActive){
        
        m_volume = volume;
        m_Triggered = triggered;
        m_position = position;
        m_filter = filter;
        m_isActive = isActive;
        
        if(m_volume != null && m_position != null)
            m_volume.setCenter(new Vector3f(m_position.x, m_position.y, 0.0f));
        
        m_actors = new ArrayList<Spatial>();
        
    }
    

    public void update(float tpf) {
         
        if(m_isActive == false)
            return;
        
        for(int i = 0; i < m_actors.size(); ++i){
           
            Spatial actor = m_actors.get(i);
            if(m_isActive == true && m_volume != null && actor != null){
                if(m_volume.contains(actor.getWorldTranslation()))
                    OnTriggered();
            }
        
        }
    }
    
    public void addActor(Spatial actor){m_actors.add(actor);}
    
    public void removeActor(Spatial actor){m_actors.remove(actor);}
    
    public void setPosition(Vector2f position){
        m_position = position;
        if(m_volume != null && m_position != null)
            m_volume.setCenter(new Vector3f(m_position.x, m_position.y, 0.0f));
        
    }
    
    public void setTrigger(Triggered triggered) { m_Triggered = triggered;}
    
    public void setFilter(String filter){m_filter = filter;}
    public String getFilter(){return m_filter;}

    public boolean isActive() {return m_isActive;}

    public boolean isTriggered() {return m_isActive;}

    public void OnTriggered() {
         if(m_Triggered != null)
            m_Triggered.onTriggered();
    }
    
}
