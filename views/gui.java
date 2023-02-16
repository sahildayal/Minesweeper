package views;

/** Represents GUI
* @author Sahil Dayal, Nicholas, Nathan
* @author Student
* @version GCIS Project
* @since Spring
*/

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Location;
import models.Minesweeper;

public class gui extends Application {

     /** Creates details for the GUI
     * @param primaryStage The Stage
     * @param scoreStyleSetup The Style of the Score
     * @param start starts the game
     * @param makeCells makes the cells
     * @param endgame ends the game
     */

    private GridPane gameDisplay = new GridPane();
    private final Label mineCount = new Label("Mines: 4");
    public final Label moveLabel = new Label("Moves: 0");
    protected final Label announcementText = new Label("");
    private final Button hintButton = new Button("Hint");
    private final Button resetButton = new Button("Reset");
    private final Button solveButton = new Button("Solve");
    private int rows = 10;
    private int cols = 10;
    private Minesweeper minesweeper = new Minesweeper(10, 10, 30);

    private void scoreStyleSetup(HBox vbox) {
        mineCount.setBackground(new Background(new BackgroundFill(Color.CYAN, CornerRadii.EMPTY, Insets.EMPTY)));
        moveLabel.setBackground(new Background(new BackgroundFill(Color.CYAN, CornerRadii.EMPTY, Insets.EMPTY)));
        mineCount.prefWidth(30);
        mineCount.prefHeight(20);
        moveLabel.prefWidth(30);
        moveLabel.prefHeight(20);
        hintButton.prefWidth(30);
        hintButton.prefHeight(20);
        resetButton.prefWidth(30);
        resetButton.prefHeight(20);
        solveButton.prefWidth(30);
        solveButton.prefHeight(20);
        vbox.getChildren().addAll(
            mineCount,
            moveLabel,
            hintButton,
            resetButton,
            solveButton
        );
        vbox.setSpacing(10);
        announcementText.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        announcementText.setMinHeight(30);
        announcementText.setMaxWidth(Double.MAX_VALUE);
    }

    public void reset() {
        moveLabel.setText("Moves: 0");
        announcementText.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        announcementText.setText("");
        minesweeper.reset();
    }


    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox mainDisplay = new VBox();
        HBox scoreDisplay = new HBox();
        mainDisplay.getChildren().addAll(
            scoreDisplay, gameDisplay, announcementText
        );

        scoreStyleSetup(scoreDisplay);

        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                gameDisplay.add(makeCell(x, y), x, y);
            }
        }

        hintButton.setOnAction(new HintHandle(minesweeper));
        resetButton.setOnAction(new ResetHandle(this));
        solveButton.setOnAction(new SolveHandle(minesweeper, this));

        Scene scene = new Scene(mainDisplay);
        stage.setScene(scene);
        stage.show();
    }

    public Button makeCell(int x, int y) {
        Button btn = new Button();
        Location location = new Location(y, x);
        btn.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        btn.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        btn.setOnAction(new PressHandle(minesweeper, this, location));
        btn.setPrefSize(30, 30);
        ButtonObserver btnObserver = new ButtonObserver(btn);
        minesweeper.register(location, btnObserver);
        return btn;
    }

    public void endGame() {
        if (minesweeper.gameWon()) {
            announcementText.setText("You win");
            announcementText.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        } else {
            announcementText.setText("You lose");
            announcementText.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        }
        minesweeper.uncover();
    }
}
