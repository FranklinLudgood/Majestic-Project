/*******************************************************
 * File Name: AreaTriggered.java
 * Author: Franklin Ludgood
 * Date Created: 09-07-2015
 *******************************************************/
package MessageSystem;

import com.jme3.scene.Spatial;


public interface AreaTriggered {
    
    public void onTriggered(AreaTrigger trigger);
    public Spatial getSpatial();
}
