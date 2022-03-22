package jeudelavie.controleur;

import jeudelavie.miscellaneous.Cell;
import jeudelavie.model.BoardModel;
import jeudelavie.model.BoardModelOld;
import jeudelavie.vue.BoardView;

import java.time.Duration;
import java.time.Instant;

public class BoardController {
    private BoardModelOld boardModelOld;
    private BoardModel boardModel;
    private boolean isPaused;

    private BoardView boardView;

    public BoardController(int boardSize) {
        this.boardModelOld = new BoardModelOld(this, boardSize);
        this.boardModel = new BoardModel(boardSize);
        this.boardView = new BoardView(this, this.boardModelOld,this.boardModel);
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


    public void computeNextGeneration(){
        for(int i = 0; i < boardModel.getBoardSize(); i++){
            for( int j = 0 ; j < boardModel.getBoardSize() ; j++){
                int aliveCells = 0;
                for(int iNearCells = -1; iNearCells < 2; iNearCells++){
                    for( int jNearCells = -1 ; jNearCells < 2 ; jNearCells++) {
                       aliveCells += ((i+iNearCells < 0)||(j+jNearCells < 0) || (i+iNearCells > boardModel.getSize()-1) || (j+jNearCells > boardModel.getSize()-1) ) ? 0 :boardModel.getBoard()[i+iNearCells][j+jNearCells] ;
                    }
                }
                aliveCells -=  boardModel.getBoard()[i][j];

                if((boardModel.getBoard()[i][j] == 1) && (aliveCells >= boardModel.getMortMin()) && (aliveCells <= boardModel.getMortMax()))
                {
                    boardModel.setDead(i,j);
                }
                else if ((boardModel.getBoard()[i][j] == 0) && (aliveCells >= boardModel.getVieMin()) && (aliveCells <= boardModel.getMortMax()))
                {
                    boardModel.setAlive(i,j);
                }
            }
        }
        System.out.println("fini it");
        System.out.println(boardModel.getNumberOfIterations().toString());
        boardModel.increaseIterations();
    }

    public void pastFigure(int[][] figure, int figureSize, int x, int y ) {
        for (int i = 0; i < figureSize; i++) {
            for (int j = 0; j < figureSize; j++) {
                boardModel.getBoard()[x + i][y + j] = figure[i][j];
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
