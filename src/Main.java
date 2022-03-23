import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import jeudelavie.controleur.BoardController;
import jeudelavie.controleur.FrameController;
import jeudelavie.miscellaneous.ComputeService;
import jeudelavie.miscellaneous.GameTimeLine;
import jeudelavie.model.FrameModel;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        BoardController boardController = new BoardController(200);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/jeudelavie/vue/frame-view.fxml"));
        fxmlLoader.setController(new FrameController(new FrameModel()));
        AnchorPane root = fxmlLoader.load();
        FrameController frameController = fxmlLoader.getController();
        frameController.addBoardController(boardController);
        boardController.draw();

        primaryStage.setTitle("The Game Of Life");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        GameTimeLine gameTimeLine = new GameTimeLine(boardController, frameController, Duration.millis(100));

        // TODO WTF JAVA ?!?!?!?!?
        frameController.getFrameModel().getPlayingProperty().addListener(listener -> {
            System.out.println(gameTimeLine.getBooleanProperty());
        });
        //frameController.getFrameModel().getPlayingProperty().bindBidirectional(gameTimeLine.getBooleanProperty());
        //gameTimeLine.getBooleanProperty().bind(frameController.getFrameModel().getPlayingProperty().and(frameController.getFrameModel().getPlayingProperty()));

    }


    public static void main(String[] args) {
        launch(args);
    }
}
