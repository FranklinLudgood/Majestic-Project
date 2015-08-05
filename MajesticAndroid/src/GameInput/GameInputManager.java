/*******************************************************
 * File Name: CreateBodyFixture.java
 * Author: Franklin Ludgood
 * Date Created: 08-04-2015
 *******************************************************/
package GameInput;
import com.jme3.input.event.TouchEvent;
import java.util.List;
import java.util.ArrayList;

public class GameInputManager {
    
    private static GameInputManager m_AndroidInputManager;
    
    public static GameInputManager GetInstance(){
        
        if(m_AndroidInputManager == null)
            m_AndroidInputManager = new GameInputManager();
    
        return m_AndroidInputManager;
    }
    
    private List<GameOrientationListener> m_OrientationListenerList;
    private List<GameTouchListner> m_TouchListnerList;
    
    private GameInputManager(){
    
        m_OrientationListenerList = new ArrayList<GameOrientationListener>();
        m_TouchListnerList = new ArrayList<GameTouchListner>();
    }
    
    
    public void register(GameOrientationListener listener){
       if(! m_OrientationListenerList.contains(listener))
           m_OrientationListenerList.add(listener);
    }
    
    public void register(GameTouchListner listener){
        if(!m_TouchListnerList.contains(listener))
            m_TouchListnerList.add(listener);
    }
    
    public void unRegister(GameOrientationListener listener){
        m_OrientationListenerList.remove(listener);
    }
    
    public void unRegister(GameTouchListner listener){
        m_TouchListnerList.remove(listener);
    }
    
    public void SendTouchEvent(TouchEvent event, float tpf){
        for(int i = 0; i < m_TouchListnerList.size(); ++i)
            m_TouchListnerList.get(i).onTouch(event, tpf);
    }
    
    public void SendOrientationEvent(float EulerX, float EulerY, float EulerZ){
        for(int i = 0; i < m_OrientationListenerList.size(); ++i){
            m_OrientationListenerList.get(i).onChangedOrientation(EulerX, EulerY, EulerZ);
        }
    }
    
}
