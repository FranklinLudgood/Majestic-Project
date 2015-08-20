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
import GameObjects.Dyn4RigidBodyControl;
import States.BallSlowing;
import States.BallRolling;
import GameObjects.PlayerControl;
import MessageSystem.GameContactListner;
import States.BallFalling;
import Utils.CreateBodyFixture;
import com.jme3.math.ColorRGBA;


public class InGameAppState extends AbstractAppState{
    
    private World m_2Dworld;
    private Application m_Application;
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
       double elapsedTime = (double) tpf;
       m_2Dworld.update(elapsedTime);
    }
    
   
    @Override
   public void cleanup() {
     super.cleanup();
     m_2Dworld.removeAllBodiesAndJoints();
     m_2Dworld = null;
   }
    
    
    @Override
   public void initialize(AppStateManager stateManager, Application app) {
       super.initialize(stateManager, app);
       
       
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
    
        
        //Setting up floor.
        Box floorBox = new Box(new Vector3f(-15.0f, -0.5f, -1.5f), new Vector3f(15.0f, 0.5f, 1.5f));
        Geometry floor = new Geometry("floor", floorBox);
        
        
        Material floorMat =  new Material(m_Application.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        floorMat.setColor("Color", ColorRGBA.Blue);
        
        //adding floor
        floor.setMaterial(floorMat);
        BodyFixture floorFixuture = CreateBodyFixture.createBodyFixtureFromSpatial(floorBox, Vector2f.ZERO, 0.0f);
        Body floor2D = new Body();
        floor2D.addFixture(floorFixuture);
        floor2D.setMass(Mass.Type.INFINITE);
        Dyn4RigidBodyControl floorControl = new Dyn4RigidBodyControl(floor, floor2D);
        floor.addControl(floorControl);
        
        //adding ceiling
        Geometry ceiling = new Geometry("ceiling", floorBox);
        ceiling.setMaterial(floorMat);
        BodyFixture ceilFixture = CreateBodyFixture.createBodyFixtureFromSpatial(floorBox, Vector2f.ZERO, 0.0f);
        Body ceil2D = new Body();
        ceil2D.addFixture(ceilFixture);
        ceil2D.setMass(Mass.Type.INFINITE);
        ceil2D.getTransform().setTranslation(0.0, 15.0);
        ceiling.getLocalTransform().setTranslation(new Vector3f(0.0f, 15.0f, 0.0f));
        Dyn4RigidBodyControl ceilControl = new Dyn4RigidBodyControl(ceiling, ceil2D);
        ceiling.addControl(ceilControl);
        
        //Adding sides to the game field.
        Box sideBox = new Box(new Vector3f(-.5f ,-15.0f  , -1.5f), new Vector3f(.5f,  15.0f, 1.5f));
        Material borderMat =  new Material(m_Application.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        borderMat.setColor("Color", ColorRGBA.Green);
        
        Geometry leftBox = new Geometry("LeftBorder", sideBox);
        leftBox.setMaterial(borderMat);
        BodyFixture leftFixture = CreateBodyFixture.createBodyFixtureFromSpatial(sideBox, 
        Vector2f.ZERO, 0.0f);
        leftBox.getLocalTransform().setTranslation(-15.5f, 0.0f, 0.0f);
        Body leftBox2D = new Body();
        leftBox2D.getTransform().setTranslation(-15.5, 0.0);
        leftBox2D.addFixture(leftFixture);
        leftBox2D.setMass(Mass.Type.INFINITE);
        Dyn4RigidBodyControl leftControl = new Dyn4RigidBodyControl(leftBox, leftBox2D);
        leftBox.addControl(leftControl);
         
         
        Geometry rightBox = new Geometry("RightBorder", sideBox);
        rightBox.setMaterial(borderMat);
        rightBox.getLocalTransform().setTranslation(15.5f, 0.0f, 0.0f);
        BodyFixture rightFixture = CreateBodyFixture.createBodyFixtureFromSpatial(sideBox, 
        Vector2f.ZERO, 0.0f);
        Body rightBox2D = new Body();
        rightBox2D.getTransform().setTranslation(15.5, 0.0);
        rightBox2D.addFixture(leftFixture);
        rightBox2D.setMass(Mass.Type.INFINITE);
        Dyn4RigidBodyControl rightControl = new Dyn4RigidBodyControl(rightBox, rightBox2D);
        rightBox.addControl(rightControl);
        
        
        m_SceneNode.attachChild(leftBox);
        m_SceneNode.attachChild(rightBox);
        m_SceneNode.attachChild(floor);
        m_SceneNode.attachChild(ceiling);
       
        
        m_2Dworld.addBody(floor2D);
        m_2Dworld.addBody(leftBox2D);
        m_2Dworld.addBody(rightBox2D);
        m_2Dworld.addBody(ceil2D);
        
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
        BodyFixture playerFix =  CreateBodyFixture.createBodyFixtureFromSpatial(playerSphere, 
        Vector2f.ZERO);
        Body playerBody = new Body();
        playerBody.addFixture(playerFix);
        playerBody.setMass();
        playerBody.getTransform().setTranslation(-10.0, 6.0);
        PlayerControl control = new PlayerControl(playerGeom, playerBody);
        //control.getBody().applyImpulse(new Vector2(50.0, 0.0));
        control.setState(BallFalling.GetInstance());
        //control.setState(BallRolling.GetInstance());
        control.getBody().setUserData(control);
        GameInput.GameInputManager.GetInstance().register((GameOrientationListener)control);
        GameInput.GameInputManager.GetInstance().register((GameTouchListner)control);
        playerGeom.addControl(control);
        m_2Dworld.addBody(playerBody);
        m_SceneNode.attachChild(playerGeom);
    
    }
    
    private void setWorld(){
        
         Material ballMat = new Material(m_Application.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        ballMat.setColor("Color", ColorRGBA.Magenta);
        
        Material cylinderMat = new Material(m_Application.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        cylinderMat.setColor("Color", ColorRGBA.Cyan);
        
        Material boxMat =  new Material(m_Application.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        boxMat.setColor("Color", ColorRGBA.Yellow);
    
        //creating balls
        Sphere ball1Sphere = new Sphere(25, 25, 2.0f);
        Geometry ball1 = new Geometry("Ball1", ball1Sphere);
        ball1.setMaterial(ballMat);
        BodyFixture ball1Fixture = CreateBodyFixture.createBodyFixtureFromSpatial(ball1Sphere, 
        Vector2f.ZERO);
        Body ball1Body = new Body();
        ball1Body.addFixture(ball1Fixture);
        ball1Body.setMass();
        ball1Body.getTransform().setTranslation(0.0, 5.0);
        ball1.getLocalTransform().setTranslation(0.0f, 5.0f, 0.0f);
        Dyn4RigidBodyControl ball1Control = new Dyn4RigidBodyControl(ball1, ball1Body);
        ball1.addControl(ball1Control);
        
        
        Sphere ball2Sphere = new Sphere(25, 25, 1.0f);
        Geometry ball2 = new Geometry("Ball2", ball2Sphere);
        ball2.setLocalTranslation(new Vector3f(-5.0f, 3.0f, 0.0f));
        ball2.setMaterial(ballMat);
        BodyFixture ball2Fixture = CreateBodyFixture.createBodyFixtureFromSpatial(ball2Sphere, 
        new Vector3f(-5.0f, 3.0f, 0.0f));
        Body ball2Body = new Body();
        ball2Body.addFixture(ball2Fixture);
        ball2Body.setMass();
        ball2Body.getTransform().setTranslation(-5.0, 3.0);
        Dyn4RigidBodyControl ball2Control = new Dyn4RigidBodyControl(ball2, ball2Body);
        ball2.addControl(ball2Control);
        
        
        Sphere ball3Sphere = new Sphere(25, 25, 1.5f);
        Geometry ball3 = new Geometry("Ball3", ball3Sphere);
        ball3.setLocalTranslation(new Vector3f(-2.5f, 8.5f, 0.0f));
        ball3.setMaterial(ballMat);
       
        BodyFixture ball3Fixture = CreateBodyFixture.createBodyFixtureFromSpatial(ball3Sphere, 
        new Vector3f(-2.5f, 8.5f, 0.0f));
        Body ball3Body = new Body();
        ball3Body.addFixture(ball3Fixture);
        ball3Body.setMass();
        ball3Body.getTransform().setTranslation(-2.5, 8.5);
        Dyn4RigidBodyControl ball3Control = new Dyn4RigidBodyControl(ball3, ball3Body);
        ball3.addControl(ball3Control);
        
        
        
        //Creating boxes
        Box boxMesh = new Box(1.0f, 1.0f, 1.0f);
        Geometry box1 = new Geometry("Box1", boxMesh);
        box1.setLocalTranslation(3.5f, 3.5f, 0.0f);
        box1.setMaterial(boxMat);
        BodyFixture box1Fixture = CreateBodyFixture.createBodyFixtureFromSpatial(boxMesh, 
        Vector2f.ZERO, 0.0f);
        Body box1Body = new Body();
        box1Body.addFixture(box1Fixture);
        box1Body.setMass();
        box1Body.getTransform().setTranslation(3.5, 3.5);
        Dyn4RigidBodyControl box1Control = new Dyn4RigidBodyControl(box1, box1Body);
        box1.addControl(box1Control);
        
        boxMesh= new Box(3.0f, 1.5f, 1.0f);     
        Geometry box2 = new Geometry("Box2", boxMesh);
        box2.setLocalTranslation(4.5f, 8.5f, 0.0f);
        box2.setMaterial(boxMat);
         BodyFixture box2Fixture = CreateBodyFixture.createBodyFixtureFromSpatial(boxMesh, 
        Vector2f.ZERO, 0.0f);
        Body box2Body = new Body();
        box2Body.addFixture(box2Fixture);
        box2Body.setMass();
        box2Body.getTransform().setTranslation(4.5, 8.5);
        Dyn4RigidBodyControl box2Control = new Dyn4RigidBodyControl(box2, box2Body);
        box2.addControl(box2Control);
        
        m_SceneNode.attachChild(ball1);
        m_SceneNode.attachChild(ball2);
        m_SceneNode.attachChild(ball3);
        m_SceneNode.attachChild(box1);
        m_SceneNode.attachChild(box2);
        
        
        m_2Dworld.addBody(ball1Body);
        m_2Dworld.addBody(ball2Body);
        m_2Dworld.addBody(ball3Body);
        m_2Dworld.addBody(box1Body);
        m_2Dworld.addBody(box2Body);
        
    
    }
    
    
}
