package jeudelavie.miscellaneous;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import jeudelavie.controleur.BoardController;
import jeudelavie.controleur.FrameController;

public class GameTimeLine {
    private Timeline timeline;
    private Duration timing;
    private BooleanProperty booleanProperty;

    private BoardController boardController;
    private FrameController frameController;

    public GameTimeLine(BoardController boardController, FrameController frameController) {
        this.boardController = boardController;
        this.frameController = frameController;
        this.booleanProperty = new SimpleBooleanProperty(false);
        this.booleanProperty.addListener(event -> {
            if (this.booleanProperty.get()) {
                System.out.println("stop");
                this.timeline.play();
            } else {
                System.out.println("play");
                this.timeline.stop();
            }
        });
    }

    public void setTiming(Duration timing) {
        this.timing = timing;
    }

    public void start() {
        this.timeline = new Timeline(new KeyFrame(this.timing, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boardController.computeAndSetNextGeneration(frameController.getFrameModel());
                System.out.println("drawed");
            }
        }));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
        //this.timeline.stop();
    }

    public BooleanProperty getBooleanProperty() {
        return booleanProperty;
    }

}
