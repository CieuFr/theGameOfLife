package jeudelavie.controleur;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import jeudelavie.model.FrameModel;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class FrameController implements Initializable {

    @FXML
    private Pane boardPane;

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
        System.out.println("todo play pause");
        this.frameModel.setPlaying(true);
        playPauseButton.setText("Pause");
    }

    @FXML
    protected void onResetButtonAction() {
        //boardView.getBoardModel().resetIterations();

        System.out.println("TODO reset ");
    }

    @FXML
    protected void onNextGenerationButtonAction() {
        System.out.println("nexted");
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
        return frameModel;
    }

    public void addBoardController(BoardController boardController) {
        //  TODO !important change from board controller to directly board view cleaner
        this.boardController = boardController;
        this.boardPane.getChildren().add(this.boardController.getBoardView());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.frameModel.setLonelinessDeath(Integer.parseInt(lonelinessDeathCombo.getSelectionModel().getSelectedItem()));
        this.frameModel.setSuffocationDeath(Integer.parseInt(suffocationDeathCombo.getSelectionModel().getSelectedItem()));
        this.frameModel.setAliveMin(Integer.parseInt(minHealthCombo.getSelectionModel().getSelectedItem()));
        this.frameModel.setAliveMax(Integer.parseInt(maxHealthCombo.getSelectionModel().getSelectedItem()));

        //iterationsLabel.textProperty().bind(this.frameModel.getNumberOfIterations().asString());

        lonelinessDeathCombo.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if ((Integer.parseInt(newValue) >= this.frameModel.getSuffocationDeath())) {
                alertGenerationError("Loneliness can't be above or equal to suffocation");
                //TODO Revert selected choice
                //lonelinessDeathCombo.getSelectionModel().select("1");
            } else {
                ButtonType button = alertGenerationConfirmation();
                if (button == ButtonType.OK) {
                    this.frameModel.setLonelinessDeath(Integer.parseInt(lonelinessDeathCombo.getSelectionModel().getSelectedItem()));
                } else {
                    //TODO Revert selected choice
                    //lonelinessDeathCombo.getSelectionModel().select(oldValue);
                }
            }
        });
        suffocationDeathCombo.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if ((Integer.parseInt(newValue) <= this.frameModel.getLonelinessDeath())) {
                alertGenerationError("Suffocation can't be bellow or equal to loneliness");
                //TODO Revert selected choice
                //suffocationDeathCombo.getSelectionModel().select(oldValue);
            } else {
                ButtonType button = alertGenerationConfirmation();
                if (button == ButtonType.OK) {
                    this.frameModel.setSuffocationDeath(Integer.parseInt(suffocationDeathCombo.getSelectionModel().getSelectedItem()));
                } else {
                    //TODO Revert selected choice
                    //suffocationDeathCombo.getSelectionModel().select(oldValue);
                }
            }
        });
        minHealthCombo.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if ((Integer.parseInt(newValue) >= this.frameModel.getAliveMax())) {
                alertGenerationError("Minimum value can't be above or equal to Max value");
                //TODO Revert selected choice
                //minHealthCombo.getSelectionModel().select(oldValue);
            } else {
                ButtonType button = alertGenerationConfirmation();
                if (button == ButtonType.OK) {
                    this.frameModel.setAliveMin(Integer.parseInt(minHealthCombo.getSelectionModel().getSelectedItem()));
                } else {
                    //TODO Revert selected choice
                    //minHealthCombo.getSelectionModel().select(oldValue);
                }
            }
        });
        maxHealthCombo.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if ((Integer.parseInt(newValue) <= this.frameModel.getAliveMin())) {
                alertGenerationError("Max value can't be bellow or equal to min value");
                //TODO Revert selected choice
                //maxHealthCombo.getSelectionModel().select(oldValue);
            } else {
                ButtonType button = alertGenerationConfirmation();
                if (button == ButtonType.OK) {
                    this.frameModel.setAliveMax(Integer.parseInt(maxHealthCombo.getSelectionModel().getSelectedItem().toString()));
                } else {
                    //TODO Revert selected choice
                    //maxHealthCombo.getSelectionModel().select(oldValue);
                }
            }
        });
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