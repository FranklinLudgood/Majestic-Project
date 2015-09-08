/*******************************************************
 * File Name: InGameAppState.java
 * Author: Franklin Ludgood
 * Date Created: 08-03-2015
 ********************************************************/
package AppStates;
import GameInput.GameOrientationListener;
import GameInput.GameTouchListner;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import org.dyn4j.dynamics.World;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.Settings;
import org.dyn4j.geometry.Mass;
import org.dyn4j.geometry.Vector2;
import com.jme3.math.FastMath;
//import GameObjects.Dyn4RigidBodyControl;
import com.jme3.cinematic.MotionPath;
//import GameObjects.PlayerControl;
import GameObjects.*;
import MessageSystem.GameContactListner;
import States.BallFalling;
import Utils.CreateBodyFixture;
import com.jme3.math.ColorRGBA;
import MessageSystem.*;
import com.jme3.animation.LoopMode;
import com.jme3.bounding.BoundingSphere;
import com.jme3.bounding.BoundingVolume;



public class InGameAppState extends AbstractAppState{
    
    
    private Application m_Application;
    private World m_2Dworld;
    private MessageCenter m_MessageCenter;
    private Node m_SceneNode;
    
    public void set2Dworld(World world){m_2Dworld = world;}
    public World getWorld(){return m_2Dworld;}
    
    public void setApplication(Application app){m_Application = app;}
    public Application getApplication(){return m_Application;}
    
