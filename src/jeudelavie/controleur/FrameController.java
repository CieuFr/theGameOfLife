package jeudelavie.controleur;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import jeudelavie.model.BoardModel;
import jeudelavie.model.FrameModel;
import jeudelavie.vue.BoardView;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class FrameController implements Initializable {

    @FXML
    private Pane boardPane;

    @FXML
    private Pane figurePane;

    @FXML
    private ComboBox<String> patternLoadingCombo;

    @FXML
    private ComboBox<String> lonelinessDeathCombo;

    @FXML
    private ComboBox<String> suffocationDeathCombo;

    @FXML
    private ComboBox<String> minHealthCombo;

    @FXML
    private ComboBox<String> maxHealthCombo;

    @FXML
    private TextField boardSizeTextField;

    @FXML
    private Button playPauseButton;

    @FXML
    private Button resetButton;

    @FXML
    private Button nextGenerationButton;

    @FXML
    private Button randomizeButton;

    @FXML
    private Button quitButton;

    @FXML
    private Button loadButton;

    @FXML
    private Label iterationsLabel;

    @FXML
    protected void onPlayPauseButtonAction() {

        if (this.frameModel.isPlaying()) {
            System.out.println("Pause the game");
            this.frameModel.setPlaying(false);
            playPauseButton.setText("Play");
        } else {
            System.out.println("Playing the game");
            this.frameModel.setPlaying(true);
            playPauseButton.setText("Pause");
        }
    }

    @FXML
    protected void onResetButtonAction() {
        //boardView.getBoardModel().resetIterations();

        System.out.println("TODO reset ");
    }

    @FXML
    protected void onNextGenerationButtonAction() {
        boardController.computeAndSetNextGeneration(this.frameModel);
    }

    @FXML
    protected void onRandomizeButtonAction() {
        System.out.println("TODO randomize ");
    }

    @FXML
    protected void onQuitButtonAction() {
        System.exit(0);
    }

    @FXML
    protected void onLoadButtonAction() {
        System.out.println("todo");
    }

    private FrameModel frameModel;
    private BoardController boardController;

    public FrameController(FrameModel frameModel) {
        this.frameModel = frameModel;
    }

    public FrameModel getFrameModel() {
        return this.frameModel;
    }

    public void addBoardController(BoardController boardController) {
        //  TODO !important change from board controller to directly board view cleaner
        this.boardController = boardController;
        this.boardPane.getChildren().add(this.boardController.getBoardView());
    }

    public void bindNumberOfIterations(){
        iterationsLabel.textProperty().bind(this.boardController.getBoardModel().getNumberOfIterations().asString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.frameModel.setLonelinessDeath(Integer.parseInt(lonelinessDeathCombo.getSelectionModel().getSelectedItem()));
        this.frameModel.setSuffocationDeath(Integer.parseInt(suffocationDeathCombo.getSelectionModel().getSelectedItem()));
        this.frameModel.setAliveMin(Integer.parseInt(minHealthCombo.getSelectionModel().getSelectedItem()));
        this.frameModel.setAliveMax(Integer.parseInt(maxHealthCombo.getSelectionModel().getSelectedItem()));

        AtomicBoolean lonelinessChanging = new AtomicBoolean(false);
        AtomicBoolean suffocationChanging = new AtomicBoolean(false);
        AtomicBoolean minHealthChanging = new AtomicBoolean(false);
        AtomicBoolean maxHealthChanging = new AtomicBoolean(false);

        lonelinessDeathCombo.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (!lonelinessChanging.get()) {
                lonelinessChanging.set(true);
                if ((Integer.parseInt(newValue) > this.frameModel.getSuffocationDeath())) {
                    alertGenerationError("Loneliness can't be above or equal to suffocation");
                    lonelinessDeathCombo.getSelectionModel().clearSelection();
                    lonelinessDeathCombo.getSelectionModel().select(oldValue);
                } else {
                    ButtonType button = alertGenerationConfirmation();
                    if (button == ButtonType.OK) {
                        this.frameModel.setLonelinessDeath(Integer.parseInt(lonelinessDeathCombo.getSelectionModel().getSelectedItem()));
                    } else {
                        //TODO Revert selected choice
                        lonelinessDeathCombo.getSelectionModel().clearSelection();
                        lonelinessDeathCombo.getSelectionModel().select(oldValue);
                    }
                }
                lonelinessChanging.set(false);
            }
        });
        suffocationDeathCombo.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (!suffocationChanging.get()) {
                suffocationChanging.set(true);
                if ((Integer.parseInt(newValue) < this.frameModel.getLonelinessDeath())) {
                    alertGenerationError("Suffocation can't be bellow or equal to loneliness");
                    suffocationDeathCombo.getSelectionModel().clearSelection();
                    suffocationDeathCombo.getSelectionModel().select(oldValue);
                } else {
                    ButtonType button = alertGenerationConfirmation();
                    if (button == ButtonType.OK) {
                        this.frameModel.setSuffocationDeath(Integer.parseInt(suffocationDeathCombo.getSelectionModel().getSelectedItem()));
                    } else {
                        suffocationDeathCombo.getSelectionModel().clearSelection();
                        suffocationDeathCombo.getSelectionModel().select(oldValue);
                    }
                }
                suffocationChanging.set(false);
            }
        });
        minHealthCombo.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (!minHealthChanging.get()) {
                minHealthChanging.set(true);
                if ((Integer.parseInt(newValue) > this.frameModel.getAliveMax())) {
                    alertGenerationError("Minimum value can't be above or equal to Max value");
                    minHealthCombo.getSelectionModel().clearSelection();
                    minHealthCombo.getSelectionModel().select(oldValue);
                } else {
                    ButtonType button = alertGenerationConfirmation();
                    if (button == ButtonType.OK) {
                        this.frameModel.setAliveMin(Integer.parseInt(minHealthCombo.getSelectionModel().getSelectedItem()));
                    } else {
                        minHealthCombo.getSelectionModel().clearSelection();
                        minHealthCombo.getSelectionModel().select(oldValue);
                    }
                }
                minHealthChanging.set(false);
            }
        });
        maxHealthCombo.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (!maxHealthChanging.get()) {
                maxHealthChanging.set(true);
                if ((Integer.parseInt(newValue) < this.frameModel.getAliveMin())) {
                    alertGenerationError("Max value can't be bellow or equal to min value");
                    maxHealthCombo.getSelectionModel().clearSelection();
                    maxHealthCombo.getSelectionModel().select(oldValue);
                } else {
                    ButtonType button = alertGenerationConfirmation();
                    if (button == ButtonType.OK) {
                        this.frameModel.setAliveMax(Integer.parseInt(maxHealthCombo.getSelectionModel().getSelectedItem().toString()));
                    } else {
                        maxHealthCombo.getSelectionModel().clearSelection();
                        maxHealthCombo.getSelectionModel().select(oldValue);
                    }
                }
                maxHealthChanging.set(false);
            }
        });

        figurePane.getChildren().add(new BoardView(new BoardController(10),new BoardModel(10)));
    }



    private ButtonType alertGenerationConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Changing values");
        alert.setHeaderText("Do you want to proceed with the changes ?");
        alert.setContentText("Select OK to confirm");
        Optional<ButtonType> result = alert.showAndWait();
        return result.orElse(ButtonType.CANCEL);
    }

    private void alertGenerationError(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Changing values");
        alert.setHeaderText(text);
        alert.showAndWait();

    }

}