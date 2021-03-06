/*******************************************************
 * File Name: BaseGameEntity.java
 * Author: Franklin Ludgood
 * Date Created: 08-09-2015
 *******************************************************/
package GameObjects;


public interface BaseGameEntity {
    
    public enum ObjectType {PLAYER, LEVEL_MANAGER, BENIGN, YELLOW_BLOCK, BLUE_BLOCK, 
                                GRAVITY_BLOCK, YELLOW_BUMPER, BLUE_BUMPER, BLUE_BALL, YELLOW_BALL}
    
    public ObjectType getObjectType();
    public int getObjectID();
    String getObjectName();
    
}
