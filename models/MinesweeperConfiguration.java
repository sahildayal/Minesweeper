package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import backtracker.Configuration;

public class MinesweeperConfiguration implements Configuration {
    private final Minesweeper game;
    private final ArrayList<Location> steps;

    public MinesweeperConfiguration(Minesweeper game) {
        this.game = game;
        this.steps = new ArrayList<>();
    }

    public MinesweeperConfiguration(Minesweeper game, ArrayList<Location> prevSteps) {
        this.game = game;
        this.steps = prevSteps;
    }

    @Override
    public Collection<Configuration> getSuccessors() {
        ArrayList<Configuration> games = new ArrayList<>();
        for (Location option : game.getPossibleSelections()) {
            Minesweeper newGame = new Minesweeper(game);
            MinesweeperConfiguration config = new MinesweeperConfiguration(newGame, new ArrayList<>(steps));
            try {
                newGame.makeSelection(option);
                config.steps.add(option);
                games.add(config);
            } catch (MinesweeperException err) {
                if (newGame.gameWon()) {
                    config.steps.add(option);
                    games.add(config);
                }
            }
        }
        return games;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public boolean isGoal() {
        return game.gameWon();
    }

    public List<Location> stepsTaken() {
        return steps;
    }
    
}
