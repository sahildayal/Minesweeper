package views;

/** Represents Press Handle
* @author Sahil Dayal, Nicholas, Nathan
* @author Student
* @version GCIS Project
* @since Spring
*/

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import models.Location;
import models.LocationState;
import models.Minesweeper;
import models.MinesweeperException;

public class PressHandle implements EventHandler<ActionEvent> {

     /** Creates details for the Press Handle
      * @param handle The Location that was updated
      */

    private Minesweeper game;
    private gui gui;
    private Location handled;

    public PressHandle(Minesweeper game, views.gui gui, Location handled) {
        this.game = game;
        this.gui = gui;
        this.handled = handled;
    }

    @Override
    public void handle(ActionEvent arg0) {
        if (handled.getState() != LocationState.HIDDEN) {
            return;
        }
        try {
            game.makeSelection(handled);
            gui.moveLabel.setText("Moves: " + game.getMoveCount());
        } catch (MinesweeperException e) {
            gui.endGame();
        }
    }
    
}
