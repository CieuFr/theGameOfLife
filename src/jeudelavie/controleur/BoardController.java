package jeudelavie.controleur;

import javafx.scene.input.MouseButton;
import jeudelavie.miscellaneous.RandomGenerator;
import jeudelavie.model.BoardModel;
import jeudelavie.model.FrameModel;
import jeudelavie.vue.BoardView;

public class BoardController extends CanvasController {
    private FigureController figureController;

    public BoardController(int boardSize) {
        super(new BoardModel(boardSize));
        this.setCanvasView(new BoardView((BoardModel) this.canvasModel));

        this.canvasView.setOnScroll(scrollEvent -> {
            double deltaY = scrollEvent.getDeltaY();
            // TODO on zoom make the "selected" cell the center of the newly zoomed canvas
            if (deltaY < 0) {
                this.zoomOut();
            } else {
                this.zoomIn();
            }
        });

        this.canvasView.setOnDragDetected(dragEvent -> {
            System.out.println("Drag detected");
            // TODO on drag move the canvas
        });

        this.canvasView.setOnMouseClicked(clickEvent -> {
            // TODO refactor will cause problems later | it does n°6 |
            int cellX = (int) Math.floor((clickEvent.getX() / (this.canvasModel.getBoardPixelSize() * ((BoardModel) this.canvasModel).getZoomRatio())) * this.canvasModel.getSize());
            int cellY = (int) Math.floor((clickEvent.getY() / (this.canvasModel.getBoardPixelSize() * ((BoardModel) this.canvasModel).getZoomRatio())) * this.canvasModel.getSize());

            if ((clickEvent.getButton() == MouseButton.SECONDARY) && (!((BoardModel) this.canvasModel).isPlaying())) {
                if (clickEvent.isShiftDown() && (this.figureController != null)) {
                    this.pastFigure(this.figureController.getFigureModel().getBoard(), this.figureController.getFigureModel().getSize(), cellX, cellY);
                } else {
                    this.figureController.getFigureModel().setBoard(getSquareFromBoard(cellX, cellY, this.figureController.getFigureModel().getSize())); // TODO change acces to model !important
                    this.figureController.draw();
                }
            } else if ((clickEvent.getButton() == MouseButton.PRIMARY)) {
                this.canvasModel.inverseState(cellX, cellY);
            }
            this.draw();
        });
    }

    public BoardModel getBoardModel() {
        return (BoardModel) this.canvasModel;
    }

    public void setFigureController(FigureController figureController) {
        this.figureController = figureController;
    }

    public void zoomIn() {
        ((BoardModel) this.canvasModel).incrementZoomRatio();
        draw();
    }

    public void zoomOut() {
        ((BoardModel) this.canvasModel).decrementZoomRatio();
        draw();
    }

    @Override
    public void draw(){
        draw(!((BoardModel) this.canvasModel).isPlaying());
    }

    public int[][] computeNextGeneration(FrameModel frameModel) {
        int boardSize = this.canvasModel.getSize();
        int[][] newBoard = new int[boardSize][boardSize];

        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                int aliveNeighbours = canvasModel.countAliveNeighbours(x, y);
                if (canvasModel.getState(x, y) == 1) {
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
        //((BoardModel) canvasModel).increaseIterations();
        return newBoard;
    }

    public void computeAndSetNextGeneration(FrameModel frameModel) {
        int[][] newBoard = computeNextGeneration(frameModel);
        this.canvasModel.setBoard(newBoard);
        draw();
    }

    public void pastFigure(int[][] figure, int figureSize, int x, int y) {
        int boardSize = this.canvasModel.getSize();

        for (int i = 0; i < figureSize; i++) {
            for (int j = 0; j < figureSize; j++) {
                if (!(((x + i) >= boardSize) || ((y + j) >= boardSize))) {
                    canvasModel.getBoard()[x + i][y + j] = figure[i][j]; // TODO
                }
            }
        }
        draw();
    }

    public int[][] getSquareFromBoard(int x, int y, int figureSize) {
        int boardSize = this.canvasModel.getSize();
        int[][] sampleSquare = new int[figureSize][figureSize];

        for (int i = 0; i < figureSize; i++) {
            for (int j = 0; j < figureSize; j++) {
                if (!(((x + i) >= boardSize) || ((y + j) >= boardSize))) {
                    sampleSquare[i][j] = canvasModel.getBoard()[x + i][y + j]; // TODO get cell at & remove get board
                } else {
                    sampleSquare[i][j] = 0;
                }
            }
        }
        return sampleSquare;
    }

    public BoardView getBoardView() {
        return (BoardView) canvasView;
    }

    @Override
    public void resetCanvas() {
        super.resetCanvas();
        ((BoardModel) this.canvasModel).resetIterations();
        this.draw();
    }

    public void randomizeBoard(int probability) {
        int boardSize = this.canvasModel.getSize();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (probability > RandomGenerator.generator.nextInt(100)) {
                    this.canvasModel.setAlive(i, j);
                } else {
                    this.canvasModel.setDead(i, j);
                }
            }
        }
        draw();
    }
}
