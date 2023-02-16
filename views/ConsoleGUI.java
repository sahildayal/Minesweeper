package views;

/** Represents Console GUI
* @author Sahil Dayal, Nicholas, Nathan
* @author Student
* @version GCIS Project
* @since Spring
*/

import java.util.Scanner;

import backtracker.Backtracker;
import backtracker.Configuration;
import models.GameState;
import models.Location;
import models.Minesweeper;
import models.MinesweeperConfiguration;
import models.MinesweeperException;

public class ConsoleGUI {

     /** Creates details for Console GUI
     * @param Minesweeper game
     */

    private Minesweeper minesweeper;
    private final String cmds = "Commands:\n" +
            "        help - this help message\n" +
            "        pick <row> <col> - uncovers cell a <row> <col>\n" +
            "        hint - displays a safe selection\n" +
            "        reset - resets to a new game\n" +
            "        quit - quits the game\n" + 
            "        solve - invokes backtracking algorithm to solve the game for you";

    public static void main(String[] args) throws MinesweeperException {
        ConsoleGUI gui = new ConsoleGUI();
        gui.run();
    }

    public void run() throws MinesweeperException {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Mines: ");
            int mineCount = scanner.nextInt();
            System.out.print("Height: ");
            int boardHeight = scanner.nextInt();
            System.out.print("Width: ");
            int boardWidth = scanner.nextInt();
            minesweeper = new Minesweeper(boardHeight, boardWidth, mineCount);
            System.out.println(cmds);

            try {
                while (!minesweeper.gameWon()) {
                    System.out.println(minesweeper);
                    System.out.println("\nMoves: " + minesweeper.getMoveCount());
                    System.out.print("\nEnter a command: ");
                    String cmd = "";
                    while (cmd.equals("")) {
                        cmd = scanner.nextLine();
                    }
                    if (cmd.startsWith("help")) {
                        System.out.println(cmds);
                    } else if (cmd.startsWith("pick")) {
                        String[] parts = cmd.split(" ", 3);
                        Location pick = new Location(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                        if (!minesweeper.getPossibleSelections().contains(pick)) {
                            System.out.println("Please select a valid location.");
                        } else {
                            minesweeper.makeSelection(pick);
                        }
                    } else if (cmd.startsWith("hint")) {
                        System.out.println("Give " + minesweeper.getPossibleSelections().toArray()[0] + " a try!");
                    } else if (cmd.startsWith("reset")) {
                        minesweeper = new Minesweeper(boardHeight, boardWidth, mineCount);
                    } else if (cmd.startsWith("quit")) {
                        return;
                    } else if(cmd.startsWith("solve")){
                        if (minesweeper.getGameState() == GameState.NOT_STARTED) {
                            System.out.println("Please start the game before using solve.");
                            continue;
                        }
                        Backtracker backtracker = new Backtracker(false);
                        Configuration currentGame = new MinesweeperConfiguration(minesweeper);
                        MinesweeperConfiguration solution = (MinesweeperConfiguration)backtracker.solve(currentGame);
                        if(solution == null){
                            System.out.println("no solution");
                        } else {
                            for(Location step : solution.stepsTaken()){
                                System.out.println("Selection: " + step);
                                minesweeper.makeSelection(step);
                                System.out.println(minesweeper);
                            }
                        }
                    } else {
                        System.out.println("Unknown command. Try 'help' to view possible commands.");
                    }
                }
            } catch (MinesweeperException e) {
                if (minesweeper.gameWon()) {
                    System.out.println("You cleared all the mines! Congratulations!");
                } else {
                    System.out.println("Kablamo! Sorry, you landed on a mine! Better luck next time!");
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        minesweeper.uncover();
        System.out.println(minesweeper);
    }
}
