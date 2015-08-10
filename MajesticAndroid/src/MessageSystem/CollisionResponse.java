/*******************************************************
 * File Name: CollisionResponse.java
 * Author: Franklin Ludgood
 * Date Created: 08-10-2015
 *******************************************************/
package MessageSystem;
import GameObjects.BaseGameEntity;

public interface CollisionResponse {
    
    public void beginCollisionEvent(CollisionEvent event);
    public void persistCollisionEvent(CollisionEvent event);
    public void endCollisionEvent(CollisionEvent event);
    public BaseGameEntity getEntityID();
    
}
