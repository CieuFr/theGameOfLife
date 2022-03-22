package jeudelavie.model;

public class BoardModel {

    private int vieMin, vieMax, mortMin, mortMax;

    private int size;
    private int[][] board;

    public BoardModel(int size) {
        this.size = size;
        this.board = new int[size][size];
    }

    public void setAlive(int x, int y) {
        this.board[x][y] = 1;
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
}
