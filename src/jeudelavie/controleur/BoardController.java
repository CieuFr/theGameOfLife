package jeudelavie.controleur;

import jeudelavie.miscellaneous.Cell;
import jeudelavie.model.BoardModelOld;
import jeudelavie.vue.BoardView;

import java.time.Duration;
import java.time.Instant;

public class BoardController {
    private BoardModelOld boardModelOld;
    private BoardView boardView;

    public BoardController(int boardSize) {
        this.boardModelOld = new BoardModelOld(this, boardSize);
        this.boardView = new BoardView(this, this.boardModelOld);
    }

    public void zoomIn(){
        System.out.println("zoom in");
        System.out.println(this.boardModelOld.getCellSize());

        this.boardModelOld.setCellSize(this.boardModelOld.getCellSize()+1);
        resizeCells();
    }
    public void zoomOut(){
        System.out.println("zoom out");
        this.boardModelOld.setCellSize(this.boardModelOld.getCellSize()-1);
        System.out.println(this.boardModelOld.getCellSize());

        resizeCells();
    }

    private void resizeCells() {
        Instant start = Instant.now();
        int cellSize = this.boardModelOld.getCellSize();
        for (int x = 0; x < this.boardModelOld.getBoardSize(); x++) {
            for (int y = 0; y < this.boardModelOld.getBoardSize(); y++) {
                Cell cell = this.boardModelOld.getCellsMatrix()[x][y];
                cell.setStyle("-fx-background-color: #39ff00;");
                cell.setSize(cellSize);
                //cell.setStyle("-fx-background-color: #ffffff;");
            }
        }
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end));
    }

    private void computeNextGeneration(){
        for(int i = 0; i < boardModel.getBoardSize(); i++){
            for( int j = 0 ; j < boardModel.getBoardSize() ; j++){
                int aliveCells = 0;
                for(int iNearCells = -1; iNearCells < 2; iNearCells++){
                    for( int jNearCells = -1 ; jNearCells < 2 ; jNearCells++) {
                       aliveCells += boardModel.getCellsMatrix()[iNearCells][jNearCells] ;
                    }
                }
                aliveCells -=  boardModel.getCellsMatrix()[i][j];

                if(aliveCells == )


            }
        }


    }


    public BoardModel getBoardModel() {
        return boardModel;
    }

    public BoardView getBoardView() {
        return boardView;
    }
}
