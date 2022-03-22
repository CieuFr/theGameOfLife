package jeudelavie.vue;

import javafx.scene.layout.GridPane;
import jeudelavie.controleur.BoardController;
import jeudelavie.model.BoardModelOld;

public class BoardView extends GridPane {
    private BoardModelOld boardModelOld;
    private BoardController boardController;

    public BoardView(BoardController boardController, BoardModelOld boardModelOld){
        super();
        this.boardController = boardController;
        this.boardModelOld = boardModelOld;
        this.setGridLinesVisible(true);
        this.setVgap(2);
        this.setHgap(2);
        for (int x = 0; x < this.boardModelOld.getBoardSize(); x++) {
            for (int y = 0; y < this.boardModelOld.getBoardSize(); y++) {
                this.add(this.boardModelOld.getCellAt(x, y), x, y);
            }
        }
    }


    public BoardModelOld getBoardModel() {
        return boardModelOld;
    }

    public BoardController getBoardController() {
        return boardController;
    }
}
