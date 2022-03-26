package jeudelavie.controleur;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import jeudelavie.miscellaneous.RandomGenerator;
import jeudelavie.model.BoardModel;
import jeudelavie.model.FrameModel;
import jeudelavie.vue.BoardView;

import java.util.Arrays;

public class BoardController {
    private BoardModel boardModel;
    private BoardView boardView;
    private BoardController figureController;

    //TODO PB AVEC LE RESIZE
    public void resizeFrame(int boardSize) {
        this.boardSize = boardSize;
        this.boardModel.setSize(boardSize);
        this.draw();
    }

    private int boardSize;

    public BoardController(int boardSize) {
        this.boardSize = boardSize;
        this.boardModel = new BoardModel(boardSize);
        this.boardView = new BoardView(this, this.boardModel);

        this.boardView.setOnScroll(scrollEvent -> {
            double deltaY = scrollEvent.getDeltaY();
            // TODO on zoom make the "selected" cell the center of the newly zoomed canvas
            if (deltaY < 0) {
                this.zoomOut();
            } else {
                this.zoomIn();
            }
        });

        this.boardView.setOnDragDetected(dragEvent -> {
            System.out.println("Drag detected");
            // TODO on drag move the canvas
        });

        this.boardView.setOnMouseClicked(clickEvent -> {

            System.out.println("Click detected");
            System.out.println(clickEvent);

            // TODO refactor
            int cellX = (int) Math.floor((clickEvent.getX() / (this.getBoardModel().getBoardPixelSize() * this.boardModel.getZoomRatio())) * this.boardModel.getBoardSize());
            int cellY = (int) Math.floor((clickEvent.getY() / (this.getBoardModel().getBoardPixelSize() * this.boardModel.getZoomRatio())) * this.boardModel.getBoardSize());
            if((clickEvent.getButton() == MouseButton.SECONDARY)&& (!boardModel.isPlaying())){
                if(clickEvent.isShiftDown() && (figureController != null)){
                    this.pastFigure(figureController.getBoardModel().getBoard(),figureController.getBoardModel().getBoardSize(),cellX,cellY);
                } else {
                    this.figureController.getBoardModel().setBoard(getSquareFromBoard(cellX,cellY,figureController.getBoardModel().getBoardSize()));
                    this.figureController.draw();
                }
            } else if((clickEvent.getButton() == MouseButton.PRIMARY)) {
                        this.inverseCellState(cellX, cellY);
            }
        });
    }

    public void setFigureController(BoardController figureController) {
        this.figureController = figureController;
    }

    // TODO
    public void zoomIn() {
        System.out.println("zoom in");
        this.boardModel.incrementZoomRatio();

        System.out.println(this.boardModel.getZoomRatio());

        draw();
    }

    // TODO
    public void zoomOut() {
        System.out.println("zoom out");
        this.boardModel.decrementZoomRatio();

        System.out.println(this.boardModel.getZoomRatio());

        draw();
    }


    // TODO
    public int[][] computeNextGeneration(FrameModel frameModel) {
        int[][] newBoard = new int[this.boardSize][this.boardSize];

        for (int x = 0; x < this.boardSize; x++) {
            for (int y = 0; y < this.boardSize; y++) {
                int aliveNeighbours = boardModel.countAliveNeighbours(x, y);
                if (boardModel.getState(x, y) == 1) {
                    if (aliveNeighbours >= frameModel.getSuffocationDeath()) {
                        newBoard[x][y] = 0;
                    } else if (aliveNeighbours <= frameModel.getLonelinessDeath()) {
                        newBoard[x][y] = 0;
                    } else {
                        newBoard[x][y] = 1;
                    }
                } else {
                    if (aliveNeighbours == frameModel.getAliveMin() && aliveNeighbours <= frameModel.getAliveMax()) {
                        newBoard[x][y] = 1;
                    } else {
                        newBoard[x][y] = 0;
                    }
                }

                /*int aliveCells = 0;
                for (int iNearCells = -1; iNearCells < 2; iNearCells++) {
                    for (int jNearCells = -1; jNearCells < 2; jNearCells++) {
                        aliveCells += ((i + iNearCells < 0) || (j + jNearCells < 0) || (i + iNearCells > boardModel.getSize() - 1) || (j + jNearCells > boardModel.getSize() - 1)) ? 0 : boardModel.getBoard()[i + iNearCells][j + jNearCells];
                    }
                }
                aliveCells -= boardModel.getBoard()[i][j];

                if ((boardModel.getBoard()[i][j] == 1) && (aliveCells >= boardModel.getMortMin()) && (aliveCells <= boardModel.getMortMax())) {
                    boardModel.setDead(i, j);
                } else if ((boardModel.getBoard()[i][j] == 0) && (aliveCells >= boardModel.getVieMin()) && (aliveCells <= boardModel.getMortMax())) {
                    boardModel.setAlive(i, j);
                }*/
            }
        }
        // TODO revert to directly modify board

        return newBoard;

    }

