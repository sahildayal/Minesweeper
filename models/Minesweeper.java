/**
 * Contains the game logic for the Minesweeper game
 * 
 * @author Nicholas Boulos, Sahil Dayal, Nathan Z
 */

package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Minesweeper {
    public static final char MINE = 'M';
    public static final char COVERED = '-';

    private GameState currState = GameState.NOT_STARTED;
    private final int rows;
    private final int cols;
    private final int mineCount;
    private final Location[][] gameBoard;
    private int moves;
    public final HashMap<Location, MinesweeperObserver> observer;
    private Location hinted;
    
    public static void main(String[] args) {
        Minesweeper minesweeper = new Minesweeper(5, 5, 3);
        System.out.println(minesweeper);
    }

    public void reset() {
        moves = 0;
        hinted = null;
        currState = GameState.NOT_STARTED;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                gameBoard[row][col].reset();
                observer.get(gameBoard[row][col]).cellUpdated(gameBoard[row][col]);
            }
        }
    }

    public Minesweeper(Minesweeper minesweeper){
        this.rows = minesweeper.rows;
        this.cols = minesweeper.cols;
        this.mineCount = minesweeper.mineCount;
        this.gameBoard = new Location[rows][cols];
        this.moves = minesweeper.moves;
        this.observer = minesweeper.observer;
        this.hinted = minesweeper.hinted;
        this.currState = minesweeper.currState;

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                gameBoard[y][x] = minesweeper.gameBoard[y][x].copy();
            }
        }

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                for (int xdiff = -1; xdiff < 2; xdiff++) {
                    for (int ydiff = -1; ydiff < 2; ydiff++) {
                        try {
                            if (!(xdiff == 0 && ydiff == 0)) {
                                gameBoard[y][x].addNeighbor(gameBoard[y+ydiff][x+xdiff]);
                            }
                        } catch (IndexOutOfBoundsException ignored) {
                        }
                    }
                }
            }
        }
    }

    public Minesweeper(int rows, int cols, int mineCount){
        this.rows = rows;
        this.cols = cols;
        this.mineCount = mineCount;
        this.gameBoard = new Location[rows][cols];
        this.moves = 0;
        this.observer = new HashMap<>();

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                gameBoard[y][x] = new Location(y, x);
            }
        }

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                for (int xdiff = -1; xdiff < 2; xdiff++) {
                    for (int ydiff = -1; ydiff < 2; ydiff++) {
                        try {
                            if (!(xdiff == 0 && ydiff == 0)) {
                                gameBoard[y][x].addNeighbor(gameBoard[y+ydiff][x+xdiff]);
                            }
                        } catch (IndexOutOfBoundsException ignored) {
                        }
                    }
                }
            }
        }
    }

    /**
     * Method attempts to make a move and the given location if the move is valid. Otherwise
     * the method throws a MinesweeperException.
     * 
     * @param location Location that the method attempts to make a move on
     * @throws MinesweeperException If the location is invalid or bombed throw exception
     */
    public void makeSelection(Location location) throws MinesweeperException {
        //Initializes locations row and col
        int selectionRow = location.getRow();
        int selectionCol = location.getCol();

        //If the game has not started populate the board with mines
        if(currState == GameState.NOT_STARTED){
            Random random = new Random();
            int mines = 0;
            while(mines < mineCount){
                int row = random.nextInt(rows);
                int col = random.nextInt(cols);
                //While row and col are within 1 cell of the selected location or are on the selected location find a new number
                if (row <= selectionRow + 1 && row >= selectionRow - 1) {
                    if (col <= selectionCol + 1 && col >= selectionCol - 1) {
                        continue;
                    }
                }

                //sets the mines at the location
                if (!gameBoard[row][col].getMine()) {
                    gameBoard[row][col].setMine();
                    mines++;
                }
            }
            currState = GameState.IN_PROGRESS;
        }

        //Gets the location at the selection's location
        Location selection = gameBoard[selectionRow][selectionCol];
        
        //If the selection has a hidden state try to reveal it. If it's bombed then throw exception otherwise change state to empty and increment moves

        for (Location revealed : selection.reveal()) {
            if(checkObservers(observer) == true ){
                notifyObserver(revealed);
            }
        }
        
        if (selection.getState() == LocationState.MINE) {
            throw new MinesweeperException();
        } else if (gameWon()) {
            throw new MinesweeperException();
        }

        moves++;
    }

    /**
     * Getter method that returns the number of moves
     * @return
     */
    public int getMoveCount(){return moves;}

    /**
     * Getter method that returns the current game state
     * @return
     */
    public GameState getGameState(){return currState;}

    /**
     * Checks if game is in a winning state
     * @return
     */
    public boolean gameWon(){
        int hiddenCount = 0;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if(gameBoard[i][j].getState() == LocationState.HIDDEN) {
                    hiddenCount++;
                }
            }
        }
        //Returns false if a Hidden tile was found
        return hiddenCount == mineCount;
    }


    /**
     * Returns a collection of valid cell locations that are currently covered
     * @return
     */
    public Collection<Location> getPossibleSelections(){
        List<Location> selections = new ArrayList<>();
        for (Location[] row : gameBoard) {
            for (Location cell : row) {
                if (cell.getState() == LocationState.HIDDEN) {
                    selections.add(cell);
                }
            }
        }
        return selections;
    }

    @Override
    public String toString() {
        String display = "";
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                if (gameBoard[y][x].getState() == LocationState.EMPTY) {
                    display += "[ ] ";
                } else if (gameBoard[y][x].getState() == LocationState.HIDDEN) {
                    display += "[-] ";
                } else if (gameBoard[y][x].getState() == LocationState.NUMBERED) {
                    display += "[" + gameBoard[y][x].checkSurroundings() + "] ";
                } else {
                    display += "[!] ";
                }
            }
            if (y + 1 != rows) {
                display += "\n";
            }
        }
        return display;
    }

    public void uncover() {
        for (Location remainder : getPossibleSelections()) {
            remainder.reveal();
            if(checkObservers(observer) == true){
                notifyObserver(remainder);
            }
        }
    }

    public int getRows(){return rows;}
    public int getCols(){return cols;}
    public int mineCount(){return mineCount;}

    public boolean isCovered(Location location) {
        return location.getState() == LocationState.HIDDEN;
    }

    public char getSymbol(Location location) {
        if (location.getMine()) {
            return MINE;
        } else if (location.getState() == LocationState.HIDDEN) {
            return COVERED;
        } else if (location.getState() == LocationState.EMPTY) {
            return '0';
        } else {
            return ("" + location.checkSurroundings()).charAt(0);
        }
    }

    /**
     * Takes in a MinesweeperObserver and registers it as the observer.
     * @param observer Observer that will be registered
     */
    public void register(Location location, MinesweeperObserver observer) {
        this.observer.put(location, observer);
    }

    // public void printMines() {
    //     for (int y = 0; y < gameBoard.length; y++) {
    //         for (int x = 0; x < gameBoard[y].length; x++) {
    //             if (gameBoard[y][x].getMine() == true) {
    //                 System.out.println("XY: " + x + " " + y);
    //             }
    //         }
    //     }
    // }

    /**
     * Notifies observer when a location has been updated.
     * @param location passes location that's been updated.
     */
    public void notifyObserver(Location location) {
        if (this.observer.get(hinted) != null ) {
            if (hinted != null) {
                this.observer.get(hinted).unhint();
            }
        }
        this.observer.get(location).cellUpdated(location);
        
    }

    public boolean checkObservers(HashMap<Location, MinesweeperObserver> observer){
        if(observer.isEmpty()){
            return false;
        } else {
            return true;
        }
    }

    public void hintObserver(Location location) {
        if (hinted != null) {
            this.observer.get(hinted).unhint();
        }
        observer.get(location).hint();
    }
}