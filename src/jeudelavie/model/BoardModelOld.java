package jeudelavie.model;

import jeudelavie.controleur.BoardController;
import jeudelavie.miscellaneous.Cell;

public class BoardModelOld {
    private BoardController boardController;

    private Cell[][] cellsMatrix;
    private Cell[][] cellsMatrix2;
    private int boardSize;
    private int cellSize;

    public BoardModelOld(BoardController boardController, int boardSize) {
        this.boardController = boardController;

        this.boardSize = boardSize;
        this.cellsMatrix = new Cell[boardSize][boardSize];
        for (int x = 0; x < this.boardSize; x++) {
            for (int y = 0; y < this.boardSize; y++) {
                this.cellsMatrix[x][y] = new Cell(10);
            }
        }
    }

    public int getCellSize() {
        return cellSize;
    }

    public Cell[][] getCellsMatrix() {
        return cellsMatrix;
    }

    public Cell getCellAt(int x, int y) {
        return this.cellsMatrix[x][y];
    }

    public void killCellAt(int x, int y) {
        this.cellsMatrix[x][y].kill();
    }

    public void setCellAliveAt(int x, int y) {
        this.cellsMatrix[x][y].setAlive();
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setCellSize(int i) {
        this.cellSize = i;
    }
}
