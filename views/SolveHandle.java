package views;

import backtracker.Backtracker;
import backtracker.Configuration;

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
import models.MinesweeperConfiguration;
import models.MinesweeperException;

public class SolveHandle implements EventHandler<ActionEvent> {

     /** Creates details Hint Handler
     * @param handle The Location that was updated
     */

    private Minesweeper game;
    private gui gui;

    public SolveHandle(Minesweeper game, gui gui) {
        this.game = game;
        this.gui = gui;
    }

    public void handle (ActionEvent arg0) {
        Backtracker backtracker = new Backtracker(false);
        Configuration currentGame = new MinesweeperConfiguration(game);
        MinesweeperConfiguration solution = (MinesweeperConfiguration)backtracker.solve(currentGame);
        if(solution == null){
            gui.announcementText.setText("no solution");
        } else {
            for(Location step : solution.stepsTaken()){
                gui.announcementText.setText("Selection: " + step);
                try {
                    game.makeSelection(step);
                } catch (MinesweeperException e) {
                    e.printStackTrace();
                }
            }
        }
        // Backtracker backtracker = new Backtracker(false);
        // Configuration currentGame = new MinesweeperConfiguration(game);
        // MinesweeperConfiguration solution =(MinesweeperConfiguration) backtracker.solve(currentGame);

        // if (solution == null) {
        //     gui.announcementText.setText("No solution found.");
        // } else {
        //     String message = "" + solution.stepsTaken();
        //     gui.announcementText.setText(message);
        // }
    }
}
