/*******************************************************
 * File Name: GameEvent.java
 * Author: Franklin Ludgood
 * Date Created: 08-23-2015
 ********************************************************/
package MessageSystem;

import com.jme3.math.Vector2f;


public class GameEvent {
    
    public static enum EventType {GAME_WON, GAME_LOST,}
    
    private Vector2f m_Position;
    private EventType m_Type;
    
    public GameEvent(Vector2f position, EventType type){
        
        m_Position = position;
        m_Type = type;
    
    }
    
    public Vector2f getPosition(){return m_Position;}
    public EventType getType(){return m_Type;}
    
}
