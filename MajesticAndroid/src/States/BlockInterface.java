/*******************************************************
 * File Name: BlockInterface.java
 * Author: Franklin Ludgood
 * Date Created: 09-02-2015
 *******************************************************/
package States;
import GameObjects.Block;


public interface BlockInterface {
    
    public void Enter(Block block, BlockInterface state);
    public BlockInterface Update(Block block, float tpf);
    public void Exit(Block block, BlockInterface state);
    
}
