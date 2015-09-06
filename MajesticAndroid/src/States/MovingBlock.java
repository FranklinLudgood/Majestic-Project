/*******************************************************
 * File Name: MovingBlock.java
 * Author: Franklin Ludgood
 * Date Created: 09-02-2015
 *******************************************************/
package States;
import GameObjects.Blocks;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.MotionPathListener;
import com.jme3.cinematic.events.MotionEvent;

public class MovingBlock implements BlockInterface {
    
    private static MovingBlock m_block;
    
    public static MovingBlock GetInstance(){
    
        if(m_block == null)
            m_block = new MovingBlock();
    
        return m_block;
    }
    
    public void Enter(Blocks block, BlockInterface state);
    public BlockInterface Update(Blocks block, float tpf);
    public void Exit(Blocks block, BlockInterface state);
    
}
