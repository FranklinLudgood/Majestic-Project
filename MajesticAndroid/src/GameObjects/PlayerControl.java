/*******************************************************
 * File Name: PlayerControl.java
 * Author: Franklin Ludgood
 * Date Created: 08-03-2015
 *******************************************************/
package GameObjects;
import GameInput.GameOrientationListener;
import GameInput.GameTouchListner;
import static GameObjects.BaseGameEntity.ObjectType.BLUE_BLOCK;
import static GameObjects.BaseGameEntity.ObjectType.BLUE_BUMPER;
import static GameObjects.BaseGameEntity.ObjectType.GRAVITY_BLOCK;
import static GameObjects.BaseGameEntity.ObjectType.YELLOW_BLOCK;
import static GameObjects.BaseGameEntity.ObjectType.YELLOW_BUMPER;
import MessageSystem.AreaTrigger;
import MessageSystem.CollisionEvent;
import MessageSystem.CollisionResponse;
import MessageSystem.MessageCenter;
import MessageSystem.GameBroadCast;
import com.jme3.input.event.TouchEvent;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector2f;
import States.StateInterface;
import com.jme3.scene.Spatial;
import org.dyn4j.dynamics.Body;
import MessageSystem.AreaTriggered;
//import org.dyn4j.dynamics.Force;
import org.dyn4j.geometry.Vector2;


public class PlayerControl extends Dyn4RigidBodyControl implements GameOrientationListener, GameTouchListner, BaseGameEntity, CollisionResponse, AreaTriggered {
    
    
    
    public static final String PlayerName = "PlayerControlBall";
    public static final int PlayerID = -1;
    public static final float scale = 1.0f;
    public static final float maxSpeed = 10.0f;
    public static final float jumpScale = 30.0f;
    public static final float gravityDelay = 2.5f;
    
    
    private StateInterface m_state;
    private Vector3f m_DeviceOrientation;
    private Vector2f m_jumpNormal;
    private int m_CurrentHealth;
    private int m_Score;
    private int m_Multipler;
    private boolean m_TouchOccured;
    private BaseGameEntity.ObjectType m_type;
    private float gravityTimer;
    
    public PlayerControl(){
        m_DeviceOrientation = new Vector3f();
        m_jumpNormal = new Vector2f();
        m_jumpNormal.set(Vector2f.ZERO);
        m_TouchOccured = false;
        m_CurrentHealth = PlayerProfile.GetInstance().max_health;
        m_type = BaseGameEntity.ObjectType.BLUE_BALL;
        m_Score = 0;
        m_Multipler = 1;
       gravityTimer = 0.0f;
    }
    
    public PlayerControl(Spatial spatial, Body body){
        super(spatial, body);
         m_DeviceOrientation = new Vector3f();
         m_jumpNormal = new Vector2f();
         m_jumpNormal.set(Vector2f.ZERO);
         m_TouchOccured = false;
         m_CurrentHealth = PlayerProfile.GetInstance().max_health;
         m_type = BaseGameEntity.ObjectType.BLUE_BALL;
         m_Score = 0;
         m_Multipler = 1;
         gravityTimer = 0.0f;
    }
    
 
    public void setDeviceOrientation(Vector3f orientation){
        m_DeviceOrientation = orientation;
    }
    
    public void setDeviceOrientation(float x, float y, float z){
    
        if(m_DeviceOrientation == null)
            m_DeviceOrientation = new Vector3f(x, y, z);
        else
            m_DeviceOrientation.set(x, y, z);
    } 
    
    public Vector3f getDeviceOrientation(){return m_DeviceOrientation;}
    
    public void setJumpNormal(float x, float y){
        if(m_jumpNormal == null)
             m_jumpNormal = new Vector2f();
        
        m_jumpNormal.set(x, y);
    }
    
    public void setJumpNormal(Vector2f jump){
        m_jumpNormal = jump;
    }
    
    public Vector2f getJumpVector(){return m_jumpNormal;}
    
    public void setState(StateInterface state){m_state = state;}
    public StateInterface getState(){return m_state;}
    
    public void setTouchedOccured(boolean touch){m_TouchOccured = touch;}
    public boolean getTouchedOccured(){return m_TouchOccured;}

