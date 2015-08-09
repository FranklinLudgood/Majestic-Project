/*******************************************************
 * File Name: PlayerProfile.java
 * Author: Franklin Ludgood
 * Date Created: 08-03-2015
 *******************************************************/
package GameObjects;

/**
 *
 * @author Rashaan
 * TODO: 
 */
public class PlayerProfile {
    
    private static PlayerProfile m_profile;
    
    public static PlayerProfile GetInstance(){
        
        if(m_profile == null)
            m_profile = new PlayerProfile();
        
        
        return m_profile;
    }
    
    public String Name;
    public int total_points;
    public int total_amount_health_lost;
    public int total_amount_health_received;
    public float total_air_time; //time is in seconds.
    public int challenges_completed;
    public float win_lose_ratio;
    public float tilt_coefficient;
    public float stop_coefficient;
    
    
    private PlayerProfile(){
    
        total_points = 0;
        total_amount_health_lost = 0;
        total_amount_health_received = 0;
        total_air_time = 0.0f; 
        challenges_completed = 0;
        win_lose_ratio = 0.0f;
        tilt_coefficient = 0.0f;
        stop_coefficient = 0.0f;
    
    }
    
}
