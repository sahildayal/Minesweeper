package views;

/** Represents Reset Handler
* @author Sahil Dayal, Nicholas, Nathan
* @author Student
* @version GCIS Project
* @since Spring
*/

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ResetHandle implements EventHandler<ActionEvent> {

    /** Creates details for the Reset Handler
     * @param handle The Location that was updated and reset 
     */

    private gui gui;

    public ResetHandle(views.gui gui) {
        this.gui = gui;
    }

    @Override
    public void handle(ActionEvent arg0) {
        gui.reset();
    }
    
}
