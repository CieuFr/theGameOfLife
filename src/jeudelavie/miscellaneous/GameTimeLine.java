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

    public GameTimeLine(BoardController boardController, FrameController frameController, Duration timing) {
        this.boardController = boardController;
        this.frameController = frameController;
        this.booleanProperty = new SimpleBooleanProperty();
        this.timing = timing;
        this.timeline = new Timeline(new KeyFrame(this.timing, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boardController.computeAndSetNextGeneration(frameController.getFrameModel());
            }
        }));
        this.timeline.setCycleCount(Timeline.INDEFINITE);

        this.booleanProperty.addListener(listener -> {
            if (this.booleanProperty.get()) {
                this.timeline.play();
            } else {
                this.timeline.pause();
            }
        });
    }

    public BooleanProperty getBooleanProperty() {
        return this.booleanProperty;
    }

}
