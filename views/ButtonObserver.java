package views;

/** Represents the Button Observer
* @author Sahil Dayal, Nicholas, Nathan
* @author Student
* @version GCIS Project
* @since Spring
*/

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import models.Location;
import models.LocationState;
import models.MinesweeperObserver;

public class ButtonObserver implements MinesweeperObserver {

    /** Creates details for the Button Observer
     * @param cellUpdated The Location that was updated
     * @param hint How the button would look like
     * @param unhint how the button would look like
     */
     
    private Button btn;

    public ButtonObserver(Button btn) {
        this.btn = btn;
    }

    @Override
    public void cellUpdated(Location location) {
        if (location.getState() == LocationState.EMPTY) {
            btn.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        } else if(location.getState() == LocationState.NUMBERED){
            String number = location.checkSurroundings() + "";
            btn.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            btn.setText(number);
        } else if(location.getState() == LocationState.MINE) {
            btn.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            btn.setText("!");
        } else {
            btn.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
            btn.setText("");
        }
    }

    @Override
    public void hint() {
        btn.setBorder(new Border(new BorderStroke(Color.PINK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    @Override
    public void unhint() {
        btn.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }
    
}
