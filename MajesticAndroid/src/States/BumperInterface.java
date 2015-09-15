/*******************************************************
 * File Name: BumperInterface.java
 * Author: Franklin Ludgood
 * Date Created: 09-15-2015
 *******************************************************/
package States;
import GameObjects.Bumper;


public interface BumperInterface {
    
    public void Enter(Bumper bumper, BumperInterface state);
    public BumperInterface Update(Bumper bumper, float tpf);
    public void Exit(Bumper bumper, BumperInterface state);
      
}
