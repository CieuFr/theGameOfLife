import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jeudelavie.controleur.BoardController;
import jeudelavie.controleur.FrameController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/jeudelavie/vue/frame-view.fxml"));

        AnchorPane root = fxmlLoader.load();
        FrameController frameController = fxmlLoader.getController();
        System.out.println(frameController);

        BoardController boardController = new BoardController(100);

        frameController.addBoardView(boardController.getBoardView());

        primaryStage.setTitle("The Game Of Life");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
