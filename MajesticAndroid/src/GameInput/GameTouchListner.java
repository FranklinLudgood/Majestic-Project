/*******************************************************
 * File Name: GameTouchListner.java
 * Author: Franklin Ludgood
 * Date Created: 08-04-2015
 ********************************************************/
package GameInput;
import com.jme3.input.event.TouchEvent;


public interface GameTouchListner {
    
    public void onTouch(TouchEvent event, float tpf);
    
}
