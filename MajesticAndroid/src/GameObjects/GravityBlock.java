/*******************************************************
 * File Name: GravityBlock.java
 * Author: Franklin Ludgood
 * Date Created: 09-09-2015
 *******************************************************/
package GameObjects;
import MessageSystem.AreaTrigger;
import States.BlockInterface;
import com.jme3.bounding.BoundingSphere;
import com.jme3.math.Vector2f;
import com.jme3.scene.Spatial;
import org.dyn4j.dynamics.Body;

public class GravityBlock extends Block {
    
    public static final String FILTER = "GravityBlock";
    public static final float GravityConstant = 50.0f;
    
    private AreaTrigger m_trigger;
    
    public GravityBlock(){
    
        super();
        m_trigger = null;
    }
    
    public GravityBlock(float radius, float delay, Spatial spatial, Body body, 
            boolean enableTimer, BlockInterface state, boolean gravityEnable){
        
        super(delay, spatial, body, enableTimer, BaseGameEntity.ObjectType.GRAVITY_BLOCK, state);
        BoundingSphere sphere =  new BoundingSphere(radius, spatial.getWorldTranslation());
        Vector2f position = new Vector2f((float) body.getTransform().getTranslationX(), (float) body.getTransform().getTranslationY());
        m_trigger = new AreaTrigger(sphere, position, FILTER, gravityEnable);
                                        
    }
    
    public void SetTrigger(AreaTrigger trigger){m_trigger = trigger;}
    public AreaTrigger getTrigger(){return m_trigger;}
    
}
