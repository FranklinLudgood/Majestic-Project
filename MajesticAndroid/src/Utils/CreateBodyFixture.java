/*******************************************************
 * File Name: CreateBodyFixture.java
 * Author: Franklin Ludgood
 * Date Created: 08-03-2015
 *******************************************************/
package Utils;


import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Sphere;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Capsule;
import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Triangle;
import org.dyn4j.geometry.Vector2;
import java.lang.Math.*;


public class CreateBodyFixture {
    
     public static BodyFixture createBodyFixtureFromSpatial(Box box, Vector2f position, float rotationRad){
    
        
       Vector3f boxCenter = box.getBound().getCenter();
       double width = (double) Math.abs((boxCenter.x + box.xExtent) - (boxCenter.x - box.xExtent));
       double height = (double)  Math.abs((boxCenter.y + box.yExtent) - (boxCenter.y - box.yExtent));
        Rectangle rectangle = new Rectangle(width, height);
        rectangle.translate((double) position.x, (double) position.y);
        rectangle.rotate(rotationRad);
        BodyFixture body = new BodyFixture(rectangle);
        return body;
    }
     
      public static BodyFixture createBodyFixtureFromSpatial(Box box, Vector3f position, float rotationRad){
    
       Vector3f boxCenter = box.getBound().getCenter();
       double width = (double) Math.abs((boxCenter.x + box.xExtent) - (boxCenter.x - box.xExtent));
       double height = (double) Math.abs((boxCenter.y + box.yExtent) - (boxCenter.y - box.yExtent));
       Rectangle rectangle = new Rectangle(width, height);
       rectangle.translate((double) position.x, (double) position.y);
       rectangle.rotate(rotationRad);
       BodyFixture body = new BodyFixture(rectangle);
       return body;
    }
      
      public static BodyFixture CreateBox(Vector3f position, float width, float height, float rotationRad){
      
       Rectangle rectangle = new Rectangle(width, height);
       rectangle.translate((double) position.x, (double) position.y);
       rectangle.rotate(rotationRad);
       BodyFixture body = new BodyFixture(rectangle);
       return body;
       
      }
      
      public static BodyFixture CreateBox(Vector2f position, float width, float height, float rotationRad){
      
       Rectangle rectangle = new Rectangle(width, height);
       rectangle.translate((double) position.x, (double) position.y);
       rectangle.rotate(rotationRad);
       BodyFixture body = new BodyFixture(rectangle);
       return body;
       
      }
    
    public static BodyFixture createBodyFixtureFromSpatial(Sphere sphere, Vector2f position){
    
        Circle circle = new Circle((double) sphere.radius);
        BodyFixture body = new BodyFixture(circle);
        return body;
    }
    
    public static BodyFixture createBodyFixtureFromSpatial(Sphere sphere, Vector3f position){
    
        Circle circle = new Circle((double) sphere.radius);
        BodyFixture body = new BodyFixture(circle);
        return body;
    }
    
    public static BodyFixture createBodyFixtureFromSpatial(Cylinder cylinder, Vector2f position, float rotationRad){
    
        Capsule capsule = new Capsule((double) cylinder.getRadius2(),(double) cylinder.getHeight());
        capsule.translate((double) position.x,(double) position.y);
        capsule.rotate(rotationRad);
        BodyFixture body = new BodyFixture(capsule);
        return body;
    }
    
    public static BodyFixture createBodyFixtureTriangleFromPoints(Vector2f point1, Vector2f point2,
                                                                  Vector2f point3, Vector2f position,
                                                                  float rotationRad){
        
        Triangle triangle = new Triangle(new Vector2((double) point1.x, (double) point1.y), 
                                         new Vector2((double) point2.x, (double)point2.y), 
                                          new Vector2((double) point3.x, (double) point3.y));
        
        triangle.translate((double) position.x,(double) position.y);
        triangle.rotate(rotationRad);
        BodyFixture body = new BodyFixture(triangle);
        return body;
    }
    
}
