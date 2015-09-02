/*******************************************************
 * File Name: PlayerProfile.java
 * Author: Franklin Ludgood
 * Date Created: 08-03-2015
 *******************************************************/
package GameObjects;


public class PlayerProfile {
    
    private static PlayerProfile m_profile;
    
    
    //TODO: finish this function.
    public static boolean LoadPrayerProfile(String filePath){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public static PlayerProfile GetInstance(){
        
        if(m_profile == null)
            m_profile = new PlayerProfile();
        
        
        return m_profile;
    }
    
    public String Name;
    public int max_health;
    public int total_points;
    public int total_amount_health_lost;
    public int total_amount_health_received;
    public float total_air_time; //time is in seconds.
    public int challenges_completed;
    public float win_lose_ratio;
    public float tilt_coefficient;
    public float stop_coefficient;
    public float isZero;
    public float speed;
    
    
    private PlayerProfile(){
    
        total_points = 0;
        total_amount_health_lost = 0;
        total_amount_health_received = 0;
        total_air_time = 0.0f; 
        challenges_completed = 0;
        win_lose_ratio = 0.0f;
        tilt_coefficient = 0.25f;
        stop_coefficient = 0.5f;
        isZero = 0.05f;
        max_health = 3;
    
    }
    
}
