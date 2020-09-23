package ca.cmpt213.as3.Model;

/**
 * class for mouse to store and provide mouse's position (row, col)
 * Also, changes position when called to move direction towards up, down, left, or right
 */

public class Mouse {
    private int rowPosition;
    private int colPosition;

    public Mouse(int rowPosition, int colPosition) {
        this.rowPosition = rowPosition;
        this.colPosition = colPosition;
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public int getColPosition() {
        return colPosition;
    }

    public void moveRight() {
        colPosition++;
    }

    public void moveLeft() {
        colPosition--;
    }

    public void moveUp() {
        rowPosition--;
    }

    public void moveDown() {
        rowPosition++;
    }
}
