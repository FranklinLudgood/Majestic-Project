/*******************************************************
 * File Name: BaseGameEntity.java
 * Author: Franklin Ludgood
 * Date Created: 08-09-2015
 *******************************************************/
package GameObjects;


public interface BaseGameEntity {
    
    public enum ObjectType {PLAYER, BENIGN, YELLOW_BLOCK, BLUE_BLOCK, 
                                YELLOW_BUMPER, BLUE_BUMPER, BLUE_BALL, YELLOW_BALL}
    
    public ObjectType getObjectType();
    public int getObjectID();
    String getObjectName();
    
}
