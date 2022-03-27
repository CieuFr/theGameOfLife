package jeudelavie.vue;

import jeudelavie.model.FigureModel;

public class FigureView extends CanvasView {
    public FigureView(FigureModel figureModel) {
        super(figureModel);
        this.canvasModel.setBoardPixelWidth(Math.max((5 * figureModel.getSize()), 200));
    }
}