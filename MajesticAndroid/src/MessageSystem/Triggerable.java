/*******************************************************
 * File Name: Triggerable.java
 * Author: Franklin Ludgood
 * Date Created: 08-23-2015
 *******************************************************/
package MessageSystem;


public interface Triggerable {
    
    public void update(float tpf);
    public boolean isActive();
    public boolean  isTriggered();
    public void OnTriggered();
    
}
