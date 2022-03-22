package jeudelavie.vue;

import javafx.scene.layout.GridPane;
import jeudelavie.controleur.BoardController;
import jeudelavie.model.BoardModel;

public class BoardView extends GridPane {
    private BoardModel boardModel;
    private BoardController boardController;

    public BoardView(BoardController boardController, BoardModel boardModel){
        super();
        this.boardController = boardController;
        this.boardModel = boardModel;
        this.setGridLinesVisible(true);
        this.setVgap(2);
        this.setHgap(2);
        for (int x = 0; x < this.boardModel.getBoardSize(); x++) {
            for (int y = 0; y < this.boardModel.getBoardSize(); y++) {
                this.add(this.boardModel.getCellAt(x, y), x, y);
            }
        }
    }


    public BoardModel getBoardModel() {
        return boardModel;
    }

    public BoardController getBoardController() {
        return boardController;
    }
}
