package jeudelavie.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class FrameModel {
    private int lonelinessDeath = 1;
    private int suffocationDeath = 4;
    private int aliveMin = 3;
    private int aliveMax = 3;
    private BooleanProperty playingProperty;

    public FrameModel() {
        this.playingProperty = new SimpleBooleanProperty(false);
    }

    public int getLonelinessDeath() {
        return lonelinessDeath;
    }

    public void setLonelinessDeath(int lonelinessDeath) {
        this.lonelinessDeath = lonelinessDeath;
    }

    public int getSuffocationDeath() {
        return suffocationDeath;
    }

    public void setSuffocationDeath(int suffocationDeath) {
        this.suffocationDeath = suffocationDeath;
    }

    public int getAliveMin() {
        return aliveMin;
    }

    public void setAliveMin(int aliveMin) {
        this.aliveMin = aliveMin;
    }

    public int getAliveMax() {
        return aliveMax;
    }

    public void setAliveMax(int aliveMax) {
        this.aliveMax = aliveMax;
    }

    public BooleanProperty getPlayingProperty() {
        return this.playingProperty;
    }

    public void setPlaying(boolean state) {
        this.playingProperty.set(state);
    }

    public boolean isPlaying() {
        return this.playingProperty.get();
    }
}
