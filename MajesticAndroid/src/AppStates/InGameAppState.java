/*******************************************************
 * File Name: InGameAppState.java
 * Author: Franklin Ludgood
 * Date Created: 08-03-2015
 ********************************************************/
package AppStates;
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
      
      InitPhyisics2D();
       
    }
    
    
    private void InitPhyisics2D() {
    
         Box floorBox = new Box(new Vector3f(-15.0f, -0.5f, -1.5f), new Vector3f(15.0f, 0.5f, 1.5f));
        Geometry floor = new Geometry("floor", floorBox);
       
        Material floorMat =  new Material(m_Application.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        floorMat.setColor("Color", ColorRGBA.Blue);
        
        floor.setMaterial(floorMat);
        BodyFixture floorFixuture =  CreateBodyFixture.createBodyFixtureFromSpatial(floorBox, Vector2f.ZERO, 0.0f);
        Body floor2D = new Body();
        floor2D.addFixture(floorFixuture);
        floor2D.setMass(Mass.Type.INFINITE);
        Dyn4RigidBodyControl floorControl = new Dyn4RigidBodyControl(floor, floor2D);
        floor.addControl(floorControl);
        
        
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
        
        
        m_SceneNode.attachChild(floor);
        m_SceneNode.attachChild(ball1);
        m_SceneNode.attachChild(ball2);
        m_SceneNode.attachChild(ball3);
        m_SceneNode.attachChild(box1);
        m_SceneNode.attachChild(box2);
        
      
        m_2Dworld.addBody(floor2D);
        m_2Dworld.addBody(ball1Body);
        m_2Dworld.addBody(ball2Body);
        m_2Dworld.addBody(ball3Body);
        m_2Dworld.addBody(box1Body);
        m_2Dworld.addBody(box2Body);
       
        
    }
    
    
}