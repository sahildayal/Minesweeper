# Minesweeper

Minesweeper is a classic single-player puzzle game where the objective is to clear a rectangular board containing hidden mines without accidentally triggering any of them. The game is won by revealing all the cells without mines.

This repository contains a Java implementation of the game 'Minesweeper' using the Swing graphical user interface (GUI). It provides a simple and intuitive user interface, allowing players to interact with the game easily.

## Game Rules

The game starts with a grid of covered cells, some of which contain hidden mines. The objective is to uncover all the cells without triggering the mines. To help the player know the location of the mines, the uncovered cells display the number of mines that are adjacent to it.

When a player uncovers a cell that contains a mine, the game ends immediately, and the player loses. However, if the player uncovers all the cells without mines, the game ends, and the player wins.

## Features

- Left-click and right-click actions: The player canperform left-click and right-click actions to interact with the game board.
- Left-click: Uncover a cell. If the cell contains a mine, the game ends and the player loses. If the cell does not contain a mine, it will display the number of adjacent mines.
- Right-click: Flag or unflag a cell. The player can use flags to mark cells that they suspect contain mines, helping them keep track of the possible mine locations.

## Getting Started

To run the Minesweeper game, you will need to have Java Development Kit (JDK) installed on your machine.

1. Clone the repository:

   ```
   git clone https://github.com/sahildayal/Minesweeper.git
   ```

2. Open the project in your favorite integrated development environment (IDE).

3. Build and run the project.

## Screenshots

Here are a few screenshots of the Minesweeper game:

![Minesweeper GUI](https://github.com/sahildayal/Minesweeper/blob/main/m1.png "Minesweeper GUI") 

![MinesweeperG](https://github.com/sahildayal/Minesweeper/blob/main/m2.png)

![MinesweeperGg](https://github.com/sahildayal/Minesweeper/blob/main/m_hint.png)

## Contributing

Contributions are welcome! If you have any ideas for improvements or find any bugs, please feel free to open an issue or submit a pull request.
