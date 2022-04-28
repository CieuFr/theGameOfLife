package jeudelavie.model;

import java.util.Arrays;

public abstract class CanvasModel {
    private int boardPixelSize;
    protected int size;
    protected int[][] board;

    public CanvasModel(int size) {
        this.size = size;
        this.board = new int[size][size];
    }

    public void setSize(int size) {
        this.size = size;
        this.board = new int[size][size];
    }

    public int getSize() {
        return this.size;
    }

    public int getState(int x, int y) { // TODO tmts
        if (x < 0 || x >= this.size) {
            return 0;
        }
        if (y < 0 || y >= this.size) {
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

    public void setAlive(int x, int y) {
        this.board[x][y] = 1;
    }

    public void setDead(int x, int y) {
        this.board[x][y] = 0;
    }

    public void inverseState(int x, int y) {
        this.board[x][y] = this.board[x][y] == 1 ? 0 : 1;
    }

    public void resetBoard() {
        for (int[] row : this.board) {
            Arrays.fill(row, 0);
        }
    }

    public int[][] getBoard() {
        return this.board;
    }

    public void setBoardPixelWidth(int boardPixelSize) {
        this.boardPixelSize = boardPixelSize;
    }

    public int getBoardPixelSize() {
        return this.boardPixelSize;
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

    public int getZoomRatio(){
        return 1;
    }
}
