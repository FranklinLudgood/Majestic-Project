/*******************************************************
 * File Name: InGameAppState.java
 * Author: Franklin Ludgood
 * Date Created: 09-30-2015
 ********************************************************/
package InterfaceController;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import GameObjects.LevelManager;
//import MessageSystem.GameMessage;
import MessageSystem.MessageCenter;
import MessageSystem.BroadCastResponse;
import MessageSystem.GameBroadCast;

public class StartScreenController implements ScreenController, BroadCastResponse {
    
    
    private Nifty m_nifty;
    

    public void bind(Nifty nifty, Screen screen) {
        m_nifty = nifty;
        m_nifty.removeScreen("IntroScreen");
        m_nifty.removeScreen("SplashScreen");
        MessageCenter.GetInstance().addBroadCastResponse(this, GameBroadCast.BroadCastType.GOOGLE_PLAY_SUCESS);
        MessageCenter.GetInstance().addBroadCastResponse(this, GameBroadCast.BroadCastType.GOOGLE_PLAY_FAILURE);
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
        LevelManager.GetInstance().getApplication().stop();  
    }
    
    public void noButton(){
            //m_nifty.closePopup("confirmPopup");
    }

    public void Response(GameBroadCast broadCast) {
        if(broadCast.GetType() == GameBroadCast.BroadCastType.GOOGLE_PLAY_SUCESS)
            m_nifty.gotoScreen("OutOfGameScreen");
    }
 
    
}
