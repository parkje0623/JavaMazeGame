package ca.cmpt213.as3.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * class for handling the general game play.
 * Cheese is randomly generated and cats are randomly moved.
 * Mouse moves depends on the user input
 * Handles cheat code: show the entire map / change the number of cheese to 1
 * Checks if the mouse have collected the cheese (win the game) or is eaten by the cat (lose the game)
 */

public class GameManager {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 15;

    private Mouse mouse;
    private Cheese cheese;
    private ArrayList<Cat> catList = new ArrayList<>();
    private Maze maze = new Maze();

    private static int numCheeseCollected = 0;
    private static int numCheese = 5;
    private boolean gotCheese = false;
    private static boolean deadMouse = false;
    private static boolean winGame = false;
    private String[] previousMove = {"", "", ""};

    public GameManager(){
        makeMouse();
        makeCat();
    }

    public void setCheese(){
        makeCheese();
    }

    public boolean command(String input){
        boolean success = true;
        if (input.equals("c")) {
            changeNumCheese();
        } else if (input.equals("m")){
            showMap();
        } else{
            if (moveMouse(input)) {
                success = true;
                revealMouse(mouse.getRowPosition(), mouse.getColPosition());
                if (!deadMouse) {
                    moveCat();
                }
                if (deadMouse) {
                    maze.setMaze(mouse.getRowPosition(), mouse.getColPosition(), "deadSpot");
                }
                mouseDead();
                mouseDeadCheck();
                eatCheese();
                winGame();
            } else {
                success = false;
            }
        }
        return success;
    }

    public void makeMouse() {
        mouse = new Mouse (1,1);
    }

    public void makeCat(){
        catList.add(new Cat(1, 18));
        catList.add(new Cat(13, 1));
        catList.add(new Cat(13, 18));
    }

    public void makeCheese(){
        Random random = new Random();
        int randomHeight = random.nextInt(HEIGHT - 3) + 2;
        int randomWidth = random.nextInt(WIDTH - 3) + 2;

        if (maze.getMaze()[randomHeight][randomWidth].equals("wall")){
            makeCheese();
        }
        else{
            cheese = new Cheese(randomHeight, randomWidth);
            maze.setMaze(randomHeight, randomWidth, "cheese");
        }
    }

    private void changeNumCheese(){
        numCheese = 1;
    }

    public static int getNumCheeseCollected() {
        return numCheeseCollected;
    }

    public static int getNumCheese() {
        return numCheese;
    }

    public void showMap(){
        for (int startHeight = 1; startHeight < HEIGHT; startHeight++){
            for (int startWidth = 1; startWidth < WIDTH; startWidth++){
                maze.setVisited(startHeight, startWidth);
            }
        }
    }

    public void eatCheese(){
        if (gotCheese) {
            numCheeseCollected++;
            if (!winGame()) {
                maze.mazeMaker();
                makeCheese();
                mouse = new Mouse(1, 1);
                catList.removeAll(catList);
                makeCat();
                revealMouse(mouse.getRowPosition(), mouse.getColPosition());
                gotCheese = false;
            }
        }
    }

    public boolean mouseDead(){
        if (maze.getMaze()[mouse.getRowPosition()][mouse.getColPosition()].equals("cat")){
            maze.setMaze(mouse.getRowPosition(), mouse.getColPosition(),"deadSpot");
            deadMouse = true;
            showMap();
        }
        return deadMouse;
    }

    public static boolean mouseDeadCheck() {
        return deadMouse;
    }

    public void mouseMoveToCat (int height, int width){
        if (maze.getMaze()[height][width].equals("cat")){
            maze.setMaze(height, width,"deadSpot"); // then cat moves
            deadMouse = true;
            showMap();
        }
    }

    public boolean winGame(){
        if (numCheeseCollected == numCheese){
            winGame = true;
            showMap();
        }
        return winGame;
    }

    public void setGotCheese (int height, int width){
        if (maze.getMaze()[height][width].equals("cheese")) {
            gotCheese = true;
        }
    }

    public void newMousePosition (int height, int width){
        setGotCheese(height, width);
        mouseMoveToCat(height, width);
        maze.setMaze(mouse.getRowPosition(), mouse.getColPosition(), "empty");
        maze.setMaze(height, width, "mouse");
    }

    public boolean moveMouse(String direction){
        boolean success = true;
        switch (direction) {
            case "w":
                //move up
                if (maze.getMaze()[mouse.getRowPosition() - 1][mouse.getColPosition()].equals("wall")) { // if wall
                    success = false; // fail
                } else {
                    newMousePosition(mouse.getRowPosition() - 1, mouse.getColPosition());
                    mouse.moveUp();
                    success = true; // success
                }
                break;
            case "a":
                // move left
                if (maze.getMaze()[mouse.getRowPosition()][mouse.getColPosition() - 1].equals("wall")) { // if wall
                    success = false;
                } else {
                    newMousePosition(mouse.getRowPosition(), mouse.getColPosition() - 1);
                    mouse.moveLeft();
                    success = true;
                }
                break;
            case "s":
                //move down
                if (maze.getMaze()[mouse.getRowPosition() + 1][mouse.getColPosition()].equals("wall")) { // if wall
                    success = false;
                } else {
                    newMousePosition(mouse.getRowPosition() + 1, mouse.getColPosition());
                    mouse.moveDown();
                    success = true;
                }
                break;
            case "d":
                //move right
                if (maze.getMaze()[mouse.getRowPosition()][mouse.getColPosition() + 1].equals("wall")) { // if wall
                    success = false;
                } else {
                    newMousePosition(mouse.getRowPosition(), mouse.getColPosition() + 1);
                    mouse.moveRight();
                    success = true;
                }
                break;
        }
        return success;
    }

