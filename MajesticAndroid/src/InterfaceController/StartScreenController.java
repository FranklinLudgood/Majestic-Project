/*******************************************************
 * File Name: InGameAppState.java
 * Author: Franklin Ludgood
 * Date Created: 09-30-2015
 ********************************************************/
package InterfaceController;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import GameObjects.LevelManager;

public class StartScreenController implements ScreenController {
    
    private Nifty m_nifty;
    

    public void bind(Nifty nifty, Screen screen) {
        m_nifty = nifty;
    }

    public void onStartScreen() {
         
    }

    public void onEndScreen() {
         
    }
    
    public void startButton(){
        m_nifty.gotoScreen("OutOfGameScreen");
    }
    
    public void exitButton(){
       

    }
    
    public void yesButton(){
        LevelManager.GetInstance().getApplication().stop();  
    }
    
    public void noButton(){
            //m_nifty.closePopup("confirmPopup");
    }
    
    
    
}
