/*******************************************************
 * File Name: SplashScreenController.java
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


public class SplashScreenController implements ScreenController, TimedTriggered {
    
    private Nifty m_nifty;

    public void bind(Nifty nifty, Screen screen) {
         m_nifty = nifty;
         MessageCenter center = MessageCenter.GetInstance();
         TimeTrigger timer = new TimeTrigger(5.0f, this, true);
         center.CreateTimeDelay(timer);
    }

    public void onStartScreen() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void onEndScreen() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void onTriggered() {
         m_nifty.gotoScreen("start");
    }
    
}
