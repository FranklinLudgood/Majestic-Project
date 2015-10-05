/*******************************************************
 * File Name: IntroGameController.java
 * Author: Franklin Ludgood
 * Date Created: 10-03-2015
 ********************************************************/
package InterfaceController;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import MessageSystem.MessageCenter;
import MessageSystem.TimedTriggered;
import MessageSystem.TimeTrigger;

public class IntroGameController implements ScreenController, TimedTriggered {
    
    Nifty m_nifty;

    public void bind(Nifty nifty, Screen screen) {
        m_nifty = nifty;
        MessageCenter messageCenter = MessageCenter.GetInstance();
        TimeTrigger trigger = new TimeTrigger(25.0f, this, true);
        messageCenter.CreateTimeDelay(trigger);
    }

    public void onStartScreen() {
        //TODO:add effects and sounds.
        
    }

    public void onEndScreen() {
       //TODO:add effects and sounds.
        
    }

    public void onTriggered() {
         m_nifty.gotoScreen("SplashScreen");
    }
    
}
