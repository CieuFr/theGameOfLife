package jeudelavie.controleur;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import jeudelavie.vue.BoardView;

public class FrameController {
    @FXML
    private Label welcomeText;

    @FXML
    private Pane boardPane;

    private BoardView boardView;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void addBoardView(BoardView boardView) {
        this.boardView = boardView;
        this.boardPane.getChildren().add(boardView);

        this.boardPane.setOnScroll(scrollEvent -> {
            double deltaY = scrollEvent.getDeltaY();
            if (deltaY < 0) {
                this.boardView.getBoardController().zoomOut();
            } else {
                this.boardView.getBoardController().zoomIn();
            }
        });

        this.boardPane.setOnDragDetected(dragEvent -> {
            System.out.println(dragEvent);
        });
    }
}