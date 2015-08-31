/*******************************************************
 * File Name: MessageCenter.java
 * Author: Franklin Ludgood
 * Date Created: 08-24-2015
 *******************************************************/
package MessageSystem;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.EnumMap;
import com.jme3.scene.Spatial;


//TODO: Test this class.

public class MessageCenter {
    
    
    private static MessageCenter m_message;
    
    public static MessageCenter GetInstance(){
        
        if(m_message == null)
            m_message = new MessageCenter();
        
        return m_message;
    }
    
    private EnumMap<GameEvent.EventType, List<EventResponse>> m_EventResponders;
    private EnumMap<GameBroadCast.BroadCastType, List<BroadCastResponse>> m_BroadCastResponders;
    private HashMap<Integer, MessageResponse> m_MessageResponders;
    private List<TimeTrigger> m_TimeObjects;
    private List<AreaTrigger> m_AreaObjects;
    private RingBuffer<GameEvent> m_bufferObjects;
    
    
    private MessageCenter(){
    
        m_TimeObjects = new ArrayList<TimeTrigger>();
        m_AreaObjects = new ArrayList<AreaTrigger>();
        m_EventResponders = new EnumMap<GameEvent.EventType,List<EventResponse>>(GameEvent.EventType.class);
        m_BroadCastResponders = new EnumMap<GameBroadCast.BroadCastType, List<BroadCastResponse>>(GameBroadCast.BroadCastType.class);
        m_MessageResponders = new HashMap<Integer, MessageResponse>();
        m_bufferObjects = new RingBuffer<GameEvent>(GameEvent.class, 35);
    
    }
    
    
    
    public void update(float tpf){
    
            for(int i = 0; i < m_TimeObjects.size(); ++i)
                m_TimeObjects.get(i).update(tpf);
            
            for(int i = 0; i < m_AreaObjects.size(); ++i)
                m_AreaObjects.get(i).update(tpf);
            
            while(m_bufferObjects.isEmpty() != true){
                  GameEvent event = m_bufferObjects.getHead();
                    if(event != null){
                        if(m_EventResponders.containsKey(event.getType()) == true){
                         List<EventResponse> list = m_EventResponders.get(event.getType());
                         if(list != null){
                             for(int i = 0; i < list.size(); ++i)
                                 list.get(i).Response(event);
                         }
                       }
                  }
                  m_bufferObjects.Dequeue();
            }
    
    }
    
    public void SendEvent(GameEvent event){
            m_bufferObjects.Enqueue(event);
    }
    
    public boolean SendBroadCast(GameBroadCast broadCast){
        
        boolean success = m_BroadCastResponders.containsKey(broadCast.GetType());
        
        if(success == true){
           List<BroadCastResponse> list = m_BroadCastResponders.get(broadCast.GetType());
           if(list != null){
                for(int i = 0; i < list.size(); ++i)
                    list.get(i).Response(broadCast);
          }
        }
        return success;
    }
    
     public boolean ProcessMessage(GameMessage message){
         
         boolean success = m_MessageResponders.containsKey(message.GetReceiverID());
         MessageResponse response = m_MessageResponders.get(message.GetReceiverID());
         
         if(response != null)
             response.ProcessMessage(message);
         
         
         return success;
    }
    
    
    public void CreateTimeDelay(TimeTrigger trigger){ 
        m_TimeObjects.add(trigger);
    }
    
    public void CreateAreaTrigger(AreaTrigger trigger){
        m_AreaObjects.add(trigger);
    }
    
    public boolean AddActor(Spatial actor, String filter){
    
        if(m_AreaObjects.isEmpty() == true)
            return false;
        
        boolean success = false;
        for(int i = 0; i < m_AreaObjects.size(); ++i){
            if(m_AreaObjects.get(i).getFilter().equals(filter)){
                m_AreaObjects.get(i).addActor(actor);
                success = true;
            }
        }
    
        return success;
    }
    
    
    public void RemoveActor(Spatial actor, String filter){
        
        if(m_AreaObjects.isEmpty() == true)
            return;
    
        for(int i = 0; i < m_AreaObjects.size(); ++i){
            if(m_AreaObjects.get(i).equals(filter) == true){
                m_AreaObjects.get(i).removeActor(actor);
            }
        }
         
    }
    
    public boolean RemoveTrigger(TimeTrigger trigger){
        return m_TimeObjects.remove(trigger);
    }
    
    public boolean RemoveTrigger(AreaTrigger trigger){
        return m_AreaObjects.remove(trigger);
    }
    
    
   
    public void addMessageResponse(MessageResponse response, int key){
    
        boolean isCreated = m_MessageResponders.containsKey(key);
        if(isCreated != true)
            m_MessageResponders.put(key, response);
    }
    
    
    public void addBroadCastResponse(BroadCastResponse response, GameBroadCast.BroadCastType type){
    
        boolean isCreated = m_BroadCastResponders.containsKey(type);
        if(isCreated == true)
            m_BroadCastResponders.get(type).add(response);
        
        else 
        {
            ArrayList list = new ArrayList<BroadCastResponse>();
            list.add(type);
            m_BroadCastResponders.put(type, list);
        }
    
    }
    
    
    
    public void addEventResponse(EventResponse responce, GameEvent.EventType type){
        
        boolean isCreated = m_EventResponders.containsKey(type);
        if(isCreated == true)
            m_EventResponders.get(type).add(responce);
        else
        {
             ArrayList list = new ArrayList<EventResponse>();
             list.add(responce);
             m_EventResponders.put(type, list);
        }
     
    }
    
   
}
