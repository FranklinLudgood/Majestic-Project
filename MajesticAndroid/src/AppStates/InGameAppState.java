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
import com.jme3.renderer.Camera;
import com.jme3.app.state.AppStateManager;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
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
import Utils.*;
import com.jme3.math.ColorRGBA;
import MessageSystem.*;
import com.jme3.animation.LoopMode;
import com.jme3.bounding.BoundingSphere;
import com.jme3.bounding.BoundingVolume;
import GameObjects.LevelManager;
import States.*;
import com.jme3.scene.Spatial;



public class InGameAppState extends AbstractAppState{
    
    
    private MessageCenter m_MessageCenter;
    private LevelManager m_LevelManager;
    private Camera m_playerCamera;
    private PlayerControl m_Control;
     
    
     @Override
    public void update(float tpf) {
       super.update(tpf);
       m_MessageCenter.update(tpf);
       double elapsedTime = (double) tpf;
       LevelManager.GetInstance().getWorld().update(elapsedTime);
       Vector3f position = m_Control.getSpatial().getLocalTranslation();
       m_playerCamera.lookAt(position, Vector3f.UNIT_Y);
    }
    
   
    @Override
   public void cleanup() {
     super.cleanup();
     LevelManager.GetInstance().getWorld().removeAllBodiesAndJoints();
     m_MessageCenter = null;
     m_LevelManager = null;
   }
    
    
    @Override
   public void initialize(AppStateManager stateManager, Application app) {
       super.initialize(stateManager, app);
       
      m_MessageCenter = MessageCenter.GetInstance();
      m_LevelManager = LevelManager.GetInstance();
       
      Settings worldSettings = new Settings();
      worldSettings.setStepFrequency((1/60.0));
      
      worldSettings.setAngularTolerance(0.05);
      worldSettings.setLinearTolerance(0.05);
      worldSettings.setAutoSleepingEnabled(true);
      worldSettings.setSleepAngularVelocity(0.1);
      worldSettings.setSleepLinearVelocity(0.1);
      
      GameContactListner listner = new GameContactListner();
      
      m_LevelManager.getWorld().setGravity(new Vector2(0.0, -10.0));
      m_LevelManager.getWorld().setSettings(worldSettings);
      m_LevelManager.getWorld().addListener(listner);
      
      setColor();
      setCamera();
      setGameBoundries();
      setPath();
      setBumpers();
      setBricks();
      setPlayer();
       
    }
    
    private void setCamera(){
        
        m_playerCamera = m_LevelManager.getCamera("DefaultCam");
        m_playerCamera.setLocation(new Vector3f(25.5f, 12.5f, 35.0f));
        m_playerCamera.lookAt(new Vector3f(25.0f, 12.5f, 0.0f), Vector3f.UNIT_Y);
    
    }
    
