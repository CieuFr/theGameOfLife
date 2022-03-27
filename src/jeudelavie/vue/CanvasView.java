package jeudelavie.vue;

import javafx.scene.canvas.Canvas;
import jeudelavie.model.CanvasModel;

public abstract class CanvasView extends Canvas {
    protected CanvasModel canvasModel;

    public CanvasView(CanvasModel canvasModel) {
        super(Math.max((5 * canvasModel.getSize()), 200), Math.max((5 * canvasModel.getSize()), 200));
        this.canvasModel = canvasModel;
    }
}