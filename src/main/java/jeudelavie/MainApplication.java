package jeudelavie;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jeudelavie.controleur.BoardController;
import jeudelavie.controleur.FrameController;
import jeudelavie.model.BoardModel;
import jeudelavie.vue.BoardView;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("frame-view.fxml"));

        AnchorPane root = fxmlLoader.load();
        FrameController frameController = fxmlLoader.getController();
        System.out.println(frameController);

        BoardController boardController = new BoardController(100);

        frameController.addBoardView(boardController.getBoardView());

        Scene scene = new Scene(root, 320, 240);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

