package jeudelavie.vue;

import jeudelavie.model.BoardModel;

public class BoardView extends CanvasView {
    public BoardView(BoardModel boardModel) {
        super(boardModel);
        this.canvasModel.setBoardPixelWidth(Math.max((5 * boardModel.getSize()), 200));
    }
}
