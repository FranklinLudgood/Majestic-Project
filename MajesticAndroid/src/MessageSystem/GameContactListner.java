/*******************************************************
 * File Name: GameContactListner.java
 * Author: Franklin Ludgood
 * Date Created: 08-09-2015
 *******************************************************/
package MessageSystem;
import GameObjects.BaseGameEntity;
import org.dyn4j.dynamics.contact.ContactListener;
import org.dyn4j.dynamics.contact.ContactPoint;
import org.dyn4j.dynamics.contact.PersistedContactPoint;
import org.dyn4j.dynamics.contact.SolvedContactPoint;
import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Vector2;
import com.jme3.math.Vector2f;

//TODO:Finish This Class.
public class GameContactListner implements ContactListener {
    
    private enum CollisionType {BEGIN, PERSIST, END}

    @Override
    public void sensed(ContactPoint point) {
         
    }

    @Override
    public boolean begin(ContactPoint point) {
        createCollisionEvents(point, CollisionType.BEGIN);
        return true;
    }

    @Override
    public void end(ContactPoint point) {
        createCollisionEvents(point, CollisionType.END); 
    }

    @Override
    public boolean persist(PersistedContactPoint point) {
        createCollisionEvents(point);
         return true;
    }

    @Override
    public boolean preSolve(ContactPoint point) {
        return true;
    }

    @Override
    public void postSolve(SolvedContactPoint point) {
       
    }
    
    private void createCollisionEvents(ContactPoint point, CollisionType type){
        
         Body body1 = point.getBody1();
         Body body2 = point.getBody2();
        
        Vector2 normal = point.getNormal();
        Vector2 position = point.getPoint();
        Vector2f contactNormal = new Vector2f((float)normal.x, (float)normal.y);
        Vector2f contactPoint = new Vector2f((float)position.x, (float)position.y);
        float penetration = (float) point.getDepth();
        
        Vector2f velocity1 = new Vector2f((float)body1.getLinearVelocity().x, (float)body1.getLinearVelocity().y);
        Vector2f velocity2 = new Vector2f((float)body2.getLinearVelocity().x, (float)body2.getLinearVelocity().y);
        Vector2f position1 = new Vector2f((float) body1.getTransform().getTranslationX(), (float)body1.getTransform().getTranslationY());
        Vector2f position2 = new Vector2f((float) body2.getTransform().getTranslationX(), (float)body2.getTransform().getTranslationY());
        Vector2f closingVel = velocity1.subtract(velocity2);
        Vector2f closingPos = position1.subtract(position2);
        float closingSpeed = -1.0f * closingVel.dot(closingPos);
        
        CollisionResponse collision1 = (CollisionResponse) body1.getUserData();
        CollisionResponse collision2 = (CollisionResponse) body2.getUserData();
        BaseGameEntity entity1 = null;
        BaseGameEntity entity2 = null;
        
        if(collision1 != null)
            entity1 = collision1.getEntityID();
        
        if(collision2 != null)
            entity2 = collision2.getEntityID();
        
        CollisionEvent event1 = new CollisionEvent(body2, entity2, contactNormal, contactPoint, penetration, closingSpeed);
        CollisionEvent event2 = new CollisionEvent(body1, entity1, contactNormal, contactPoint, penetration, closingSpeed);
        
        switch(type){
            case BEGIN:
                //TODO: add sound for collision
                if(collision1 != null)
                    collision1.beginCollisionEvent(event1);
                
                if(collision2 != null)
                    collision2.beginCollisionEvent(event2);
                
                break;
                
            case PERSIST:
                   if(collision1 != null)
                        collision1.persistCollisionEvent(event1); 
                
                   if(collision2 != null)
                        collision2.persistCollisionEvent(event2);
                break;
                
            case END:
                
                if(collision1 != null)
                    collision1.endCollisionEvent(event1);
                
                if(collision2 != null)
                    collision2.endCollisionEvent(event2);
                
                break;
        }
    }
        
     private void createCollisionEvents(PersistedContactPoint point){
             Body body1 = point.getBody1();
             Body body2 = point.getBody2();

           Vector2 normal = point.getNormal();
           Vector2 position = point.getPoint();
           Vector2f contactNormal = new Vector2f((float)normal.x, (float)normal.y);
           Vector2f contactPoint = new Vector2f((float)position.x, (float)position.y);
           float penetration = (float) point.getDepth();

           Vector2f velocity1 = new Vector2f((float)body1.getLinearVelocity().x, (float)body1.getLinearVelocity().y);
           Vector2f velocity2 = new Vector2f((float)body2.getLinearVelocity().x, (float)body2.getLinearVelocity().y);
           Vector2f position1 = new Vector2f((float) body1.getTransform().getTranslationX(), (float)body1.getTransform().getTranslationY());
           Vector2f position2 = new Vector2f((float) body2.getTransform().getTranslationX(), (float)body2.getTransform().getTranslationY());
           Vector2f closingVel = velocity1.subtract(velocity2);
           Vector2f closingPos = position1.subtract(position2);
           float closingSpeed = -1.0f * closingVel.dot(closingPos);

           CollisionResponse collision1 = (CollisionResponse) body1.getUserData();
           CollisionResponse collision2 = (CollisionResponse) body2.getUserData();
           BaseGameEntity entity1 = null;
           BaseGameEntity entity2 = null;
           
           
             if(collision1 != null)
               entity1 = collision1.getEntityID();

           if(collision2 != null)
               entity2 = collision2.getEntityID();
           
           CollisionEvent event1 = new CollisionEvent(body2, entity2, contactNormal, contactPoint, penetration, closingSpeed);
        CollisionEvent event2 = new CollisionEvent(body1, entity1, contactNormal, contactPoint, penetration, closingSpeed);

         
           
           if(collision1 != null)
                    collision1.persistCollisionEvent(event1);             
                
                if(collision2 != null)
                    collision2.persistCollisionEvent(event2);                     

           }
    
    
}