    public void revealMouse(int mouseHeight, int mouseWidth){
        int mouseRight = 1;
        int mouseDown = 1;
        for (int mouseLeft = -1; mouseLeft <= mouseRight; mouseLeft++){
            for (int mouseUp = -1; mouseUp <= mouseDown; mouseUp++){
                if (maze.getMaze()[mouseHeight + mouseUp][mouseWidth + mouseLeft].equals("wall")) {
                    maze.setVisited(mouseHeight + mouseUp, mouseWidth + mouseLeft);
                } else if (maze.getMaze()[mouseHeight + mouseUp][mouseWidth + mouseLeft].equals("empty")){
                    maze.setVisited(mouseHeight + mouseUp, mouseWidth + mouseLeft);
                }
            }
        }
        maze.setVisited(mouseHeight, mouseWidth);
    }

    public void catEatCheese (int catIndex){
        if (maze.getMaze()[catList.get(catIndex).getRowPosition()][catList.get(catIndex).getColPosition()].equals("cheese")) {
            maze.setMaze(catList.get(catIndex).getRowPosition(), catList.get(catIndex).getColPosition(), "cheese");
        } else {
            maze.setMaze(catList.get(catIndex).getRowPosition(), catList.get(catIndex).getColPosition(), "empty");
        }
    }

    public void newCatPosition (int height, int width){
        if (!maze.getMaze()[height][width].equals("cheese")) {
            maze.setMaze(height, width, "cat");
        }
    }

    public void moveCat(){
        RandomDirection direction = new RandomDirection();
        direction.shuffleDirection();
        List<String> randomDirection = direction.getRandomDirection();

        for (int i = 0; i < catList.size(); i++){
            innerloop:
            for (int j = 0; j < randomDirection.size(); j++){
                switch (randomDirection.get(j)){
                    case "up":
                        if (previousMove[i].equals("down")) {
                            if (!(maze.getMaze()[catList.get(i).getRowPosition()][catList.get(i).getColPosition() - 1].equals("wall")
                                    && maze.getMaze()[catList.get(i).getRowPosition()][catList.get(i).getColPosition() + 1].equals("wall")
                                    && maze.getMaze()[catList.get(i).getRowPosition() + 1][catList.get(i).getColPosition()].equals("wall"))) {
                                break;
                            }
                        }
                        if (!maze.getMaze()[catList.get(i).getRowPosition() - 1][catList.get(i).getColPosition()].equals("wall")) {
                            catEatCheese(i);
                            newCatPosition(catList.get(i).getRowPosition() - 1, catList.get(i).getColPosition());
                            catList.get(i).moveUp();
                            previousMove[i] = "up";
                            break innerloop;
                        }
                        break;
                    case "down":
                        if (previousMove[i].equals("up")) {
                            if (!(maze.getMaze()[catList.get(i).getRowPosition()][catList.get(i).getColPosition() - 1].equals("wall")
                                    && maze.getMaze()[catList.get(i).getRowPosition()][catList.get(i).getColPosition() + 1].equals("wall")
                                    && maze.getMaze()[catList.get(i).getRowPosition() - 1][catList.get(i).getColPosition()].equals("wall"))) {
                                break;
                            }
                        }
                        if (!maze.getMaze()[catList.get(i).getRowPosition() + 1][catList.get(i).getColPosition()].equals("wall")) {
                            catEatCheese(i);
                            newCatPosition(catList.get(i).getRowPosition() + 1, catList.get(i).getColPosition());
                            catList.get(i).moveDown();
                            previousMove[i] = "down";
                            break innerloop;
                        }
                        break;
                    case "left":
                        if (previousMove[i].equals("right")) {
                            if (!(maze.getMaze()[catList.get(i).getRowPosition() - 1][catList.get(i).getColPosition()].equals("wall")
                                    && maze.getMaze()[catList.get(i).getRowPosition()][catList.get(i).getColPosition() + 1].equals("wall")
                                    && maze.getMaze()[catList.get(i).getRowPosition() + 1][catList.get(i).getColPosition()].equals("wall"))) {
                                break;
                            }
                        }
                        if (!maze.getMaze()[catList.get(i).getRowPosition()][catList.get(i).getColPosition() - 1].equals("wall")) {
                            catEatCheese(i);
                            newCatPosition(catList.get(i).getRowPosition(), catList.get(i).getColPosition() - 1);
                            catList.get(i).moveLeft();
                            previousMove[i] = "left";
                            break innerloop;
                        }
                        break;
                    case "right":
                        if (previousMove[i].equals("left")) {
                            if (!(maze.getMaze()[catList.get(i).getRowPosition() - 1][catList.get(i).getColPosition()].equals("wall")
                                    && maze.getMaze()[catList.get(i).getRowPosition()][catList.get(i).getColPosition() - 1].equals("wall")
                                    && maze.getMaze()[catList.get(i).getRowPosition() + 1][catList.get(i).getColPosition()].equals("wall"))) {
                                break;
                            }
                        }
                        if (!maze.getMaze()[catList.get(i).getRowPosition()][catList.get(i).getColPosition() + 1].equals("wall")) {
                            catEatCheese(i);
                            newCatPosition(catList.get(i).getRowPosition(), catList.get(i).getColPosition() + 1);
                            catList.get(i).moveRight();
                            previousMove[i] = "right";
                            break innerloop;
                        }
                        break;
                }
            }
        }
    }
}
