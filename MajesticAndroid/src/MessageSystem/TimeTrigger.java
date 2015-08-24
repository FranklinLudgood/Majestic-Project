/*******************************************************
 * File Name: TimeTrigger.java
 * Author: Franklin Ludgood
 * Date Created: 08-23-2015
 *******************************************************/
package MessageSystem;


public class TimeTrigger implements Triggerable  {
    
    
    private float m_delay;
    private boolean m_isActive;
    private boolean m_isTriggered;
    private Triggered m_Triggered;
    
    public TimeTrigger(float delay, Triggered triggered, boolean isActive){
        m_delay = delay;
        m_Triggered = triggered;
        m_isActive = isActive;
        m_isTriggered = false;
    }
    
    public void setTrigger(Triggered triggered) { m_Triggered = triggered;}
    
    public void setDelay(float delay){m_delay = delay;}
    
    public void setActive(boolean active){m_isActive = active;}
    
    public void reset() { m_isTriggered = false; }
    

    public void update(float tpf) {
        
        if(m_isActive == false)
            return;
        
        m_delay-=tpf;
        
        if(m_delay <= 0.0f && m_isActive == true && m_isTriggered == false){
            m_isTriggered = true;
            if(m_Triggered != null)
                OnTriggered();
        }
    }

    public boolean isActive() {return m_isActive;}

    public boolean isTriggered() {return m_isTriggered;}

    public void OnTriggered() {
        
        if(m_Triggered != null)
            m_Triggered.onTriggered();
    }
    
    
}
