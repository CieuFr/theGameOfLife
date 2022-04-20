package jeudelavie.controleur;

import javafx.scene.input.MouseButton;
import jeudelavie.model.FigureModel;
import jeudelavie.vue.FigureView;

public class FigureController extends CanvasController {

    public FigureController(int boardSize) {
        super(new FigureModel(boardSize));
        this.setCanvasView(new FigureView((FigureModel) this.canvasModel));

        this.canvasView.setOnMouseClicked(clickEvent -> {
            int cellX = (int) Math.floor((clickEvent.getX() / (this.canvasModel.getBoardPixelSize()) * this.canvasModel.getSize()));
            int cellY = (int) Math.floor((clickEvent.getY() / (this.canvasModel.getBoardPixelSize()) * this.canvasModel.getSize()));

            if ((clickEvent.getButton() == MouseButton.PRIMARY)) {
                this.inverseCellState(cellX, cellY);
            }
        });
    }

    public FigureModel getFigureModel() {
        return (FigureModel) this.canvasModel;
    }

    public FigureView getFigureView() {
        return (FigureView) this.canvasView;
    }

    public void inverseCellState(int cellX, int cellY) {
        this.canvasModel.inverseState(cellX, cellY);
        this.draw();
    }

    @Override
    public void resetCanvas() {
        super.resetCanvas();
        this.draw();
    }

}
