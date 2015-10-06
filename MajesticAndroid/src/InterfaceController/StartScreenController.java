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
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void onEndScreen() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void exitButton(){
       Element popUp = m_nifty.createPopup("confirmPopup");
       m_nifty.showPopup(m_nifty.getCurrentScreen(), popUp.getId(), null);

    }
    
    public void yesButton(){
        LevelManager.GetInstance().getApplication().stop();  
    }
    
    public void noButton(){
            m_nifty.closePopup("confirmPopup");
    }
    
    
    
}
