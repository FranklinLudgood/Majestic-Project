/*******************************************************
 * File Name: CreateGameObjects.java
 * Author: Franklin Ludgood
 * Date Created: 09-26-2015
 *******************************************************/
package Utils;

import GameInput.GameOrientationListener;
import GameInput.GameTouchListner;
import GameObjects.BaseGameEntity;
import GameObjects.Block;
import GameObjects.Bumper;
import GameObjects.Dyn4RigidBodyControl;
import GameObjects.GravityBlock;
import GameObjects.PlayerControl;
import States.BallFalling;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;
import com.jme3.scene.shape.Box;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import GameObjects.LevelManager;
import GameObjects.MovingBlock;
import MessageSystem.MessageCenter;
import States.ChangingBumper;
import States.MovingBumper;
import com.jme3.animation.LoopMode;
import com.jme3.cinematic.MotionPath;
import java.util.List;
import org.dyn4j.geometry.Mass;


public class CreateGameObjects {
    
    public static final Box pointBox = new Box(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector3f(0.5f, 0.5f, 0.5f));
    
    public static PlayerControl MakePlayerBall(String Name, Vector3f position, float radius){
        
        Sphere playerSphere = new Sphere(25, 25, radius);
        Geometry playerGeom = new Geometry(Name, playerSphere);
        playerGeom.setMaterial(Block.blueMaterial);
        playerGeom.getLocalTransform().setTranslation(position);
        BodyFixture playerFix =  CreateBodyFixture.createBodyFixtureFromSpatial(playerSphere, Vector2f.ZERO);
        Body playerBody = new Body();
        playerBody.addFixture(playerFix);
        playerBody.setMass();
        playerBody.getTransform().setTranslation((double) position.x, (double) position.y);
        PlayerControl control = new PlayerControl(playerGeom, playerBody);
        control.setState(BallFalling.GetInstance());
        control.getBody().setUserData(control);
        GameInput.GameInputManager.GetInstance().register((GameOrientationListener)control);
        GameInput.GameInputManager.GetInstance().register((GameTouchListner)control);
        playerGeom.addControl(control);
        LevelManager.GetInstance().RegisterObject(Name, (Dyn4RigidBodyControl) control);
        MessageCenter.GetInstance().AddActor(control, GravityBlock.FILTER);
        
       return control; 
    }
    
    public static MotionPath CreateMotionPath(String Name, List<Vector3f> waypoints, 
                                boolean enableDebug, boolean setCycle){
    
        MotionPath path = new MotionPath();
        for(int i = 0; i < waypoints.size(); ++i){
            path.addWayPoint(waypoints.get(i));
        }
        
        if(enableDebug == true){
            path.enableDebugShape(LevelManager.GetInstance().getAssetManager(),
                    LevelManager.GetInstance().getNode());
        } 
        path.setCycle(setCycle);
        return path;
    
    }
    
   public static Bumper CreateStationaryYellowBumper(String Name, String Filter, Vector3f position,
                            float radius, int ID){
   
        Sphere bumperSphere = new Sphere(25, 25, radius);
        Geometry bumperGeom = new Geometry(Name, bumperSphere);
        bumperGeom.setMaterial(Block.yellowMaterial);
        BodyFixture bumperFix = CreateBodyFixture.createBodyFixtureFromSpatial(bumperSphere, Vector2f.ZERO);
        Body bumperBody = new Body();
        bumperBody.addFixture(bumperFix);
        bumperBody.setMass(Mass.Type.INFINITE);
        Bumper bumper = new Bumper(bumperGeom, bumperBody, BaseGameEntity.ObjectType.YELLOW_BUMPER, Name, ID, null);
        bumperGeom.addControl(bumper);
        bumperBody.getTransform().setTranslation((double) position.x, (double) position.y);
        bumperGeom.getLocalTransform().setTranslation(position);
        bumperBody.setUserData(bumper);
        LevelManager.GetInstance().RegisterObject(Filter, (Dyn4RigidBodyControl) bumper);
   
        return bumper;
   }
   
   
   public static Bumper CreateStationaryBlueBumper(String Name, String Filter, Vector3f position,
                            float radius, int ID){
       
        Sphere bumperSphere = new Sphere(25, 25, radius);
        Geometry bumperGeom = new Geometry(Name, bumperSphere);
        bumperGeom.setMaterial(Block.blueMaterial);
        BodyFixture bumperFix = CreateBodyFixture.createBodyFixtureFromSpatial(bumperSphere, Vector2f.ZERO);
        Body bumperBody = new Body();
        bumperBody.addFixture(bumperFix);
        bumperBody.setMass(Mass.Type.INFINITE);
        Bumper bumper = new Bumper(bumperGeom, bumperBody, BaseGameEntity.ObjectType.BLUE_BUMPER, Name, ID, null);
        bumperGeom.addControl(bumper);
        bumperBody.getTransform().setTranslation((double) position.x, (double) position.y);
        bumperGeom.getLocalTransform().setTranslation(position);
        bumperBody.setUserData(bumper);
        LevelManager.GetInstance().RegisterObject(Filter, (Dyn4RigidBodyControl) bumper);
   
        return bumper;  
   }
   
