package jeudelavie.vue;

import javafx.scene.canvas.Canvas;
import jeudelavie.controleur.BoardController;
import jeudelavie.model.BoardModel;

public class BoardView extends Canvas {
    private BoardModel boardModel;
    private BoardController boardController;

    public BoardView(BoardController boardController, BoardModel boardModel) {
        super(Math.max((5 * boardModel.getBoardSize()), 200), Math.max((5 * boardModel.getBoardSize()), 200));
        this.boardController = boardController;
        this.boardModel = boardModel;
        this.boardModel.setBoardPixelWidth(Math.max((5 * boardModel.getBoardSize()), 200));

    }

    public BoardModel getBoardModel() {
        return boardModel;
    }

    public BoardController getBoardController() {
        return boardController;
    }
}
