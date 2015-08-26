/*******************************************************
 * File Name: MessageCenter.java
 * Author: Franklin Ludgood
 * Date Created: 08-24-2015
 *******************************************************/
package MessageSystem;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;


//TODO: finish this class
//Implement: event and message system.

public class MessageCenter {
    
    
    private static MessageCenter m_message;
    
    public static MessageCenter GetInstance(){
        
        if(m_message == null)
            m_message = new MessageCenter();
        
        return m_message;
    }
    
    private HashMap<GameEvent.EventType, List<EventResponse>> m_EventResponders;
    private HashMap<Integer, MessageResponse> m_MessageResponders;
    private List<TimeTrigger> m_TimeObjects;
    private List<AreaTrigger> m_AreaObjects;
    private RingBuffer<GameEvent> m_bufferObjects;
    
    
    private MessageCenter(){
    
        m_TimeObjects = new ArrayList<TimeTrigger>();
        m_AreaObjects = new ArrayList<AreaTrigger>();
        m_EventResponders = new HashMap<GameEvent.EventType, List<EventResponse>>();
        m_MessageResponders = new HashMap<Integer, MessageResponse>();
        m_bufferObjects = new RingBuffer<GameEvent>(GameEvent.class, 25);
    
    }
    
    //TODO: Finish these Functions;
    
    public bool SendEvent(GameEvent event){
    
        
    }
    
    
    public bool CreateTimeDelay(TimeTrigger trigger){ 
    
    }
    
    public bool CreateAreaTrigger(AreaTrigger trigger){
    
    }
    
    public bool ProcessMessage(GameMessage message){
    
    }
}
