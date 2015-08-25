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
    
    private HashMap<String,List<EventResponse>> m_EventResponders;
    private List<TimeTrigger> m_TimeObjects;
    private List<AreaTrigger> m_AreaObjects;
    private RingBuffer<GameEvent> m_bufferObjects;
    
    
    private MessageCenter(){
    
        m_TimeObjects = new ArrayList<TimeTrigger>();
        m_AreaObjects = new ArrayList<AreaTrigger>();
        m_EventResponders = new HashMap<String, List<EventResponse>>();
        m_bufferObjects = new RingBuffer<GameEvent>(GameEvent.class, 25);
    
    }
}
