/*******************************************************
 * File Name: ChangingBumper.java
 * Author: Franklin Ludgood
 * Date Created: 09-15-2015
 *******************************************************/
package States;

import GameObjects.BaseGameEntity;
import GameObjects.Bumper;
import GameObjects.Block;


public class ChangingBumper implements BumperInterface {
    
    public float m_delay;
    public float m_timer;
    
    public ChangingBumper(float delay){
        m_delay = delay;
        m_timer = delay;
    }
    

    @Override
    public void Enter(Bumper bumper, BumperInterface state) {
        bumper.getSpatial().setMaterial(Block.blueMaterial);
        bumper.setObjectType(BaseGameEntity.ObjectType.BLUE_BUMPER);
        m_timer = m_delay;
    }

    
    @Override
    public BumperInterface Update(Bumper bumper, float tpf) {
        
        m_timer-=tpf;
        if(m_timer <= 0.0f){
            
            switch(bumper.getObjectType()){
            
                case YELLOW_BUMPER:
                    bumper.setObjectType(BaseGameEntity.ObjectType.BLUE_BUMPER);
                    bumper.getSpatial().setMaterial(Block.blueMaterial);
                    break;
                    
                case BLUE_BUMPER:
                    bumper.setObjectType(BaseGameEntity.ObjectType.YELLOW_BUMPER);
                    bumper.getSpatial().setMaterial(Block.yellowMaterial);
                    break;
            
            }
            m_timer = m_delay;
        }
        
        return null;
    }

    
    @Override
    public void Exit(Bumper bumper, BumperInterface state) {
         m_timer = m_delay;
    }
    
}
