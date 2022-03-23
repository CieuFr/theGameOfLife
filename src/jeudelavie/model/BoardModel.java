package jeudelavie.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class BoardModel {

    private int vieMin, vieMax, mortMin, mortMax;
    private int size;
    private int zoomRatio;
    private int boardPixelSize;

    private int[][] board;

    IntegerProperty numberOfIterations;

    public BoardModel(int size) {
        this.size = size;
        this.zoomRatio = 1;
        this.board = new int[size][size];
        // TODO
        this.numberOfIterations = new SimpleIntegerProperty(0);
    }

    public int getSize() {
        return size;
    }

    //todo
    public int getState(int x, int y) {
        if (x < 0 || x >= size) {
            return 0;
        }

        if (y < 0 || y >= size) {
            return 0;
        }

        return this.board[x][y];
    }

    public int countAliveNeighbours(int x, int y) {
        int count = 0;

        count += getState(x - 1, y - 1);
        count += getState(x, y - 1);
        count += getState(x + 1, y - 1);

        count += getState(x - 1, y);
        count += getState(x + 1, y);

        count += getState(x - 1, y + 1);
        count += getState(x, y + 1);
        count += getState(x + 1, y + 1);

        return count;
    }

    // TODO
    public void increaseIterations() {
        this.numberOfIterations.set(numberOfIterations.get() + 1);
    }
    // TODO

    public void resetIterations() {
        this.numberOfIterations.set(0);
    }
    // TODO

    public IntegerProperty getNumberOfIterations() {
        return numberOfIterations;
    }


    public void setAlive(int x, int y) {
        this.board[x][y] = 1;
    }

    public void inverseState(int x, int y) {
        if (this.board[x][y] == 1) {
            this.board[x][y] = 0;
        } else {
            this.board[x][y] = 1;
        }
    }



    public void setDead(int x, int y) {
        this.board[x][y] = 0;
    }

    public int getVieMin() {
        return vieMin;
    }

    public void setVieMin(int vieMin) {
        this.vieMin = vieMin;
    }

    public int getVieMax() {
        return vieMax;
    }

    public void setVieMax(int vieMax) {
        this.vieMax = vieMax;
    }

    public int getMortMin() {
        return mortMin;
    }

    public void setMortMin(int mortMin) {
        this.mortMin = mortMin;
    }

    public int getMortMax() {
        return mortMax;
    }

    public void setMortMax(int mortMax) {
        this.mortMax = mortMax;
    }

    public int[][] getBoard() {
        return board;
    }

    public int getBoardSize() {
        return size;
    }

    public void setBoardPixelWidth(int boardPixelSize) {
        this.boardPixelSize = boardPixelSize;
    }

    public int getBoardPixelSize() {
        return boardPixelSize;
    }

    public int getZoomRatio() {
        return this.zoomRatio;
    }

    public void decrementZoomRatio() {
        if (zoomRatio > 1)
            this.zoomRatio--;
    }

    public void incrementZoomRatio() {
        if (zoomRatio < 8)
            this.zoomRatio++;
    }

    public boolean isAlive(int x, int y) {

        return this.board[x][y] == 1;
    }

    public boolean isDead(int x, int y) {
        return this.board[x][y] == 0;
    }

    public void setBoard(int[][] newBoard) {
        this.board = newBoard;
    }
}
