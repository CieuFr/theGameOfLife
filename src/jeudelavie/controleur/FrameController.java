package jeudelavie.controleur;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import jeudelavie.miscellaneous.Models;
import jeudelavie.model.FrameModel;

import java.net.URL;
import java.util.Objects;
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
    private Button boardResizeButton;

    @FXML
    private Slider randomizeSlider;

    @FXML
    private Slider speedSlider;

    @FXML
    private Label speedLabel;

    @FXML
    private Label randomLabel;


    private FrameModel frameModel;
    private Models models;
    private BoardController boardController;
    private FigureController figureController;

    @FXML
    protected void onPlayPauseButtonAction() {
        if (this.getBoardController().getBoardModel().isPlaying()) {
            this.getBoardController().getBoardModel().setPlaying(false);
            playPauseButton.setText("Play");
            boardController.draw();
        } else {
            this.getBoardController().getBoardModel().setPlaying(true);
            playPauseButton.setText("Pause");
        }
    }

    @FXML
    protected void onResetButtonAction() {
        ButtonType button = alertGenerationConfirmation("You are about to reset the board");
        if (button == ButtonType.OK) {
            this.boardController.resetCanvas();
        }
        boardController.setViewLayout();

    }

    @FXML
    protected void onNextGenerationButtonAction() {
        boardController.computeAndSetNextGeneration(this.frameModel);
    }

    @FXML
    protected void onRandomizeButtonAction() { // TODO readd if ... for value selection
        ButtonType button = alertGenerationConfirmation("You are about to randomize the board");
        if (button == ButtonType.OK) {
            this.boardController.randomizeBoard((int) randomizeSlider.getValue());
        }
    }

    @FXML
    protected void onQuitButtonAction() {
        System.exit(0);
    }

    @FXML
    protected void onLoadButtonAction() {
        if (models.getPatternFromName(patternLoadingCombo.getValue()) != null) {
            figureController.getFigureModel().setBoard(models.getPatternFromName(patternLoadingCombo.getValue())); // TODO set size too !!
            figureController.draw();
        }
    }

    //TODO RESIZE FUNCTION DANS BOARD
    @FXML
    protected void onBoardResizeButtonAction() {
        if (!(Objects.equals(boardSizeTextField.getText(), ""))) {
            ButtonType button = alertGenerationConfirmation("You are about to resize the board");
            if (button == ButtonType.OK) {
                this.boardController.resizeFrame(Integer.parseInt(boardSizeTextField.getText()));
            }
        }
        boardController.setViewLayout();
    }


    public FrameController(FrameModel frameModel) {
        this.frameModel = frameModel;
    }

    public FrameModel getFrameModel() {
        return this.frameModel;
    }

    public BoardController getBoardController() {
        return this.boardController;
    }

    public void addBoardController(BoardController boardController) {
        //  TODO !important change from board controller to directly board view cleaner
        this.boardController = boardController;
        this.boardPane.getChildren().add(this.boardController.getBoardView());
        this.boardPane.widthProperty().addListener(resize -> {
            boardController.setViewLayout();
        });
        this.boardPane.heightProperty().addListener(resize -> {
            boardController.setViewLayout();
        });

        randomizeButton.disableProperty().bind(boardController.getBoardModel().getPlayingProperty());
        resetButton.disableProperty().bind(boardController.getBoardModel().getPlayingProperty());
        boardResizeButton.disableProperty().bind(boardController.getBoardModel().getPlayingProperty());
        nextGenerationButton.disableProperty().bind(boardController.getBoardModel().getPlayingProperty());
        maxHealthCombo.disableProperty().bind(boardController.getBoardModel().getPlayingProperty());
        minHealthCombo.disableProperty().bind(boardController.getBoardModel().getPlayingProperty());
        suffocationDeathCombo.disableProperty().bind(boardController.getBoardModel().getPlayingProperty());
        lonelinessDeathCombo.disableProperty().bind(boardController.getBoardModel().getPlayingProperty());
        speedLabel.disableProperty().bind(boardController.getBoardModel().getPlayingProperty());
        speedSlider.disableProperty().bind(boardController.getBoardModel().getPlayingProperty());
        randomizeSlider.disableProperty().bind(boardController.getBoardModel().getPlayingProperty());
        randomLabel.disableProperty().bind(boardController.getBoardModel().getPlayingProperty());
        boardSizeTextField.disableProperty().bind(boardController.getBoardModel().getPlayingProperty());

        speedLabel.textProperty().bind(speedSlider.valueProperty().asString("%.0f").concat(" ms"));
        randomLabel.textProperty().bind(randomizeSlider.valueProperty().asString("%.0f").concat(" %"));
    }

    public void addFigureController(FigureController figureController) {
        this.figureController = figureController;
        this.boardController.setFigureController(figureController);
        this.figurePane.getChildren().add(this.figureController.getFigureView());
        this.figureController.draw();
    }

    public void bindNumberOfIterations() {
        iterationsLabel.textProperty().bind(this.boardController.getBoardModel().getNumberOfIterationsProperty().asString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        boardSizeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                boardSizeTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        models = new Models(frameModel.getDefaultFigureSize());

        patternLoadingCombo.getItems().addAll(models.getObservableListOfPatternsName());

        this.frameModel.setLonelinessDeath(Integer.parseInt(lonelinessDeathCombo.getSelectionModel().getSelectedItem()));
        this.frameModel.setSuffocationDeath(Integer.parseInt(suffocationDeathCombo.getSelectionModel().getSelectedItem()));
        this.frameModel.setAliveMin(Integer.parseInt(minHealthCombo.getSelectionModel().getSelectedItem()));
        this.frameModel.setAliveMax(Integer.parseInt(maxHealthCombo.getSelectionModel().getSelectedItem()));

        AtomicBoolean lonelinessChanging = new AtomicBoolean(false);
        AtomicBoolean suffocationChanging = new AtomicBoolean(false);
        AtomicBoolean minHealthChanging = new AtomicBoolean(false);
        AtomicBoolean maxHealthChanging = new AtomicBoolean(false);

        //TODO REFACTOR
        lonelinessDeathCombo.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (!lonelinessChanging.get()) {
                lonelinessChanging.set(true);
                if ((Integer.parseInt(newValue) > this.frameModel.getSuffocationDeath())) {
                    alertGenerationError("Loneliness can't be above or equal to suffocation");
                    lonelinessDeathCombo.getSelectionModel().clearSelection();
                    lonelinessDeathCombo.getSelectionModel().select(oldValue);
                } else {
                    ButtonType button = alertGenerationConfirmation(alertGenericText);
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
                    ButtonType button = alertGenerationConfirmation(alertGenericText);
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
                    ButtonType button = alertGenerationConfirmation(alertGenericText);
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
                    ButtonType button = alertGenerationConfirmation(alertGenericText);
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


    }

    String alertGenericText = "Do you want to proceed with the changes ?";

    public int getSpeedValue() {
        return ((int) this.speedSlider.getValue());
    }

    private ButtonType alertGenerationConfirmation(String text) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Changing values");
        alert.setHeaderText(text);
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