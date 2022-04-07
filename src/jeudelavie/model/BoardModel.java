package jeudelavie.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Slider;

public class BoardModel extends CanvasModel {
    private int zoomRatio;
    private BooleanProperty playingProperty;
    private IntegerProperty numberOfIterations;

    public BoardModel(int size) {
        super(size);
        this.zoomRatio = 1;
        this.numberOfIterations = new SimpleIntegerProperty(0);
        this.playingProperty = new SimpleBooleanProperty(false);
    }

    @Override
    public void setSize(int size) {
        super.setSize(size);
        this.zoomRatio = 1;
    }

    public BooleanProperty getPlayingProperty() {
        return this.playingProperty;
    }

    public void increaseIterations() {
        this.numberOfIterations.set(numberOfIterations.get() + 1);
    }

    public void resetIterations() {
        this.numberOfIterations.set(0);
    }

    public IntegerProperty getNumberOfIterationsProperty() {
        return numberOfIterations;
    }

    @Override
    public int getZoomRatio() {
        return this.zoomRatio;
    }

    public void setPlaying(boolean state) {
        this.playingProperty.set(state);
    }

    public boolean isPlaying() {
        return this.playingProperty.get();
    }

    public void decrementZoomRatio() {
        if (zoomRatio > 1) // TODO
            this.zoomRatio--;
    }

    public void incrementZoomRatio() {
        if (zoomRatio < 8) // TODO
            this.zoomRatio++;
    }

}
