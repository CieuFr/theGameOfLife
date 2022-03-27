package jeudelavie.controleur;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import jeudelavie.model.CanvasModel;
import jeudelavie.vue.CanvasView;

public abstract class CanvasController {
    protected CanvasModel canvasModel;
    protected CanvasView canvasView;

    public CanvasController(CanvasModel canvasModel) {
        this.canvasModel = canvasModel;
    }

    public void setCanvasView(CanvasView canvasView) {
        this.canvasView = canvasView;
    }

    public boolean[][] toBoolean() {
        int boardSize = this.canvasModel.getSize();
        boolean[][] booleans = new boolean[boardSize][boardSize];

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                booleans[i][j] = this.canvasModel.getState(i, j) == 1;
            }
        }
        return booleans;
    }

    public void resizeFrame(int boardSize) {
        this.canvasModel.setSize(boardSize);
        this.draw();
    }

    public void draw() {
        draw(true);
    }

    public void draw(boolean showGrid) {
        int boardSize = this.canvasModel.getSize();

        GraphicsContext graphicsContext = this.canvasView.getGraphicsContext2D();
        Affine affine = new Affine();
        this.canvasModel.setBoardPixelWidth(Math.max((5 * canvasModel.getSize()), 200));

        int newSize = this.canvasModel.getBoardPixelSize() * this.canvasModel.getZoomRatio();
        newSize = Math.min(newSize, 8192); // 8192 is the max size of a canvas 8192/1024 = 8
        this.canvasView.setWidth(newSize);
        this.canvasView.setHeight(newSize);


        int scale = this.canvasModel.getBoardPixelSize() / boardSize * this.canvasModel.getZoomRatio();
        affine.appendScale(scale, scale);

        graphicsContext.setTransform(affine);
        graphicsContext.setFill(Color.LIGHTGRAY);
        graphicsContext.fillRect(0, 0, boardSize, boardSize);
        graphicsContext.setFill(Color.BLACK);

        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                if (this.canvasModel.getState(x, y) == 1) {
                    graphicsContext.fillRect(x, y, 1, 1);
                }
            }
        }


        if (showGrid) {
            graphicsContext.setStroke(Color.GRAY);
            graphicsContext.setLineWidth(0.05);

            for (int x = 0; x <= boardSize; x++) {
                graphicsContext.strokeLine(x, 0, x, boardSize);
            }

            for (int y = 0; y <= boardSize; y++) {
                graphicsContext.strokeLine(0, y, boardSize, y);
            }
        }
    }

    public void resetCanvas() {
        this.canvasModel.resetBoard();
    }

}
