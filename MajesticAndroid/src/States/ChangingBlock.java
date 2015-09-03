/*******************************************************
 * File Name: ChangingBlock.java
 * Author: Franklin Ludgood
 * Date Created: 09-03-2015
 *******************************************************/
package States;
import GameObjects.BaseGameEntity;
import GameObjects.Blocks;
import com.jme3.scene.Spatial;


public class ChangingBlock implements BlockInterface {
    
    private static ChangingBlock m_block;
    
    public static ChangingBlock GetInstance(){
        
        if(m_block == null)
            m_block = new ChangingBlock();
    
    
        return m_block;
    }
    
    @Override
    public void Enter(Blocks block, BlockInterface state){
             block.getSpatial().setMaterial(Blocks.blueMaterial);
             block.setObjectType(BaseGameEntity.ObjectType.BLUE_BLOCK);
             float timer = block.getDelay();
             block.setTimer(timer);           
    }
    
    @Override
    public BlockInterface Update(Blocks block, float tpf){
    
        float timer = block.getTimer() - tpf;
        if(timer <= 0.0f){
            block.setTimer(block.getDelay());
            
            if(block.getSpatial().getMaterial() == Blocks.blueMaterial){
                block.getSpatial().setMaterial(Blocks.yellowMaterial);
                block.setObjectType(BaseGameEntity.ObjectType.YELLOW_BLOCK);
            }
            else{
                block.getSpatial().setMaterial(Blocks.blueMaterial);
                block.setObjectType(BaseGameEntity.ObjectType.BLUE_BLOCK);
            }
        }else
            block.setTimer(timer);
        
        return null;
    }
    
    @Override
    public void Exit(Blocks block, BlockInterface state){
    }
}
