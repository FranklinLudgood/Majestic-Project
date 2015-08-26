/*******************************************************
 * File Name: GameMessage.java
 * Author: Franklin Ludgood
 * Date Created: 08-25-2015
 *******************************************************/
package MessageSystem;


public class GameMessage {
    
    private String m_message;
    private Object m_package;
    private int m_senderID;
    private int m_receiverID;
    
    
    public GameMessage(String message, Object object, int senderID, int receiverID){
            m_message = message;
            m_package = object;
            m_senderID = senderID;
            m_receiverID = receiverID;
    }
    
    public String GetMessage(){return m_message;}
    public Object GetPackage(){return m_package;}
    public int GetSenderID(){return m_senderID;}
    public int GetReceiverID(){return m_receiverID;}
    
}
