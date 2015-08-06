/*******************************************************
 * File Name: PlayerControl.java
 * Author: Franklin Ludgood
 * Date Created: 08-04-2015
 *******************************************************/
package GameInput;

/**
 *
 * @author Rashaan
 */
public class GameInputFilter {
    
    public static float weightLowPass = 0.0f;
    public static float weightHighPass = 0.0f;
    
    public static float LowPassFilter(float currentValue, float previousValue){
        float answer = previousValue *(1.0f - weightLowPass) + currentValue * weightLowPass;
        return answer;
    }
    
    public static float HighPassFilter(float currentValue, float previousValue, float filtered){
        float answer = weightHighPass * (filtered + currentValue - previousValue);
        return answer;
    }
    
}