    public void setNode(Node node){m_SceneNode = node;}
    public Node getNode(){return m_SceneNode;}
    
    
     @Override
    public void update(float tpf) {
       super.update(tpf);
       m_MessageCenter.update(tpf);
       double elapsedTime = (double) tpf;
       m_2Dworld.update(elapsedTime);
    }
    
   
    @Override
   public void cleanup() {
     super.cleanup();
     m_2Dworld.removeAllBodiesAndJoints();
     m_2Dworld = null;
     m_MessageCenter = null;
   }
    
    
    @Override
   public void initialize(AppStateManager stateManager, Application app) {
       super.initialize(stateManager, app);
       
      m_MessageCenter = MessageCenter.GetInstance();
       
      Settings worldSettings = new Settings();
      worldSettings.setStepFrequency((1/60.0));
      
      worldSettings.setAngularTolerance(0.05);
      worldSettings.setLinearTolerance(0.05);
      worldSettings.setAutoSleepingEnabled(true);
      worldSettings.setSleepAngularVelocity(0.1);
      worldSettings.setSleepLinearVelocity(0.1);
      
      m_Application = app;
      m_2Dworld = new World();
      m_2Dworld.setSettings(worldSettings);
      
      m_2Dworld.setGravity(new Vector2(0.0, -10.0));
      
      GameContactListner listner = new GameContactListner();
      m_2Dworld.addListener(listner);
      
      InitPhyisics2D();
       
    }
    
    
    private void InitPhyisics2D() {
        
        //setting up material
        Block.borderMaterial = new Material(m_Application.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        Block.borderMaterial.setColor("Color", ColorRGBA.Gray);
        
        Block.blueMaterial = new Material(m_Application.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        Block.blueMaterial.setColor("Color", ColorRGBA.Blue);
        
        Block.yellowMaterial = new Material(m_Application.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        Block.yellowMaterial.setColor("Color", ColorRGBA.Yellow);
        
        //Setting up a floor
        //Adding a floor
        Box floorBox = new Box(new Vector3f(-15.0f, -0.5f, -1.5f), new Vector3f(15.0f, 0.5f, 1.5f));
        Geometry floorGeom = new Geometry("floor", floorBox);
        BodyFixture floorFixuture = CreateBodyFixture.createBodyFixtureFromSpatial(floorBox, Vector2f.ZERO, 0.0f);
        Body floor2D = new Body();
        floor2D.addFixture(floorFixuture);
        floor2D.setMass(Mass.Type.INFINITE);
        Block floor = new Block(10.0f, floorGeom, floor2D, false, BaseGameEntity.ObjectType.BENIGN, null);
        floorGeom.setMaterial(Block.borderMaterial);
        floorGeom.addControl(floor);
        m_SceneNode.attachChild(floorGeom);
        m_2Dworld.addBody(floor2D);
        
        //Setting Ceiling
        //Adding a ceiling
        Geometry ceilingGeom = new Geometry("ceiling", floorBox);
        ceilingGeom.setMaterial(Block.borderMaterial);
        BodyFixture ceilFixture = CreateBodyFixture.createBodyFixtureFromSpatial(floorBox, Vector2f.ZERO, 0.0f);
        Body ceil2D = new Body();
        ceil2D.addFixture(ceilFixture);
        ceil2D.setMass(Mass.Type.INFINITE);
        ceil2D.getTransform().setTranslation(0.0, 15.0);
        ceilingGeom.getLocalTransform().setTranslation(new Vector3f(0.0f, 15.0f, 0.0f));
        Block ceiling  = new Block(10.0f, ceilingGeom, ceil2D, false, BaseGameEntity.ObjectType.BENIGN, null);
        ceilingGeom.addControl(ceiling);
        m_SceneNode.attachChild(ceilingGeom);
        m_2Dworld.addBody(ceil2D);
        
        //Adding sides to the game field.
        Box sideBox = new Box(new Vector3f(-.5f ,-15.0f  , -1.5f), new Vector3f(.5f,  15.0f, 1.5f));
        Geometry leftBoxGeom = new Geometry("LeftBorder", sideBox);
        leftBoxGeom.setMaterial(Block.borderMaterial);
        BodyFixture leftFixture = CreateBodyFixture.createBodyFixtureFromSpatial(sideBox, Vector2f.ZERO, 0.0f);
        leftBoxGeom.getLocalTransform().setTranslation(-15.5f, 0.0f, 0.0f);
        Body leftBox2D = new Body();
        leftBox2D.getTransform().setTranslation(-15.5, 0.0);
        leftBox2D.addFixture(leftFixture);
        leftBox2D.setMass(Mass.Type.INFINITE);
        Block leftBlock = new Block(10.0f, leftBoxGeom, leftBox2D, false, BaseGameEntity.ObjectType.BENIGN, null);
        leftBoxGeom.addControl(leftBlock);
        m_SceneNode.attachChild(leftBoxGeom);
        m_2Dworld.addBody(leftBox2D);
         
         
        Geometry rightBoxGeom = new Geometry("RightBorder", sideBox);
        rightBoxGeom.setMaterial(Block.borderMaterial);
        rightBoxGeom.getLocalTransform().setTranslation(15.5f, 0.0f, 0.0f);
        BodyFixture rightFixture = CreateBodyFixture.createBodyFixtureFromSpatial(sideBox, Vector2f.ZERO, 0.0f);
        Body rightBox2D = new Body();
        rightBox2D.getTransform().setTranslation(15.5, 0.0);
        rightBox2D.addFixture(leftFixture);
        rightBox2D.setMass(Mass.Type.INFINITE);
        Block rightBlock = new Block(10.0f, rightBoxGeom, rightBox2D, false, BaseGameEntity.ObjectType.BENIGN, null);
        rightBoxGeom.addControl(rightBlock);
        m_SceneNode.attachChild(rightBoxGeom);
        m_2Dworld.addBody(rightBox2D);
        
        
        setWorld();
        setPlayer();
               
    }
    
    private void setPlayer(){
        
        
        
        Material playerMat = new Material(m_Application.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        playerMat.setColor("Color", ColorRGBA.Red);
        Sphere playerSphere = new Sphere(25, 25, 1.0f);
        Geometry playerGeom = new Geometry("Player", playerSphere);
        playerGeom.setMaterial(playerMat);
        playerGeom.getLocalTransform().setTranslation(-10.0f, 6.0f, 0.0f);
        BodyFixture playerFix =  CreateBodyFixture.createBodyFixtureFromSpatial(playerSphere, Vector2f.ZERO);
        Body playerBody = new Body();
        playerBody.addFixture(playerFix);
        playerBody.setMass();
        playerBody.getTransform().setTranslation(-10.0, 11.0);
        PlayerControl control = new PlayerControl(playerGeom, playerBody);
        control.setState(BallFalling.GetInstance());
        control.getBody().setUserData(control);
        GameInput.GameInputManager.GetInstance().register((GameOrientationListener)control);
        GameInput.GameInputManager.GetInstance().register((GameTouchListner)control);
        playerGeom.addControl(control);
        m_2Dworld.addBody(playerBody);
        m_SceneNode.attachChild(playerGeom);
       
    
    }
    
    private void setWorld(){
        
         Box boxMesh = new Box(1.0f, 1.0f, 1.0f);
         Geometry pointBox = new Geometry("Point Box", boxMesh);
         pointBox.setMaterial(Block.yellowMaterial);
         BodyFixture pointFixture = CreateBodyFixture.createBodyFixtureFromSpatial(boxMesh, Vector2f.ZERO, 0.0f);
         Body pointBody = new Body();
         pointBody.addFixture(pointFixture);
         pointBody.setMass(Mass.Type.INFINITE);
         pointBody.getTransform().setTranslation(9.5, 5.5);
         Block pointControl = new Block(10.0f, pointBox, pointBody, false, BaseGameEntity.ObjectType.YELLOW_BLOCK, null);
         pointBox.addControl(pointControl);
         m_SceneNode.attachChild(pointBox);
         m_2Dworld.addBody(pointBody);
         
         
         Geometry pointChangeBox = new Geometry("Point Change Box", boxMesh);
         pointChangeBox.setMaterial(Block.yellowMaterial);
         BodyFixture pointChangeFixture = CreateBodyFixture.createBodyFixtureFromSpatial(boxMesh, Vector2f.ZERO, 0.0f);
         Body pointChangeBody = new Body();
         pointChangeBody.addFixture(pointChangeFixture);
         pointChangeBody.setMass(Mass.Type.INFINITE);
         pointChangeBody.getTransform().setTranslation(-9.5, 5.5);
         Block pointChangeControl = new Block(5.0f, pointChangeBox, pointChangeBody, true, BaseGameEntity.ObjectType.YELLOW_BLOCK, States.ChangingBlock.GetInstance());
         pointChangeBox.addControl(pointChangeControl);
         m_SceneNode.attachChild(pointChangeBox);
         m_2Dworld.addBody(pointChangeBody);
         
         
         Geometry pointMovingBox = new Geometry("Point Moving Box", boxMesh);
         pointMovingBox.setMaterial(Block.blueMaterial);
         BodyFixture pointMovingFixture = CreateBodyFixture.createBodyFixtureFromSpatial(boxMesh, Vector2f.ZERO, 0.0f);
         Body pointMovingBody = new Body();
         pointMovingBody.addFixture(pointMovingFixture);
         pointMovingBody.setMass(Mass.Type.INFINITE);
         pointMovingBody.getTransform().setTranslation(8.0, 5.0);
         MovingBlock moving = new MovingBlock(2.5f, pointMovingBox, pointMovingBody, true, BaseGameEntity.ObjectType.BLUE_BLOCK, 
                                                States.ChangingBlock.GetInstance(), 1.0f, 1.0f, LoopMode.Loop);
         
         MotionPath path = new MotionPath();
         for(int i = 0; i<8; ++i){
             path.addWayPoint(new Vector3f(FastMath.cos(FastMath.QUARTER_PI * i) * 5.0f, (FastMath.sin(FastMath.QUARTER_PI * i) * 5.0f) + 8.0f, 0.0f));
         }
         path.enableDebugShape(m_Application.getAssetManager(), m_SceneNode);
         moving.setPath(path);
         path.setCycle(true);
         pointMovingBox.addControl(moving);
         m_SceneNode.attachChild(pointMovingBox);
         m_2Dworld.addBody(pointMovingBody);
         moving.getEvent().play();
         
        
    }
    
    private void testMessageCenter(){
    
        //Testing timer function
        TimeTrigger timer = new TimeTrigger(60.0f, new TimedTriggered() {

            public void onTriggered() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }, true);
        m_MessageCenter.CreateTimeDelay(timer);
         
                
        //Testing area function
         Material ballMat = new Material(m_Application.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        ballMat.setColor("Color", ColorRGBA.Green);
        BoundingVolume volume =  new BoundingSphere(3.0f, new Vector3f(-2.5f, 5.5f, 0.0f));
        Sphere ball4Sphere = new Sphere(25, 25, 3.0f);
        Geometry ball4 = new Geometry("Ball3", ball4Sphere);
        ball4.setLocalTranslation(new Vector3f(-2.5f, 5.5f, 0.0f));
        ball4.setMaterial(ballMat);
       /* AreaTrigger areaTimer  = new AreaTrigger(volume,new TimedTriggered() {

            public void onTriggered() {
                throw new UnsupportedOperationException("Area Triggered"); //To change body of generated methods, choose Tools | Templates.
            }
        } , new Vector2f(-2.5f, 5.5f), "test", true); */
        
      // m_MessageCenter.CreateAreaTrigger(areaTimer);
       m_SceneNode.attachChild(ball4);
        
                
    }
    
    
}
