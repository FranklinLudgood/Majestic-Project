/*******************************************************
 * File Name: GameUpdate.java
 * Author: Franklin Ludgood
 * Date Created: 09-28-2015
 *******************************************************/
package MessageSystem;


public class GameUpdate {
    
   private int m_CurrentScore;
   private int m_CurrentMultipler;
   private int m_CurrentHealth;
   
   public GameUpdate(int CurrentScore, int CurrentMultipler,
                    int CurrentHealth){
   
       m_CurrentScore = CurrentScore;
       m_CurrentMultipler = CurrentMultipler;
       m_CurrentHealth = CurrentHealth;
   
   }
   
   
   public int getCurrentScore(){return m_CurrentScore;}
   public int getCurrentMultipler(){ return m_CurrentMultipler;}
   public int getCurrentHealth(){return m_CurrentHealth;}
}
