package views;

/** Represents The Hints Handler
* @author Sahil Dayal, Nicholas, Nathan
* @author Student
* @version GCIS Project
* @since Spring
*/

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import models.Location;
import models.Minesweeper;

public class HintHandle implements EventHandler<ActionEvent> {

     /** Creates details Hint Handler
     * @param handle The Location that was updated
     */

    private Minesweeper game;

    public HintHandle(Minesweeper game) {
        this.game = game;
    }

    @Override
    public void handle(ActionEvent arg0) {
        Location place = (Location) game.getPossibleSelections().toArray()[0];
        game.hintObserver(place);
    }
    
}
