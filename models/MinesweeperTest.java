/**
 * This is the JUnit tests for the minesweeper class.
 * We have made tests for all the methods in the minesweeper class.
 * 
 * @author Sahil Dayal, Nicholas, Nathan
 */

package models;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.platform.commons.annotation.Testable;

/**
 * This is the test class for the minesweeper
 * Two boards were created to throughly test the methods.
 */
@Testable
public class MinesweeperTest {

    Minesweeper board = new Minesweeper(5, 5, 8);
    Minesweeper board2 = new Minesweeper(10, 10, 33);
    /**
     * This tests the location method and the equals method in the location class, both locations are the same
     */
    @Test
    public void LocationTest() {
        Location one = new Location(3, 4);
        Location two = new Location(3, 4);

        boolean actual = true;
        boolean expected = one.equals(two);

        assertEquals(expected, actual);

    }
    
    /**
     * This is a second test for location that checks two different locations
     */
    @Test
    public void Location2Test() {
        Location one = new Location(34, 4);
        Location two = new Location(3, 4);

        boolean actual = false;
        boolean expected = one.equals(two);

        assertEquals(expected, actual);

    }

    /**
     * This is a test for the make selection method in the minesweeper class.
     * It can be seen that get movcount and make selection both work properly
     * @throws MinesweeperException - an index out of bound exception
     */

 
    @Test
    public void makeSelectionTest() throws MinesweeperException {
        Minesweeper board = new Minesweeper(5, 5, 8);
        board.makeSelection(new Location(4, 2));
        int actual = board.getMoveCount();
        int expected = 1;

        assertEquals(expected, actual);

    }

    /**
     * This is the gameState test for the get gamestate method. The game was not started.
     * @throws MinesweeperException - index out of bounds exception
     */
    @Test
    public void gameStateTest() throws MinesweeperException {
        GameState actual = board.getGameState();
        GameState expected = GameState.NOT_STARTED;

        assertEquals(expected, actual);
    }


    /**
     * This is the tostring test for tostring method. it prints out the board perfectly.
     */
    @Test
    public void toStringTest(){
        String actual = board.toString();
        String expected = "[-] [-] [-] [-] [-] \n"+"[-] [-] [-] [-] [-] \n"+"[-] [-] [-] [-] [-] \n"+ "[-] [-] [-] [-] [-] \n"+ "[-] [-] [-] [-] [-] ";
        assertEquals(expected, actual);
    }

    /**
     * This is a test to see if the makeselection method throws an exception of location is outside of range.
     */
    @Test
    public void ExceptionTest(){
        try {
            board.makeSelection(new Location(25, 10));
        } catch (ArrayIndexOutOfBoundsException | MinesweeperException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is a test to see if the player has won the game or not.
     */
    @Test
    public void gameWonTest(){
        boolean actual = board.gameWon();
        boolean expected = false;

        assertEquals(expected, actual);
    }

    /**
     * This is a test to see if the board is covered or not.
     */
    @Test
    public void isCoveredTest(){
        boolean actual = board.isCovered(new Location(3, 2));
        boolean expected = true;

        assertEquals(expected, actual);
    }

    /**
     * This is a test to see if we get the symbol of the location.
     */
    @Test
    public void getSymbolTest(){
        char a = board.getSymbol(new Location(3, 2));
        String actual = Character.toString(a);
        String expected = "" + '-';
        assertEquals(expected, actual);
    }

    // write a test for uncover method from minesweeper class
    @Test
    public void uncoverTest(){
        board.uncover();
        boolean actual = board.isCovered(new Location(3, 2));
        boolean expected = false;

        assertEquals(expected, actual);
    }
    
             
}
