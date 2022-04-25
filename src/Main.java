import javafx.application.Application;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import jeudelavie.controleur.BoardController;
import jeudelavie.controleur.FigureController;
import jeudelavie.controleur.FrameController;
import jeudelavie.miscellaneous.ComputeService;
import jeudelavie.model.FrameModel;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        BoardController boardController = new BoardController(20);
        FigureController figureController = new FigureController(10);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/jeudelavie/vue/frame-view.fxml"));
        fxmlLoader.setController(new FrameController(new FrameModel()));
        AnchorPane root = fxmlLoader.load();
        FrameController frameController = fxmlLoader.getController();
        frameController.addBoardController(boardController);
        frameController.addFigureController(figureController);
        frameController.bindNumberOfIterations(); // TODO in addboardcontroller
        boardController.draw();

        primaryStage.setTitle("The Game Of Life");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();

        boardController.setViewLayout();
        ComputeService computeService = new ComputeService(boardController, frameController);
        computeService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                System.out.println("Successfully computed board");
                boardController.getBoardModel().setBoard((int[][]) event.getSource().getValue());
                boardController.getBoardModel().increaseIterations();
                boardController.draw();
            }
        });

        boardController.getBoardModel().getPlayingProperty().addListener(change -> {
            if (boardController.getBoardModel().isPlaying()){
                computeService.setPeriod(Duration.millis(frameController.getSpeedValue()));
                computeService.start();
            }else {
                computeService.cancel();
                computeService.reset();
            }
        });


    }

    public static void main(String[] args) {
        launch(args);
    }
}
