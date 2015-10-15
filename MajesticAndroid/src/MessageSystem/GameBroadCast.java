/*******************************************************
 * File Name: GameBroadCast.java
 * Author: Franklin Ludgood
 * Date Created: 08-26-2015
 *******************************************************/
package MessageSystem;


public class GameBroadCast {
    
    public static enum BroadCastType{GAME_WON, GAME_LOST, UPDATE_SCORE, UPDATE_HEALTH, UPDATE_MULTIPLER, UPDATE_GAME, GOOGLE_PLAY_SUCESS, GOOGLE_PLAY_FAILURE}
    
    private String m_message;
    private Object m_package;
    private BroadCastType m_type;

    public GameBroadCast(String message, Object object, BroadCastType type) {
        this.m_message = message;
        this.m_package = object;
        this.m_type = type;
    }
    
    public String GetMessage(){return m_message;}
    public Object GetPackage(){return m_package;}
    public BroadCastType GetType(){return m_type;}
        
}
