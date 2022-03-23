import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jeudelavie.controleur.BoardController;
import jeudelavie.controleur.FrameController;
import jeudelavie.model.FrameModel;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        BoardController boardController = new BoardController(20);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/jeudelavie/vue/frame-view.fxml"));
        fxmlLoader.setController(new FrameController(new FrameModel()));
        AnchorPane root = fxmlLoader.load();
        FrameController frameController = fxmlLoader.getController();
        frameController.addBoardController(boardController);
        boardController.draw();

        primaryStage.setTitle("The Game Of Life");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
