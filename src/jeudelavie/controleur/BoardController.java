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

    public BoardController(int boardSize) {
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

            // TODO refactor will cause problems later
            int cellX = (int) Math.floor((clickEvent.getX() / (this.getBoardModel().getBoardPixelSize() * this.boardModel.getZoomRatio())) * this.boardModel.getBoardSize());
            int cellY = (int) Math.floor((clickEvent.getY() / (this.getBoardModel().getBoardPixelSize() * this.boardModel.getZoomRatio())) * this.boardModel.getBoardSize());

            System.out.println("cellX");
            System.out.println(cellX);
            System.out.println("cellY");
            System.out.println(cellY);


            if ((clickEvent.getButton() == MouseButton.SECONDARY) && (!boardModel.isPlaying())) {
                if (clickEvent.isShiftDown() && (figureController != null)) {
                    this.pastFigure(figureController.getBoardModel().getBoard(), figureController.getBoardModel().getBoardSize(), cellX, cellY);
                } else {
                    this.figureController.getBoardModel().setBoard(getSquareFromBoard(cellX, cellY, figureController.getBoardModel().getBoardSize()));
                    this.figureController.draw();
                }
            } else if ((clickEvent.getButton() == MouseButton.PRIMARY)) {
                this.inverseCellState(cellX, cellY);
            }
        });
    }

    public void setFigureController(BoardController figureController) {
        this.figureController = figureController;
    }

    public void zoomIn() {
        this.boardModel.incrementZoomRatio();
        draw();
    }

    public void zoomOut() {
        this.boardModel.decrementZoomRatio();
        draw();
    }


    // TODO
    public int[][] computeNextGeneration(FrameModel frameModel) {
        int boardSize = this.boardModel.getBoardSize();
        int[][] newBoard = new int[boardSize][boardSize];

        System.out.println("size");
        System.out.println(boardSize);

        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
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
            }
        }
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
        int boardSize = this.boardModel.getBoardSize();

        for (int i = 0; i < figureSize; i++) {
            for (int j = 0; j < figureSize; j++) {
                if (!(((x + i) >= boardSize) || ((y + j) >= boardSize))) {
                    boardModel.getBoard()[x + i][y + j] = figure[i][j];
                }
            }
        }
        draw();
    }

    public int[][] getSquareFromBoard(int x, int y, int figureSize) {
        int boardSize = this.boardModel.getBoardSize();
        int[][] sampleSquare = new int[figureSize][figureSize];
        for (int i = 0; i < figureSize; i++) {
            for (int j = 0; j < figureSize; j++) {
                if (!(((x + i) >= boardSize) || ((y + j) >= boardSize))) {
                    sampleSquare[i][j] = boardModel.getBoard()[x + i][y + j]; // TODO get cell at & remove get board
                } else {
                    sampleSquare[i][j] = 0;
                }
            }
        }
        return sampleSquare;
    }

    //TODO PB AVEC LE RESIZE
    public void resizeFrame(int boardSize) {
        this.boardModel.setSize(boardSize);
        this.draw();
    }

    public void draw() {
        int boardSize = this.boardModel.getBoardSize();

        GraphicsContext graphicsContext = this.boardView.getGraphicsContext2D();
        Affine affine = new Affine();
        this.boardModel.setBoardPixelWidth(Math.max((5 * boardModel.getBoardSize()), 200)); // HERE

        int new_size = this.boardModel.getBoardPixelSize() * this.boardModel.getZoomRatio();
        new_size = Math.min(new_size, 8192);
        this.boardView.setWidth(new_size);
        this.boardView.setHeight(new_size);

        System.out.println("new_size");
        System.out.println(new_size);

        int scale = this.boardModel.getBoardPixelSize() / boardSize * this.boardModel.getZoomRatio();
        affine.appendScale(scale, scale);


        graphicsContext.setTransform(affine);
        graphicsContext.setFill(Color.LIGHTGRAY);
        graphicsContext.fillRect(0, 0, boardSize, boardSize);
        graphicsContext.setFill(Color.BLACK);

        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                if (this.boardModel.getState(x, y) == 1) {
                    graphicsContext.fillRect(x, y, 1, 1);
                }
            }
        }

        graphicsContext.setStroke(Color.GRAY);
        graphicsContext.setLineWidth(0.05);
        for (int x = 0; x <= boardSize; x++) {
            graphicsContext.strokeLine(x, 0, x, boardSize);
        }

        for (int y = 0; y <= boardSize; y++) {
            graphicsContext.strokeLine(0, y, boardSize, y);
        }

    }

    public BoardModel getBoardModel() {
        return boardModel;
    }

    public BoardView getBoardView() {
        return boardView;
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
        int boardSize = this.boardModel.getBoardSize();
        boolean[][] booleans = new boolean[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                booleans[i][j] = this.boardModel.getState(i, j) == 1;
            }
        }
        return booleans;
    }

    // FONCTION POUR INITIALISER LE BOARD AVEC UN TABLEAU DE BOOLEENS
    public void initBoardFromBoolean(boolean[][] booleans, int size) {
        int boardSize = this.boardModel.getBoardSize();
        this.boardModel.setSize(size);
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (booleans[i][j]) {
                    this.getBoardModel().setAlive(i, j);
                }
                this.getBoardModel().setDead(i, j);
            }
        }
    }


    public void randomizeBoard(int probability) {
        int boardSize = this.boardModel.getBoardSize();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (probability > RandomGenerator.generator.nextInt(100)) {
                    this.boardModel.setAlive(i, j);
                } else {
                    this.boardModel.setDead(i, j);
                }
            }
        }
        draw();
    }
}
