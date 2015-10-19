/*******************************************************
 * File Name: OutOfGameScreensController.java
 * Author: Franklin Ludgood
 * Date Created: 10-11-2015
 ********************************************************/
package InterfaceController;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;


public class OutOfGameScreensController implements ScreenController {
    
    private Nifty m_nifty;

    @Override
    public void bind(Nifty nifty, Screen screen) {
         m_nifty = nifty;
    }

    @Override
    public void onStartScreen() {
         
    }

    @Override
    public void onEndScreen() {
        
    }
    
    public void loadProfileButton(){
        m_nifty.gotoScreen("start");
    }
    
    public void settingsButton(){
        m_nifty.gotoScreen("SettingsScreen");
    }
    
}