   public static Bumper CreateMovingYellowBumper(String Name, String Filter, String pathName,
                             LoopMode loop, float radius, int ID){
   
        Sphere bumperSphere = new Sphere(25, 25, radius);
        Geometry bumperGeom = new Geometry(Name, bumperSphere);
        bumperGeom.setMaterial(Block.yellowMaterial);
        BodyFixture bumperFix = CreateBodyFixture.createBodyFixtureFromSpatial(bumperSphere, Vector2f.ZERO);
        Body bumperBody = new Body();
        bumperBody.addFixture(bumperFix);
        bumperBody.setMass(Mass.Type.INFINITE);
        MotionPath path = LevelManager.GetInstance().getMotionPath(pathName);
        
        if(path == null)
            return null;
        
        Bumper bumper = new Bumper(bumperGeom, bumperBody, BaseGameEntity.ObjectType.YELLOW_BUMPER, Name, ID, null);
        MovingBumper moving = new MovingBumper(path, bumper, loop);
        bumper.setState(moving);
        bumperGeom.addControl(bumper);
        Vector3f position = path.getWayPoint(0);
        bumperBody.getTransform().setTranslation((double) position.x, (double) position.y);
        bumperGeom.getLocalTransform().setTranslation(position);
        bumperBody.setUserData(bumper);
        moving.getEvent().play();
        LevelManager.GetInstance().RegisterObject(Filter, (Dyn4RigidBodyControl) bumper);
        
        
        return bumper;
   }
   
   
   public static Bumper CreateMovingBlueBumper(String Name, String Filter, String pathName,
                             LoopMode loop, float radius, int ID){
   
        Sphere bumperSphere = new Sphere(25, 25, radius);
        Geometry bumperGeom = new Geometry(Name, bumperSphere);
        bumperGeom.setMaterial(Block.blueMaterial);
        BodyFixture bumperFix = CreateBodyFixture.createBodyFixtureFromSpatial(bumperSphere, Vector2f.ZERO);
        Body bumperBody = new Body();
        bumperBody.addFixture(bumperFix);
        bumperBody.setMass(Mass.Type.INFINITE);
        MotionPath path = LevelManager.GetInstance().getMotionPath(pathName);
        
        if(path == null)
            return null;
        
        Bumper bumper = new Bumper(bumperGeom, bumperBody, BaseGameEntity.ObjectType.BLUE_BUMPER, Name, ID, null);
        MovingBumper moving = new MovingBumper(path, bumper, loop);
        bumper.setState(moving);
        bumperGeom.addControl(bumper);
        Vector3f position = path.getWayPoint(0);
        bumperBody.getTransform().setTranslation((double) position.x, (double) position.y);
        bumperGeom.getLocalTransform().setTranslation(position);
        bumperBody.setUserData(bumper);
        moving.getEvent().play();
        LevelManager.GetInstance().RegisterObject(Filter, (Dyn4RigidBodyControl) bumper);
        
        return bumper;
   
   }
   
   
   public static Bumper CreateChangingBumper(String Name, String Filter, Vector3f position,
                            float radius, float delay, int ID){
       
       Sphere bumperSphere = new Sphere(25, 25, radius);
        Geometry bumperGeom = new Geometry(Name, bumperSphere);
        bumperGeom.setMaterial(Block.yellowMaterial);
        BodyFixture bumperFix = CreateBodyFixture.createBodyFixtureFromSpatial(bumperSphere, Vector2f.ZERO);
        Body bumperBody = new Body();
        bumperBody.addFixture(bumperFix);
        bumperBody.setMass(Mass.Type.INFINITE);
        ChangingBumper changeBump = new ChangingBumper(delay);
        Bumper bumper = new Bumper(bumperGeom, bumperBody, BaseGameEntity.ObjectType.YELLOW_BUMPER, Name, ID, changeBump);
        bumperGeom.addControl(bumper);
        bumperBody.getTransform().setTranslation((double) position.x, (double) position.y);
        bumperGeom.getLocalTransform().setTranslation(position);
        bumperBody.setUserData(bumper);
        LevelManager.GetInstance().RegisterObject(Filter, (Dyn4RigidBodyControl) bumper);
        
        return bumper;
   
   }
   
   
   public static Block CreateBeninBlockStatic(String Name, String Filter,  
                        Vector3f position, Box box){
       
        Geometry blockGeom = new Geometry(Name, box);
        blockGeom.setMaterial(Block.borderMaterial);
        BodyFixture blockFix = CreateBodyFixture.createBodyFixtureFromSpatial(box, Vector2f.ZERO, 0.0f);
        Body blockBody = new Body();
        blockBody.addFixture(blockFix);
        blockBody.setMass(Mass.Type.INFINITE);
        Block block = new Block(10.0f, blockGeom, blockBody, false, BaseGameEntity.ObjectType.BENIGN, null);
        blockGeom.addControl(block);
        blockGeom.getLocalTransform().setTranslation(position);
        blockBody.getTransform().setTranslation((double) position.x, (double) position.y);
        LevelManager.GetInstance().RegisterObject(Filter, (Dyn4RigidBodyControl) block);
        
        return block;
   
   }
   
   
   public static MovingBlock CreateBeninBlockDynamic(String Name, String Filter,  
                        String pathName, Box box, float pathDuration, float speed, 
                                                                     LoopMode loop){
       
         //set up benin blocks
         Geometry blockGeom = new Geometry(Name, box);
         blockGeom.setMaterial(Block.borderMaterial);
         BodyFixture blockFix = CreateBodyFixture.createBodyFixtureFromSpatial(box, Vector2f.ZERO, 0.0f);
         Body blockBody = new Body();
         blockBody.addFixture(blockFix);
         blockBody.setMass(Mass.Type.INFINITE);
         
         MovingBlock block = new MovingBlock(10.0f, blockGeom, blockBody, false,
                                                BaseGameEntity.ObjectType.BENIGN, null, 
                                                pathDuration, speed, loop);
         
          MotionPath path = LevelManager.GetInstance().getMotionPath(pathName);
        
          if(path == null)
             return null;
         
        Vector3f position = path.getWayPoint(0);
        
        blockGeom.addControl(block);
        blockGeom.getLocalTransform().setTranslation(position);
        blockBody.getTransform().setTranslation((double) position.x, (double) position.y);
        block.getEvent().setPath(path);
        block.getEvent().play();
        LevelManager.GetInstance().RegisterObject(Filter, (Dyn4RigidBodyControl) block);
        
        
        return block;
   }
   
   
   public static GravityBlock CreateGravityBlock(String Name, String Filter,  
                        Vector3f position, float radius){
   
        //Box box = new Box(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector3f(0.5f, 0.5f, 0.5f));
        Geometry gravityGeom = new Geometry(Name, pointBox);
        gravityGeom.setMaterial(Block.gravityMaterial);
        BodyFixture gravityFix = CreateBodyFixture.createBodyFixtureFromSpatial(pointBox, Vector2f.ZERO, 0.0f);
        Body gravityBody = new Body();
        gravityBody.addFixture(gravityFix);
        gravityBody.setMass(Mass.Type.INFINITE);
        gravityGeom.getLocalTransform().setTranslation(position);
        gravityBody.getTransform().setTranslation((double) position.x, (double) position.y);
        GravityBlock gravityBlock = new GravityBlock(radius, 10.0f, gravityGeom, gravityBody, false, null, true);
        gravityGeom.addControl(gravityBlock);
        
        LevelManager.GetInstance().RegisterObject(Filter, (Dyn4RigidBodyControl) gravityBlock);
        MessageCenter.GetInstance().CreateAreaTrigger(gravityBlock.getTrigger());
        
        return gravityBlock;
   
   }
   
