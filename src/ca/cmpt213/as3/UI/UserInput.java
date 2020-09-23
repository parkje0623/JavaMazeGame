package ca.cmpt213.as3.UI;

import ca.cmpt213.as3.Model.GameManager;
import ca.cmpt213.as3.Model.Maze;
import java.util.Scanner;

/**
 * class for receiving the user input.
 * if non valid input, errors are handled.
 */

public class UserInput {
    final private static String[] VALID_COMMAND = {"w", "a", "s", "d", "c", "m", "?"};
    private static GameManager gameManager = new GameManager();
    private static Maze maze = new Maze();

    public static String command() {
        boolean validInput = false;
        boolean success = false;
        String input = "";
        while (validInput == false) {
            System.out.print("Enter your move [WASD?]: ");
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();
            input = input.toLowerCase();
            for (int i = 0; i < VALID_COMMAND.length; i++) {
                if (input.equals(VALID_COMMAND[i])) {
                    validInput = true;
                }
            }
            if (validInput == false) {
                Text.errorMessage("invalidInput");
            }
        }
        switch (input) {
            case "w":
                success = gameManager.command("w"); // will return true if mouse successfully moved
                if (!success){
                    Text.errorMessage("moveThroughWall");
                    command();
                }
                break;
            case "a":
                success = gameManager.command("a");
                if (!success){
                    Text.errorMessage("moveThroughWall");
                    command();
                }
                break;
            case "s":
                success = gameManager.command("s");
                if (!success){
                    Text.errorMessage("moveThroughWall");
                    command();
                }
                break;
            case "d":
                success = gameManager.command("d");
                if (!success){
                    Text.errorMessage("moveThroughWall");
                    command();
                }
                break;
            case "c":
                success = gameManager.command("c");
                break;
            case "m":
                success = gameManager.command("m");
                break;
            case "?":
                Text.displayInstruction();
                command();
                break;
        }
        return input;
    }
}
