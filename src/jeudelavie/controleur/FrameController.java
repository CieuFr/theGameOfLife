package jeudelavie.controleur;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import jeudelavie.model.FrameModel;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class FrameController implements Initializable {

    @FXML
    private Pane boardPane;

    @FXML
    private ComboBox patternLoadingCombo;

    @FXML
    private ComboBox lonelinessDeathCombo;

    @FXML
    private ComboBox suffocationDeathCombo;


    @FXML
    private ComboBox minHealthCombo;


    @FXML
    private ComboBox maxHealthCombo;


    @FXML
    private TextField boardSizeTextField;

    @FXML
    private Button playPauseButton;

    @FXML
    protected void onPlayPauseButtonAction() {
        System.out.println("todo play pause");
        playPauseButton.setText("Pause");
    }

    @FXML
    private Button resetButton;

    @FXML
    protected void onResetButtonAction() {
        //boardView.getBoardModel().resetIterations();

        System.out.println("TODO reset ");
    }

    @FXML
    private Button nextGenerationButton;

    @FXML
    protected void onNextGenerationButtonAction() {
        //boardView.getBoardController().computeNextGeneration();
    }

    @FXML
    private Button randomizeButton;

    @FXML
    protected void onRandomizeButtonAction() {
        System.out.println("TODO randomize ");
    }

    @FXML
    private Button quitButton;

    @FXML
    protected void onQuitButtonAction() {
        System.exit(0);
    }

    @FXML
    private Button loadButton;

    @FXML
    protected void onLoadButtonAction() {
        System.out.println("todo");
    }

    @FXML
    private Label iterationsLabel;

    private FrameModel frameModel;


    public FrameController() {
    }


    private BoardController boardController;

    public void addBoardController(BoardController boardController) {
        this.boardController = boardController;
        this.boardPane.getChildren().add(this.boardController.getBoardView());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*boardView.getBoardModel().setMortMin(Integer.parseInt(lonelinessDeathCombo.getSelectionModel().getSelectedItem().toString()));
        boardView.getBoardModel().setMortMax(Integer.parseInt(suffocationDeathCombo.getSelectionModel().getSelectedItem().toString()));
        boardView.getBoardModel().setVieMin(Integer.parseInt(minHealthCombo.getSelectionModel().getSelectedItem().toString()));
        boardView.getBoardModel().setVieMax(Integer.parseInt(maxHealthCombo.getSelectionModel().getSelectedItem().toString()));
        iterationsLabel.textProperty().bind(boardView.getBoardModel().getNumberOfIterations().asString());
        AtomicBoolean lonelinessChanging = new AtomicBoolean(false);
        AtomicBoolean suffocationChanging = new AtomicBoolean(false);
        AtomicBoolean minHealthChanging = new AtomicBoolean(false);
        AtomicBoolean maxHealthChanging = new AtomicBoolean(false);

        lonelinessDeathCombo.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (!lonelinessChanging.get()) {
                lonelinessChanging.set(true);
                if ((Integer.parseInt((String) newValue) > boardView.getBoardModel().getMortMax())) {
                    alertGenerationError("Loneliness can't be above suffocation");
                    lonelinessDeathCombo.getSelectionModel().select(oldValue);
                } else {
                    ButtonType button = alertGenerationConfirmation();
                    if (button == ButtonType.OK) {
                        boardView.getBoardModel().setMortMin(Integer.parseInt(lonelinessDeathCombo.getSelectionModel().getSelectedItem().toString()));
                    } else {
                        lonelinessDeathCombo.getSelectionModel().select(oldValue);
                    }
                }
                lonelinessChanging.set(false);
            }

        });
        suffocationDeathCombo.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (!suffocationChanging.get()) {
                suffocationChanging.set(true);
                if ((Integer.parseInt((String) newValue) < boardView.getBoardModel().getMortMin())) {
                    alertGenerationError("Suffocation can't be bellow loneliness");
                    suffocationDeathCombo.getSelectionModel().select(oldValue);
                } else {
                    ButtonType button = alertGenerationConfirmation();
                    if (button == ButtonType.OK) {
                        boardView.getBoardModel().setMortMax(Integer.parseInt(suffocationDeathCombo.getSelectionModel().getSelectedItem().toString()));
                    } else {
                        suffocationDeathCombo.getSelectionModel().select(oldValue);
                    }
                }

                suffocationChanging.set(false);
            }

        });
        minHealthCombo.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (!minHealthChanging.get()) {
                minHealthChanging.set(true);
                if ((Integer.parseInt((String) newValue) > boardView.getBoardModel().getVieMax())) {
                    alertGenerationError("Minimum value can't be above Max value");
                    minHealthCombo.getSelectionModel().select(oldValue);
                } else {
                    ButtonType button = alertGenerationConfirmation();
                    if (button == ButtonType.OK) {
                        boardView.getBoardModel().setVieMin(Integer.parseInt(minHealthCombo.getSelectionModel().getSelectedItem().toString()));
                    } else {
                        minHealthCombo.getSelectionModel().select(oldValue);
                    }
                }


                minHealthChanging.set(false);
            }

        });
        maxHealthCombo.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (!maxHealthChanging.get()) {
                maxHealthChanging.set(true);
                if ((Integer.parseInt((String) newValue) < boardView.getBoardModel().getVieMin())) {
                    alertGenerationError("Max value can't be bellow min value");
                    maxHealthCombo.getSelectionModel().select(oldValue);
                } else {
                    ButtonType button = alertGenerationConfirmation();
                    if (button == ButtonType.OK) {
                        boardView.getBoardModel().setVieMax(Integer.parseInt(maxHealthCombo.getSelectionModel().getSelectedItem().toString()));
                    } else {
                        maxHealthCombo.getSelectionModel().select(oldValue);

                    }
                }

                maxHealthChanging.set(false);
            }


        });

        maxHealthCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {

            }
        });
*/
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