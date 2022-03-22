package jeudelavie.controleur;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import jeudelavie.vue.BoardView;

public class FrameController {

    @FXML
    private Pane boardPane;

    @FXML
    private ComboBox patternLoadingCombo;

    @FXML
    private ComboBox lonelinessDeathCombo;

    @FXML
    private ComboBox sufocationDeathCombo;

    @FXML
    private ComboBox minHealthCombo;


    @FXML
    private ComboBox maxHealthCombo;

    @FXML
    private TextField boardSizeTextField;

    @FXML
    private Button playPauseButton;

    @FXML
    protected void onPlayPauseButtonAction(){
        System.out.println("todo play pause");
        playPauseButton.setText("Pause");
    }

    @FXML
    private Button resetButton;

    @FXML
    protected void onResetButtonAction(){
        System.out.println("TODO reset ");
    }

    @FXML
    private Button nextGenerationButton;

    @FXML
    protected void onNextGenerationButtonAction(){
        System.out.println("TODO next generation ");
    }

    @FXML
    private Button randomizeButton;

    @FXML
    protected void onRandomizeButtonAction(){
        System.out.println("TODO randomize ");
    }

    @FXML
    private Button quitButton;

    @FXML
    protected void onQuitButtonAction(){
        System.exit(0);
    }

    @FXML
    private Button loadButton;

    @FXML
    protected void onLoadButtonAction(){
        System.out.println("todo");
    }



    public void initialize(){


    }


    private BoardView boardView;

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