/*******************************************************
 * File Name: ChangingBlock.java
 * Author: Franklin Ludgood
 * Date Created: 09-03-2015
 *******************************************************/
package States;
import GameObjects.BaseGameEntity;
import GameObjects.Block;



public class ChangingBlock implements BlockInterface {
    
    private static ChangingBlock m_block;
    
    public static ChangingBlock GetInstance(){
        
        if(m_block == null)
            m_block = new ChangingBlock();
    
    
        return m_block;
    }
    
    @Override
    public void Enter(Block block, BlockInterface state){
             block.getSpatial().setMaterial(Block.blueMaterial);
             block.setObjectType(BaseGameEntity.ObjectType.BLUE_BLOCK);
             float timer = block.getDelay();
             block.setTimer(timer);           
    }
    
    @Override
    public BlockInterface Update(Block block, float tpf){
    
        float timer = block.getTimer() - tpf;
        if(timer <= 0.0f){
            block.setTimer(block.getDelay());
            
            if(block.getObjectType() == BaseGameEntity.ObjectType.BLUE_BLOCK){
                block.getSpatial().setMaterial(Block.yellowMaterial);
                block.setObjectType(BaseGameEntity.ObjectType.YELLOW_BLOCK);
            }
            else{
                block.getSpatial().setMaterial(Block.blueMaterial);
                block.setObjectType(BaseGameEntity.ObjectType.BLUE_BLOCK);
            }
        }else
            block.setTimer(timer);
        
        return null;
    }
    
    @Override
    public void Exit(Block block, BlockInterface state){
            float timer = block.getDelay();
             block.setTimer(timer);
    }
}