     @Override
    public void onChangedOrientation(float EulerX, float EulerY, float EulerZ) {
         m_state.onChangedOrientation(this, EulerX, EulerY, EulerZ);
    }

     
     @Override
    public void onTouch(TouchEvent event, float tpf) {
        m_state.onTouch(this, event, tpf); 
    }
     
     //TODO: add custom render code here.
      @Override
     public void render(RenderManager rm, ViewPort vp) {
        
    }
      
      
     @Override
    public void update(float tpf) {
      super.update(tpf);
      
      if(gravityTimer > 0.0f)
          gravityTimer-=tpf;
      
      StateInterface newState = m_state.Update(this, tpf);
      if(newState != null){
          m_state.ExitState(this, newState);
          newState.EnterState(this, m_state);
          m_state = newState;
      }
     
    }  

    @Override
    public ObjectType getObjectType() { 
         return m_type; 
    }

    @Override
    public int getObjectID() {
         return PlayerID;
    }

     @Override
    public String getObjectName() {
        return PlayerName;
    }

     @Override
    public void beginCollisionEvent(CollisionEvent event) {
         
          BaseGameEntity entity = event.getBaseEntity();
          if(entity != null)
              ResolveCollision(entity.getObjectType());
          
          m_state.beginCollisionEvent(this, event);
    }

     @Override
    public void persistCollisionEvent(CollisionEvent event) {
         
          BaseGameEntity entity = event.getBaseEntity();
          if(entity != null)
              ResolveCollision(entity.getObjectType());
                      
          m_state.persistCollisionEvent(this, event);
    }

     @Override
    public void endCollisionEvent(CollisionEvent event) {
         m_state.endCollisionEvent(this, event);
    }

     @Override
    public BaseGameEntity getEntityID() {return this;}
     
    private void calculateScore(){
        m_Score+= m_Multipler * Block.basePointBlock;
        ++m_Multipler;
        GameBroadCast scoreCast = new GameBroadCast(null, new Integer(m_Score), GameBroadCast.BroadCastType.UPDATE_SCORE);
        GameBroadCast multiplyerCast = new GameBroadCast(null, new Integer(m_Multipler), GameBroadCast.BroadCastType.UPDATE_MULTIPLER);
        MessageCenter.GetInstance().SendBroadCast(scoreCast);
        MessageCenter.GetInstance().SendBroadCast(multiplyerCast);
    }
    
    private void calculateHealth(){
        
          --m_CurrentHealth;
                            
          if(m_CurrentHealth <= 0){
              GameBroadCast cast = new GameBroadCast(null, null, GameBroadCast.BroadCastType.GAME_LOST);
              MessageCenter.GetInstance().SendBroadCast(cast);
          }
          else{
              GameBroadCast cast = new GameBroadCast(null, new Integer(m_CurrentHealth), GameBroadCast.BroadCastType.UPDATE_HEALTH);
              MessageCenter.GetInstance().SendBroadCast(cast);
          }
    
    }
    
    private void ResolveCollision(BaseGameEntity.ObjectType type){
    
        switch(type){
                  
                  case YELLOW_BLOCK:
                      if(m_type == ObjectType.YELLOW_BALL)
                          calculateScore();
                      else if(m_type == ObjectType.BLUE_BALL)
                          calculateHealth();
                      break;
                      
                  case BLUE_BLOCK:
                      if(m_type == ObjectType.BLUE_BALL)
                          calculateScore();
                      else if(m_type == ObjectType.YELLOW_BALL)
                          calculateHealth();
                      break;
                      
                  case YELLOW_BUMPER:
                      m_type = ObjectType.YELLOW_BALL;
                      m_3dSpatial.setMaterial(Block.yellowMaterial);
                      break;
                      
                  case BLUE_BUMPER:
                      m_type = ObjectType.BLUE_BALL;
                      m_3dSpatial.setMaterial(Block.blueMaterial);
                      break;
                      
                  case GRAVITY_BLOCK:
                      break;
              }
    }

    @Override
    public void onTriggered(AreaTrigger trigger) {
        
        if(gravityTimer <= 0.0f){
            Vector3f position = m_3dSpatial.getWorldTranslation();
            Vector2f force = trigger.getPosition().subtract(position.x, position.y);
            //float distanceSquared = force.length();
            force.multLocal((GravityBlock.GravityConstant));
            m_2Dbody.applyImpulse(new Vector2((double) force.x, (double) force.y));
            gravityTimer = gravityDelay;
        }
        
    }
    
    
}
