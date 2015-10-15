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



public class MessageCenter {
    
    
    private static MessageCenter m_message;
    private GooglePlayInterface googleInterface;
    
    public static MessageCenter GetInstance(){
        
        if(m_message == null)
            m_message = new MessageCenter();
        
        return m_message;
    }
    
    private EnumMap<GameEvent.EventType, List<EventResponse>> m_EventResponders;
    private EnumMap<GameBroadCast.BroadCastType, List<BroadCastResponse>> m_BroadCastResponders;
    private HashMap<Integer, MessageResponse> m_MessageResponders;
    private List<TimeTrigger> m_TimeObjects;
    private HashMap<AreaTrigger,List<AreaTriggered>> m_AreaObjects;
    private HashMap<String, List<AreaTriggered>> m_TriggeredObjects;
    private RingBuffer<GameEvent> m_bufferObjects;
    
    
    private MessageCenter(){
    
        m_TimeObjects = new ArrayList<TimeTrigger>();
        m_AreaObjects = new HashMap<AreaTrigger,List<AreaTriggered>>();
        m_EventResponders = new EnumMap<GameEvent.EventType,List<EventResponse>>(GameEvent.EventType.class);
        m_BroadCastResponders = new EnumMap<GameBroadCast.BroadCastType, List<BroadCastResponse>>(GameBroadCast.BroadCastType.class);
        m_MessageResponders = new HashMap<Integer, MessageResponse>();
        m_TriggeredObjects = new HashMap<String, List<AreaTriggered>>();
        m_bufferObjects = new RingBuffer<GameEvent>(GameEvent.class, 35);
    
    }
    
    
    public void ClearAll(){
        
        m_TimeObjects.clear();
        m_AreaObjects.clear();
        m_EventResponders.clear();
        m_BroadCastResponders.clear();
        m_MessageResponders.clear();
        m_TriggeredObjects.clear();
        m_bufferObjects.ClearQueue();
    }
    
    
    
    public void update(float tpf){
    
            for(int i = 0; i < m_TimeObjects.size(); ++i){
                m_TimeObjects.get(i).update(tpf);
                
                if(m_TimeObjects.get(i).isTriggered() == true)
                    m_TimeObjects.remove(i);
            }
            
          
            
            
       for(int i = 0; i < m_AreaObjects.keySet().toArray().length; ++i){
           AreaTrigger area = (AreaTrigger) m_AreaObjects.keySet().toArray()[i];
           List<AreaTriggered> list = m_AreaObjects.get(area);
           if(list == null){
              if(m_TriggeredObjects.containsKey(area.getFilter())){
                  list = m_TriggeredObjects.get(area.getFilter());
                  m_AreaObjects.put(area, list);
              }
           }
           else if(!list.isEmpty()){
               
            for(int j = 0; j < list.size(); ++j){
                   area.update(list.get(j));   
            }
               
           
           }
           
        }
            
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
       if(!m_AreaObjects.containsKey(trigger)){
           if(m_TriggeredObjects.containsKey(trigger.getFilter())){
                List<AreaTriggered> list = m_TriggeredObjects.get(trigger.getFilter());
                m_AreaObjects.put(trigger, list);   
           } else
               m_AreaObjects.put(trigger, null);
       } else
           m_AreaObjects.put(trigger, null);   
    }
    
    public void AddActor(AreaTriggered actor, String filter){
        
        if(m_TriggeredObjects.containsKey(filter) == true){
   
            List<AreaTriggered> list = m_TriggeredObjects.get(filter);
            if(list != null){
                list.add(actor);
            }
            else {
                list = new ArrayList<AreaTriggered>();
                list.add(actor);
                m_TriggeredObjects.put(filter, list);
            }
        } else {
        
            List<AreaTriggered> newList = new ArrayList<AreaTriggered>();
            newList.add(actor);
            m_TriggeredObjects.put(filter, newList);
        }
       
    }
    
    
    public boolean RemoveActor(AreaTriggered actor, String filter){
       
        if(m_TriggeredObjects.isEmpty() == true)
            return false;
        
        boolean success = false;
        if(m_TriggeredObjects.containsKey(filter) == true){
            List<AreaTriggered> list = m_TriggeredObjects.get(filter);
            if(list != null){
                success = m_TriggeredObjects.get(filter).remove(actor);
            }
        }
           
        return success;
    }
    
    public boolean RemoveTrigger(TimeTrigger trigger){
        return m_TimeObjects.remove(trigger);
    }
    
    public void RemoveTrigger(AreaTrigger trigger){
         m_AreaObjects.remove(trigger);
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
    
    public void setGooglePlayInterface(GooglePlayInterface googlePlay){googleInterface = googlePlay;}
    
    
    public GooglePlayInterface getGooglePlayInterface(){return googleInterface;}
    
   
}
