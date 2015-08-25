/*******************************************************
 * File Name: RingBuffer.java
 * Author: Franklin Ludgood
 * Date Created: 08-24-2015
 *******************************************************/
package MessageSystem;
import java.lang.reflect.Array;


public class RingBuffer<Type> {
    
    private Type[] m_types;
    private int m_head;
    private int m_tail;

    public RingBuffer(Class<Type> clazz, int capacity) {
        m_types = (Type[]) Array.newInstance(clazz, capacity);
        m_head = m_tail = 0;
    }
    
    
    public boolean isEmpty() {return(m_head == m_tail);}
    
    public Type getHead(){
    
        if(isEmpty())
            return null;
        
        return m_types[m_head];
    }
    
    public void Enqueue(Type type){
        m_types[m_tail] = type;
        m_tail = (m_tail + 1) % m_types.length;
    }
    
    public void Dequeue(){
    
        if(m_head == m_tail)
            return;
        
        m_head = (m_head + 1) % m_types.length;
    
    }
    
    
}