   public static Block CreateBluePointBlockStatic(String Name, String Filter, Vector3f position){
       
       //Box box = new Box(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector3f(0.5f, 0.5f, 0.5f));
       Geometry pointGeom = new Geometry(Name, pointBox);
       pointGeom.setMaterial(Block.blueMaterial);
       BodyFixture pointFix = CreateBodyFixture.createBodyFixtureFromSpatial(pointBox, Vector2f.ZERO, 0.0f);
       Body pointBody = new Body();
       pointBody.addFixture(pointFix);
       pointBody.setMass(Mass.Type.INFINITE);
       Block pointBlock = new Block(10.0f, pointGeom, pointBody, false, BaseGameEntity.ObjectType.BLUE_BLOCK, null);
       pointGeom.addControl(pointBlock);
       pointBody.getTransform().setTranslation((double) position.x, (double) position.y);
       pointGeom.getLocalTransform().setTranslation(position);
       pointBody.setUserData(pointBlock);
       LevelManager.GetInstance().RegisterObject(Filter, (Dyn4RigidBodyControl) pointBlock);
        
       return pointBlock;
   }
   
   
   public static Block CreateYellowPointBlockStatic(String Name, String Filter, Vector3f position){
       
       //Box box = new Box(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector3f(0.5f, 0.5f, 0.5f));
       Geometry pointGeom = new Geometry(Name, pointBox);
       pointGeom.setMaterial(Block.yellowMaterial);
       BodyFixture pointFix = CreateBodyFixture.createBodyFixtureFromSpatial(pointBox, Vector2f.ZERO, 0.0f);
       Body pointBody = new Body();
       pointBody.addFixture(pointFix);
       pointBody.setMass(Mass.Type.INFINITE);
       Block pointBlock = new Block(10.0f, pointGeom, pointBody, false, BaseGameEntity.ObjectType.YELLOW_BLOCK, null);
       pointGeom.addControl(pointBlock);
       pointBody.getTransform().setTranslation((double) position.x, (double) position.y);
       pointGeom.getLocalTransform().setTranslation(position);
       pointBody.setUserData(pointBlock);
       LevelManager.GetInstance().RegisterObject(Filter, (Dyn4RigidBodyControl) pointBlock);
        
       return pointBlock;
   }
   
   
   public static Block CreateYellowPointBlockDynamic(String Name, String Filter,  
                        String pathName, float pathDuration, float speed, 
                                                                LoopMode loop){
       
       //Box box = new Box(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector3f(0.5f, 0.5f, 0.5f));
         Geometry blockGeom = new Geometry(Name, pointBox);
         blockGeom.setMaterial(Block.yellowMaterial);
         BodyFixture blockFix = CreateBodyFixture.createBodyFixtureFromSpatial(pointBox, Vector2f.ZERO, 0.0f);
         Body blockBody = new Body();
         blockBody.addFixture(blockFix);
         blockBody.setMass(Mass.Type.INFINITE);
         
         MovingBlock block = new MovingBlock(10.0f, blockGeom, blockBody, false,
                                                BaseGameEntity.ObjectType.YELLOW_BLOCK, null, 
                                                pathDuration, speed, loop);
         
          MotionPath path = LevelManager.GetInstance().getMotionPath(pathName);
        
          if(path == null)
             return null;
         
        Vector3f position = path.getWayPoint(0);
        
        blockGeom.addControl(block);
        blockGeom.getLocalTransform().setTranslation(position);
        blockBody.getTransform().setTranslation((double) position.x, (double) position.y);
        block.getEvent().setPath(path);
        block.getEvent().play();
        LevelManager.GetInstance().RegisterObject(Filter, (Dyn4RigidBodyControl) block);
        
        
        return block;
       
   }
   
   
   public static Block CreateBluePointBlockDynamic(String Name, String Filter,  
                        String pathName, float pathDuration, float speed, 
                                                                LoopMode loop){
       
         //Box box = new Box(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector3f(0.5f, 0.5f, 0.5f));
         Geometry blockGeom = new Geometry(Name, pointBox);
         blockGeom.setMaterial(Block.blueMaterial);
         BodyFixture blockFix = CreateBodyFixture.createBodyFixtureFromSpatial(pointBox, Vector2f.ZERO, 0.0f);
         Body blockBody = new Body();
         blockBody.addFixture(blockFix);
         blockBody.setMass(Mass.Type.INFINITE);
         
         MovingBlock block = new MovingBlock(10.0f, blockGeom, blockBody, false,
                                                BaseGameEntity.ObjectType.BLUE_BLOCK, null, 
                                                pathDuration, speed, loop);
         
          MotionPath path = LevelManager.GetInstance().getMotionPath(pathName);
        
          if(path == null)
             return null;
         
        Vector3f position = path.getWayPoint(0);
        
        blockGeom.addControl(block);
        blockGeom.getLocalTransform().setTranslation(position);
        blockBody.getTransform().setTranslation((double) position.x, (double) position.y);
        block.getEvent().setPath(path);
        block.getEvent().play();
        LevelManager.GetInstance().RegisterObject(Filter, (Dyn4RigidBodyControl) block);
        
        
        return block;
   
   }
     
}