    private void setColor(){
    
        Block.borderMaterial = new Material(m_LevelManager.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        Block.borderMaterial.setColor("Color", ColorRGBA.Gray);
        
        Block.blueMaterial = new Material(m_LevelManager.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        Block.blueMaterial.setColor("Color", ColorRGBA.Blue);
        
        Block.yellowMaterial = new Material(m_LevelManager.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        Block.yellowMaterial.setColor("Color", ColorRGBA.Yellow);
        
        Block.gravityMaterial = new Material(m_LevelManager.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        Block.gravityMaterial.setColor("Color", ColorRGBA.Pink);
    }
    
    private void setGameBoundries(){
        
        //Floor
        Box boundry = new Box(new Vector3f(-23.0f, -0.5f, -0.5f), new Vector3f(23.0f, 0.5f, 0.5f));
        Geometry floorGeom = new Geometry("floor", boundry);
        BodyFixture floorFixuture = CreateBodyFixture.createBodyFixtureFromSpatial(boundry, Vector3f.ZERO, 0.0f);
        Body floor2D = new Body();
        floor2D.addFixture(floorFixuture);
        floor2D.setMass(Mass.Type.INFINITE);
        Block floor = new Block(10.0f, floorGeom, floor2D, false, BaseGameEntity.ObjectType.BENIGN, null);
        floorGeom.setMaterial(Block.borderMaterial);
        floorGeom.addControl(floor);
        floorGeom.getLocalTransform().getTranslation().set(23.0f, 0.5f, 0.0f);
        floor2D.getTransform().setTranslation(23.0f, 0.5f);
        m_LevelManager.RegisterObject("Boundries", (Dyn4RigidBodyControl)floor);
        
        //Left Boundry
        Box sideBox = new Box(new Vector3f(-0.5f, -12.5f, -0.5f), new Vector3f(0.5f,  12.5f, 0.5f));
        Geometry leftBoxGeom = new Geometry("LeftBorder", sideBox);
        leftBoxGeom.setMaterial(Block.borderMaterial);
        BodyFixture leftFixture = CreateBodyFixture.createBodyFixtureFromSpatial(sideBox, Vector2f.ZERO, 0.0f);
        Body leftBox2D = new Body();
        leftBox2D.addFixture(leftFixture);
        leftBox2D.setMass(Mass.Type.INFINITE);
        Block leftBlock = new Block(10.0f, leftBoxGeom, leftBox2D, false, BaseGameEntity.ObjectType.BENIGN, null);
        leftBoxGeom.addControl(leftBlock);
        leftBox2D.getTransform().setTranslation(.5, 12.5);
        leftBoxGeom.getLocalTransform().setTranslation(0.5f, 12.5f, 0.0f);
        m_LevelManager.RegisterObject("Boundries", (Dyn4RigidBodyControl)leftBlock);
        
        //Right Boundry
        Geometry rightBoxGeom = new Geometry("RightBorder", sideBox);
        rightBoxGeom.setMaterial(Block.borderMaterial);
        rightBoxGeom.getLocalTransform().setTranslation(45.5f, 12.5f, 0.0f);
        BodyFixture rightFixture = CreateBodyFixture.createBodyFixtureFromSpatial(sideBox, Vector2f.ZERO, 0.0f);
        Body rightBox2D = new Body();
        rightBox2D.getTransform().setTranslation(45.5, 12.5);
        rightBox2D.addFixture(leftFixture);
        rightBox2D.setMass(Mass.Type.INFINITE);
        Block rightBlock = new Block(10.0f, rightBoxGeom, rightBox2D, false, BaseGameEntity.ObjectType.BENIGN, null);
        rightBoxGeom.addControl(rightBlock);
        m_LevelManager.RegisterObject("Boundries", (Dyn4RigidBodyControl)rightBlock);
        
        //Ceiling
        Geometry ceilingGeom = new Geometry("ceiling", boundry);
        ceilingGeom.setMaterial(Block.borderMaterial);
        BodyFixture ceilFixture = CreateBodyFixture.createBodyFixtureFromSpatial(boundry, Vector2f.ZERO, 0.0f);
        Body ceil2D = new Body();
        ceil2D.addFixture(ceilFixture);
        ceil2D.setMass(Mass.Type.INFINITE);
        ceil2D.getTransform().setTranslation(23.0, 25.5);
        ceilingGeom.getLocalTransform().setTranslation(new Vector3f(23.0f, 25.5f, 0.0f));
        Block ceiling  = new Block(10.0f, ceilingGeom, ceil2D, false, BaseGameEntity.ObjectType.BENIGN, null);
        ceilingGeom.addControl(ceiling);
        m_LevelManager.RegisterObject("Boundries", (Dyn4RigidBodyControl) ceiling);
        
    }
    
  
      
    private void setPlayer(){
        
       m_Control = CreateGameObjects.MakePlayerBall("PlayerBall", new Vector3f(35.0f, 5.0f, 0.0f), 1.0f);
        
        /*
        m_LevelManager.RegisterObject("Player", (Dyn4RigidBodyControl) m_Control);
        m_MessageCenter.AddActor(m_Control, GravityBlock.FILTER);
        */ 
           
    }
    
    private void setPath(){
    
         MotionPath path1 = new MotionPath();
         path1.addWayPoint(new Vector3f(10.5f, 12.5f, 0.0f));
         path1.addWayPoint(new Vector3f(10.5f, 2.0f, 0.0f));
         path1.enableDebugShape(m_LevelManager.getAssetManager(), m_LevelManager.getNode());
         path1.setCycle(true);
         MotionPath path2 = new MotionPath();
         path2.addWayPoint(new Vector3f(30.5f, 12.5f, 0.0f));
         path2.addWayPoint(new Vector3f(30.5f, 2.0f, 0.0f));
         path2.enableDebugShape(m_LevelManager.getAssetManager(), m_LevelManager.getNode());
         path2.setCycle(true);
         MotionPath path3 = new MotionPath();
         path3.addWayPoint(new Vector3f(10.5f, 17.5f, 0.0f));
         path3.addWayPoint(new Vector3f(30.5f, 17.5f, 0.0f));
         path3.enableDebugShape(m_LevelManager.getAssetManager(), m_LevelManager.getNode());
         path3.setCycle(true);
         MotionPath path4 = new MotionPath();
         path4.addWayPoint(new Vector3f(40.5f, 20.5f, 0.0f));
         path4.addWayPoint(new Vector3f(40.5f, 2.5f, 0.0f));
         path4.enableDebugShape(m_LevelManager.getAssetManager(), m_LevelManager.getNode());
         path4.setCycle(true);
    
         m_LevelManager.setMotionPath("Path1", path1);
         m_LevelManager.setMotionPath("Path2", path2);
         m_LevelManager.setMotionPath("Path3", path3);
         m_LevelManager.setMotionPath("Path4", path4);
    }
    
    private void setBumpers(){
        
        CreateGameObjects.CreateStationaryBlueBumper("Bumper1", "Bumper", new Vector3f(4.0f, 7.0f, 0.0f), 2.0f, 1);
        CreateGameObjects.CreateStationaryYellowBumper("Bumper2", "Bumper", new Vector3f(20.0f, 7.0f, 0.0f), 2.0f, 2);
        CreateGameObjects.CreateMovingYellowBumper("Bumper3", "Bumper", "Path4", LoopMode.Loop, 2.0f, 3);
        /*
        Sphere bumperSphere = new Sphere(25, 25, 2.0f);
        Geometry bumperGeom1 = new Geometry("Bumper1", bumperSphere);
        bumperGeom1.setMaterial(Block.yellowMaterial);
        BodyFixture bumperFix1 = CreateBodyFixture.createBodyFixtureFromSpatial(bumperSphere, Vector2f.ZERO);
        Body bumperBody1 = new Body();
        bumperBody1.addFixture(bumperFix1);
        bumperBody1.setMass(Mass.Type.INFINITE);
        ChangingBumper changeBump = new ChangingBumper(3.0f);
        Bumper bumper1 = new Bumper(bumperGeom1, bumperBody1, BaseGameEntity.ObjectType.YELLOW_BUMPER, "Bumper1", 1, changeBump);
        bumperGeom1.addControl(bumper1);
        bumperBody1.getTransform().setTranslation(4.0, 7.0);
        bumperGeom1.getLocalTransform().setTranslation(4.0f, 7.0f, 0.0f);
        bumperBody1.setUserData(bumper1);
        m_LevelManager.RegisterObject("Bumper", (Dyn4RigidBodyControl) bumper1);
        
        
        Geometry bumperGeom2 = new Geometry("Bumper2", bumperSphere);
        bumperGeom2.setMaterial(Block.yellowMaterial);
        BodyFixture bumperFix2 = CreateBodyFixture.createBodyFixtureFromSpatial(bumperSphere, Vector2f.ZERO);
        Body bumperBody2 = new Body();
        bumperBody2.addFixture(bumperFix2);
        bumperBody2.setMass(Mass.Type.INFINITE);
        MotionPath path = m_LevelManager.getMotionPath("Path4");
        
        
        Bumper bumper2 = new Bumper(bumperGeom2, bumperBody2, BaseGameEntity.ObjectType.YELLOW_BUMPER, "Bumper2", 2, null);
        MovingBumper moving = new MovingBumper(path, bumper2, LoopMode.Loop);
        bumper2.setState(moving);
        bumperGeom2.addControl(bumper2);
        bumperBody2.getTransform().setTranslation(40.5, 20.5);
        bumperGeom2.getLocalTransform().setTranslation(40.5f, 20.5f, 0.0f);
        bumperBody2.setUserData(bumper2);
        moving.getEvent().play();
        m_LevelManager.RegisterObject("Bumper", (Dyn4RigidBodyControl) bumper2);
       */
    }
    
    
    private void setBricks(){
        
        Box box = new Box(new Vector3f(-2.0f, -0.5f, -0.5f), new Vector3f(2.0f, 0.5f, 0.5f));
        Box staticBox = new Box(3.5f, 0.5f, 0.5f);
        CreateGameObjects.CreateBeninBlockDynamic("Block1", "Benin", "Path1", box, 10.0f, 0.5f, LoopMode.Loop);
        CreateGameObjects.CreateBeninBlockDynamic("Block2", "Benin", "Path2", box, 10.0f, 0.5f, LoopMode.Loop);
        
        CreateGameObjects.CreateBeninBlockStatic("Block3", "Benin", new Vector3f(5.0f, 12.5f, 0.0f), staticBox);
        CreateGameObjects.CreateBeninBlockStatic("Block4", "Benin", new Vector3f(25.0f, 12.5f, 0.0f), staticBox);
        
        CreateGameObjects.CreateYellowPointBlockStatic("Block5", "PointBlock", new Vector3f(5.5f, 14.0f, 0.0f));
        CreateGameObjects.CreateBluePointBlockStatic("Block6", "PointBlock", new Vector3f(27.0f, 15.0f, 0.0f));
        CreateGameObjects.CreateBluePointBlockDynamic("Block7", "PointBlock", "Path3", 10.0f, 0.5f, LoopMode.Loop);
        
        /*
        
        /*
        //set up gravity blocks
        Geometry gravityGeom1 = new Geometry("Gavity1", pointBox);
        gravityGeom1.setMaterial(Block.gravityMaterial);
        BodyFixture gravityFix1 = CreateBodyFixture.createBodyFixtureFromSpatial(pointBox, Vector2f.ZERO, 0.0f);
        Body gravityBody1 = new Body();
        gravityBody1.addFixture(gravityFix1);
        gravityBody1.setMass(Mass.Type.INFINITE);
        gravityGeom1.getLocalTransform().setTranslation(22.0f, 6.0f, 0.0f);
        gravityBody1.getTransform().setTranslation(22.0, 6.0);
        GravityBlock gravityBlock1 = new GravityBlock(10.0f, 10.0f, gravityGeom1, gravityBody1, false, null, true);
        gravityGeom1.addControl(gravityBlock1);
        
        m_LevelManager.RegisterObject("Gravity", (Dyn4RigidBodyControl) gravityBlock1);
        m_MessageCenter.CreateAreaTrigger(gravityBlock1.getTrigger());
        */
        //set up point blocks
        
        
        
    
    }
    
}
