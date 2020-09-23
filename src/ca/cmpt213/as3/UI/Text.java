package ca.cmpt213.as3.UI;

import ca.cmpt213.as3.Model.GameManager;
import ca.cmpt213.as3.Model.Maze;

import java.sql.SQLOutput;

/**
 * class for storing all the UI text messages and displays maze.
 */

public class Text {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 15;

    private static GameManager manager = new GameManager();

    public static void displayWelcomeMessage() {
        System.out.println("----------------------------------------");
        System.out.println("Welcome to Cat and Mouse Maze Adventure!");
        System.out.println("by Jieung Park, Jiwon Jun");
        System.out.println("----------------------------------------");
    }

    public static void displayInstruction(){
        System.out.println("DIRECTIONS:");
        System.out.println("\tFind 5 cheese before a cat eats you!");
        System.out.println("LEGEND:");
        System.out.println("\t#: Wall\n" +
                "\t@: You (a mouse)\n" +
                "\t!: Cat\n" +
                "\t$: Cheese\n" +
                "\t.: Unexplored space");
        System.out.println("MOVES:");
        System.out.println("\tUse W (up), A (left), S (down) and D (right) to move.\n" +
                "\t(You must press enter after each move).");
        System.out.println("Cheat Commands:");
        System.out.println("\tEnter M to reveal the entire map.\n" +
                "\tEnter C to change the required cheese amount to 1.");
    }

    public static void displayWholeMaze(){
        System.out.println("Maze:");
        Maze maze = new Maze();

        //manager.revealAroundMouse(1, 1);

        //maze.mazeMaker();

        //String[][] maze = Maze.getMaze();
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++){
                if (maze.getVisited()[i][j]) {
                    if (maze.getMaze()[i][j].equals("wall")) {
                        System.out.print("#");
                    } else if (maze.getMaze()[i][j].equals("mouse")) {
                        System.out.print("@");
                    } else if (maze.getMaze()[i][j].equals("cat")) {
                        System.out.print("!");
                    } else if (maze.getMaze()[i][j].equals("cheese")) {
                        System.out.print("$");
                    } else if (maze.getMaze()[i][j].equals("deadSpot")) {
                        System.out.print("X");
                    } else {
                        System.out.print(" ");
                    }
                } else if (maze.getMaze()[i][j].equals("mouse")) {
                    System.out.print("@");
                } else if (maze.getMaze()[i][j].equals("cat")) {
                    System.out.print("!");
                } else if (maze.getMaze()[i][j].equals("cheese")) {
                    System.out.print("$");
                } else if (maze.getMaze()[i][j].equals("deadSpot")) {
                    System.out.print("X");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println("");
        }
        cheeseCollected();

    }

    public static void errorMessage(String error) {
        if (error.equals("invalidInput")) {
            System.out.println("Invalid move, Please enter just A (left), S (down), D (down), W (up).");
        }
        else if (error.equals("moveThroughWall")) {
            System.out.println("Invalid move: you cannot move through walls!");
        }
    }

    public static void cheeseCollected() {
        System.out.println("Cheese collected: " + manager.getNumCheeseCollected() + " of " + manager.getNumCheese());

    }

    public static void gameOver() {
        System.out.println("GAME OVER. You have been eaten!");
    }

    public static void gameWin() {
        System.out.println("Congratulations! You won!");
    }
}
