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
import com.jme3.cinematic.MotionPath;
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
    private PlayerControl m_play;
    
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
        
        Block.gravityMaterial = new Material(m_Application.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        Block.gravityMaterial.setColor("Color", ColorRGBA.Pink);
        
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
        m_play = control;
    
    }
    
    private void setWorld(){
        
         Box boxMesh = new Box(1.0f, 1.0f, 1.0f);
         Geometry gravityBox = new Geometry("Gravity Box", boxMesh);
         gravityBox.setMaterial(Block.gravityMaterial);
         BodyFixture gravityFixture = CreateBodyFixture.createBodyFixtureFromSpatial(boxMesh, Vector2f.ZERO, 0.0f);
         Body gravityBody = new Body();
         gravityBody.addFixture(gravityFixture);
         gravityBody.setMass(Mass.Type.INFINITE);
         gravityBody.getTransform().setTranslation(0.0, 5.5);
         GravityBlock gravityControl = new GravityBlock(6.0f, 10.0f, gravityBox, gravityBody, false, null, true);
         //m_MessageCenter.CreateAreaTrigger(gravityControl.getTrigger());
         //m_MessageCenter.AddActor(m_play, gravityControl.getTrigger().getFilter());
         gravityBox.addControl(gravityControl);
         m_SceneNode.attachChild(gravityBox);
         m_2Dworld.addBody(gravityBody);
         
         
         
         Geometry pointMovingBox1 = new Geometry("Point Moving Box", boxMesh);
         pointMovingBox1.setMaterial(Block.blueMaterial);
         BodyFixture pointMovingFixture1 = CreateBodyFixture.createBodyFixtureFromSpatial(boxMesh, Vector2f.ZERO, 0.0f);
         Body pointMovingBody1 = new Body();
         pointMovingBody1.addFixture(pointMovingFixture1);
         pointMovingBody1.setMass(Mass.Type.INFINITE);
         pointMovingBody1.getTransform().setTranslation(8.0, 5.0);
         MovingBlock moving1 = new MovingBlock(1.5f, pointMovingBox1, pointMovingBody1, true, BaseGameEntity.ObjectType.BLUE_BLOCK, 
                                               States.ChangingBlock.GetInstance(), 8.0f, 1.0f, LoopMode.Loop);
         
         MotionPath path1 = new MotionPath();
         path1.addWayPoint(new Vector3f(-9.5f, 12.5f, 0.0f));
         path1.addWayPoint(new Vector3f(-9.5f, 2.0f, 0.0f));
         path1.enableDebugShape(m_Application.getAssetManager(), m_SceneNode);
         MotionPath path2 = new MotionPath();
         path2.addWayPoint(new Vector3f(9.5f, 12.5f, 0.0f));
         path2.addWayPoint(new Vector3f(9.5f, 2.0f, 0.0f));
         path2.enableDebugShape(m_Application.getAssetManager(), m_SceneNode);
         moving1.setPath(path1);
         path1.setCycle(true);
         pointMovingBox1.addControl(moving1);
         m_SceneNode.attachChild(pointMovingBox1);
         m_2Dworld.addBody(pointMovingBody1);
         moving1.getEvent().play();
         
        Geometry pointMovingBox2 = new Geometry("Point Moving Box", boxMesh);
         pointMovingBox2.setMaterial(Block.blueMaterial);
         BodyFixture pointMovingFixture2 = CreateBodyFixture.createBodyFixtureFromSpatial(boxMesh, Vector2f.ZERO, 0.0f);
         Body pointMovingBody2 = new Body();
         pointMovingBody2.addFixture(pointMovingFixture2);
         pointMovingBody2.setMass(Mass.Type.INFINITE);
         pointMovingBody2.getTransform().setTranslation(8.0, 5.0);
         MovingBlock moving2 = new MovingBlock(1.5f, pointMovingBox2, pointMovingBody2, true, BaseGameEntity.ObjectType.BLUE_BLOCK, 
                                               States.ChangingBlock.GetInstance(), 8.0f, 1.0f, LoopMode.Loop);
         
         moving2.setPath(path2);
         path2.setCycle(true);
         pointMovingBox2.addControl(moving2);
         m_SceneNode.attachChild(pointMovingBox2);
         m_2Dworld.addBody(pointMovingBody2);
         moving2.getEvent().play();
         
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
