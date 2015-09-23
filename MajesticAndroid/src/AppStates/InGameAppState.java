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
import Utils.CreateBodyFixture;
import com.jme3.math.ColorRGBA;
import MessageSystem.*;
import com.jme3.animation.LoopMode;
import com.jme3.bounding.BoundingSphere;
import com.jme3.bounding.BoundingVolume;
import GameObjects.LevelManager;
import States.*;



public class InGameAppState extends AbstractAppState{
    
    
    private MessageCenter m_MessageCenter;
    private LevelManager m_LevelManager;
     
    
     @Override
    public void update(float tpf) {
       super.update(tpf);
       m_MessageCenter.update(tpf);
       double elapsedTime = (double) tpf;
       LevelManager.GetInstance().getWorld().update(elapsedTime);
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
        
        Camera camera = m_LevelManager.getCamera("DefaultCam");
        camera.setLocation(new Vector3f(25.5f, 12.5f, 35.0f));
        camera.lookAt(new Vector3f(25.0f, 12.5f, 0.0f), Vector3f.UNIT_Y);
    
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
        
        Sphere playerSphere = new Sphere(25, 25, 1.0f);
        Geometry playerGeom = new Geometry("Player", playerSphere);
        playerGeom.setMaterial(Block.blueMaterial);
        playerGeom.getLocalTransform().setTranslation(35.0f, 5.0f, 0.0f);
        BodyFixture playerFix =  CreateBodyFixture.createBodyFixtureFromSpatial(playerSphere, Vector2f.ZERO);
        Body playerBody = new Body();
        playerBody.addFixture(playerFix);
        playerBody.setMass();
        playerBody.getTransform().setTranslation(35.0, 10.0);
        PlayerControl control = new PlayerControl(playerGeom, playerBody);
        control.setState(BallFalling.GetInstance());
        control.getBody().setUserData(control);
        GameInput.GameInputManager.GetInstance().register((GameOrientationListener)control);
        GameInput.GameInputManager.GetInstance().register((GameTouchListner)control);
        playerGeom.addControl(control);
        m_LevelManager.RegisterObject("Player", (Dyn4RigidBodyControl) control);
           
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
       
    }
    
    //TODO: Implement bricks and add win/lose.
    private void setBricks(){
    
    }
    
}
