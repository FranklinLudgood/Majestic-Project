/*******************************************************
 * File Name: BlockInterface.java
 * Author: Franklin Ludgood
 * Date Created: 09-02-2015
 *******************************************************/
package States;
import GameObjects.Blocks;


public interface BlockInterface {
    
    public void Enter(Blocks block, BlockInterface state);
    public BlockInterface Update(Blocks block, float tpf);
    public void Exit(Blocks block, BlockInterface state);
    
}
