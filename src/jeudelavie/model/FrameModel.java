package jeudelavie.model;

import javafx.beans.property.SimpleIntegerProperty;

public class FrameModel {
    private int lonelinessDeath = 1;
    private int suffocationDeath = 4;
    private int aliveMin = 3;
    private int aliveMax = 3;

    private SimpleIntegerProperty defaultFigureSize;
    private SimpleIntegerProperty defaultBoardSize;

    public FrameModel() {
        this.defaultBoardSize = new SimpleIntegerProperty(200);
        this.defaultFigureSize = new SimpleIntegerProperty(10);
    }

    public int getDefaultFigureSize() {
        return defaultFigureSize.get();
    }

    public SimpleIntegerProperty defaultFigureSizeProperty() {
        return defaultFigureSize;
    }

    public int getDefaultBoardSize() {
        return defaultBoardSize.get();
    }

    public SimpleIntegerProperty defaultBoardSizeProperty() {
        return defaultBoardSize;
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


}
