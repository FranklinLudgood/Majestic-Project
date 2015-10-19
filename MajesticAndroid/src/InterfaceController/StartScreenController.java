/*******************************************************
 * File Name: InGameAppState.java
 * Author: Franklin Ludgood
 * Date Created: 09-30-2015
 ********************************************************/
package InterfaceController;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import MessageSystem.MessageCenter;


public class StartScreenController implements ScreenController {
    
    
    private Nifty m_nifty;
    

    public void bind(Nifty nifty, Screen screen) {
        m_nifty = nifty;
        m_nifty.removeScreen("IntroScreen");
        m_nifty.removeScreen("SplashScreen");
    }

    public void onStartScreen() {
         
    }

    public void onEndScreen() {
         
    }
    
    public void startButton(){
        MessageCenter.GetInstance().getGooglePlayInterface().RequestGooglePlayAccount();
    }
    
    public void exitButton(){
       

    }
    
    public void yesButton(){
         
    }
    
    public void noButton(){
            
    }

  
    
}
