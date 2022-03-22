package jeudelavie.vue;

import javafx.scene.layout.GridPane;
import jeudelavie.controleur.BoardController;
import jeudelavie.model.BoardModel;
import jeudelavie.model.BoardModelOld;

public class BoardView extends GridPane {
    private BoardModel boardModel;
    private BoardModelOld boardModelOld;
    private BoardController boardController;

    public BoardView(BoardController boardController, BoardModelOld boardModelOld,BoardModel boardModel){
        super();
        this.boardController = boardController;
        this.boardModelOld = boardModelOld;
        this.boardModel = boardModel;
        this.setGridLinesVisible(true);
        this.setVgap(2);
        this.setHgap(2);
        for (int x = 0; x < this.boardModelOld.getBoardSize(); x++) {
            for (int y = 0; y < this.boardModelOld.getBoardSize(); y++) {
                this.add(this.boardModelOld.getCellAt(x, y), x, y);
            }
        }
    }


    public BoardModelOld getBoardModelOld() {
        return boardModelOld;
    }
    public BoardModel getBoardModel() {
        return boardModel;
    }

    public BoardController getBoardController() {
        return boardController;
    }
}
