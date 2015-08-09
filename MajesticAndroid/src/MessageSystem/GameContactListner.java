/*******************************************************
 * File Name: GameContactListner.java
 * Author: Franklin Ludgood
 * Date Created: 08-09-2015
 *******************************************************/
package MessageSystem;
import org.dyn4j.dynamics.contact.ContactListener;
import org.dyn4j.dynamics.contact.ContactPoint;
import org.dyn4j.dynamics.contact.PersistedContactPoint;
import org.dyn4j.dynamics.contact.SolvedContactPoint;

//TODO:Finish This Class.
public class GameContactListner implements ContactListener {

    public void sensed(ContactPoint point) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean begin(ContactPoint point) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void end(ContactPoint point) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean persist(PersistedContactPoint point) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean preSolve(ContactPoint point) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void postSolve(SolvedContactPoint point) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