    public void computeAndSetNextGeneration(FrameModel frameModel) {
        int[][] newBoard = computeNextGeneration(frameModel);

        this.boardModel.setBoard(newBoard);

        System.out.println(boardModel.getNumberOfIterations().toString());
        boardModel.increaseIterations();
        draw();
    }

    public void pastFigure(int[][] figure, int figureSize, int x, int y) {
        for (int i = 0; i < figureSize; i++) {
            for (int j = 0; j < figureSize; j++) {
                if (!(((x + i) >= boardSize) || ((y + j) >= boardSize))) {
                    boardModel.getBoard()[x + i][y + j] = figure[i][j];
                }
            }
        }
        draw();
    }

    public int[][] getSquareFromBoard(int x, int y, int figureSize){
        int[][] sampleSquare = new int[figureSize][figureSize];
        for (int i = 0; i < figureSize; i++) {
            for (int j = 0; j < figureSize; j++) {
                if(!(((x+i)>=boardSize)|| ((y+j)>=boardSize))){
                    sampleSquare[i][j] = boardModel.getBoard()[x + i][y + j];
                } else {
                    sampleSquare[i][j] = 0;
                }
            }
        }
        return sampleSquare;
    }

    public void draw() {
        GraphicsContext graphicsContext = this.boardView.getGraphicsContext2D();
        Affine affine = new Affine();
        this.boardView.setWidth(this.boardModel.getBoardPixelSize() * this.boardModel.getZoomRatio());
        this.boardView.setHeight(this.boardModel.getBoardPixelSize() * this.boardModel.getZoomRatio());

        affine.appendScale(this.boardModel.getBoardPixelSize() / this.boardSize * this.boardModel.getZoomRatio(), this.boardModel.getBoardPixelSize() / this.boardSize * this.boardModel.getZoomRatio());

        graphicsContext.setTransform(affine);
        graphicsContext.setFill(Color.LIGHTGRAY);
        graphicsContext.fillRect(0, 0, this.boardSize, this.boardSize);
        graphicsContext.setFill(Color.BLACK);
        for (int x = 0; x < this.boardSize; x++) {
            for (int y = 0; y < this.boardSize; y++) {
                if (this.boardModel.getState(x, y) == 1) {
                    graphicsContext.fillRect(x, y, 1, 1);
                }
            }
        }
        graphicsContext.setStroke(Color.GRAY);
        graphicsContext.setLineWidth(0.05);
        for (int x = 0; x <= this.boardSize; x++) {
            graphicsContext.strokeLine(x, 0, x, boardSize);
        }

        for (int y = 0; y <= this.boardSize; y++) {
            graphicsContext.strokeLine(0, y, boardSize, y);
        }

    }

    public BoardModel getBoardModel() {
        return boardModel;
    }

    public BoardView getBoardView() {
        return boardView;
    }

    public void setAlive(int cellX, int cellY) {
        this.boardModel.setAlive(cellX, cellY);
        draw();
    }

    public void inverseCellState(int cellX, int cellY) {
        this.boardModel.inverseState(cellX, cellY);
        draw();
    }

    public void resetBoard() {
        this.boardModel.resetBoard();
        draw();
    }

    public boolean[][] toBoolean() {
        boolean[][] booleans = new boolean[this.boardSize][this.boardSize];
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                booleans[i][j] = this.boardModel.getState(i, j) == 1;
            }
        }
        return booleans;
    }

    // FONCTION POUR INITIALISER LE BOARD AVEC UN TABLEAU DE BOOLEENS
    public void initBoardFromBoolean(boolean[][] booleans, int size){
        this.boardSize = size;
        this.boardModel.setSize(size);
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                if(booleans[i][j]){
                    this.getBoardModel().setAlive(i,j);
                }
                this.getBoardModel().setDead(i,j);
            }
        }
    }


    public void randomizeBoard(int probability) {
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                if (probability > RandomGenerator.generator.nextInt(100)){
                    this.boardModel.setAlive(i, j);
                }else{
                    this.boardModel.setDead(i, j);
                }
            }
        }
        draw();
    }
}
