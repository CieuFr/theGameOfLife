package jeudelavie.controleur;

import jeudelavie.miscellaneous.Cell;
import jeudelavie.model.BoardModel;
import jeudelavie.vue.BoardView;

import java.time.Duration;
import java.time.Instant;

public class BoardController {
    private BoardModel boardModel;
    private BoardView boardView;

    public BoardController(int boardSize) {
        this.boardModel = new BoardModel(this, boardSize);
        this.boardView = new BoardView(this, this.boardModel);
    }

    public void zoomIn(){
        System.out.println("zoom in");
        System.out.println(this.boardModel.getCellSize());

        this.boardModel.setCellSize(this.boardModel.getCellSize()+1);
        resizeCells();
    }
    public void zoomOut(){
        System.out.println("zoom out");
        this.boardModel.setCellSize(this.boardModel.getCellSize()-1);
        System.out.println(this.boardModel.getCellSize());

        resizeCells();
    }

    private void resizeCells() {
        Instant start = Instant.now();
        int cellSize = this.boardModel.getCellSize();
        for (int x = 0; x < this.boardModel.getBoardSize(); x++) {
            for (int y = 0; y < this.boardModel.getBoardSize(); y++) {
                Cell cell = this.boardModel.getCellsMatrix()[x][y];
                cell.setStyle("-fx-background-color: #39ff00;");
                cell.setSize(cellSize);
                //cell.setStyle("-fx-background-color: #ffffff;");
            }
        }
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end));
    }

    public BoardModel getBoardModel() {
        return boardModel;
    }

    public BoardView getBoardView() {
        return boardView;
    }
}
