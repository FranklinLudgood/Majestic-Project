/*******************************************************
 * File Name: CollisionEvent.java
 * Author: Franklin Ludgood
 * Date Created: 08-09-2015
 *******************************************************/
package MessageSystem;
import org.dyn4j.dynamics.Body;
import com.jme3.math.Vector2f;
import GameObjects.BaseGameEntity;


public class CollisionEvent {
    
   private Body m_body;
   private BaseGameEntity m_entity;
   private Vector2f m_collisionNormal;
   private Vector2f m_collisionPoint;
   private float m_penetration;
   private float m_closingSpeed;
   
   
   public CollisionEvent(Body body, BaseGameEntity entity, Vector2f normal,
                            Vector2f point, float penetration, float closingSpeed){
   
       m_body = body;
       m_entity = entity;
       m_collisionNormal = normal;
       m_collisionPoint = point;
       m_penetration = penetration;
       m_closingSpeed = closingSpeed;
   
   }
   
   public Body getBody(){return m_body;}
   public BaseGameEntity getBaseEntity(){return m_entity;}
   public Vector2f getCollisionNormal(){return m_collisionNormal;}
   public Vector2f getCollisionPoint(){return m_collisionPoint;}
   public float getPenetration(){return m_penetration;}
   public float getClosingSpeed(){return m_closingSpeed;}
    
}
