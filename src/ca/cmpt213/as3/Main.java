package ca.cmpt213.as3;

/**
 * class for running the game.
 * Game is ran until the user have won or lost the game.
 */

import ca.cmpt213.as3.UI.Text;
import ca.cmpt213.as3.UI.UserInput;
import ca.cmpt213.as3.Model.GameManager;
import ca.cmpt213.as3.Model.Maze;

public class Main {

    public static void main(String args[]){
        GameManager manager = new GameManager();
        Maze maze = new Maze();

        maze.mazeMaker();
        manager.setCheese();

        Text.displayWelcomeMessage();
        Text.displayInstruction();

        manager.revealMouse(1, 1);
        Text.displayWholeMaze();

        while (!(manager.winGame() || manager.mouseDeadCheck())) {
            UserInput.command();
            Text.displayWholeMaze();

        }
        if (manager.mouseDeadCheck()) {
            Text.gameOver();
        }
        else if (manager.winGame()) {
            Text.gameWin();
        }

    }
}
