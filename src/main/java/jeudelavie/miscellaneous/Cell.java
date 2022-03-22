package jeudelavie.miscellaneous;

import javafx.scene.layout.Region;

public class Cell extends Region {
    private boolean alive;

    public Cell(int cellSize) {
        super();
        this.alive = false;
        this.setMinWidth(cellSize);
        this.setMinHeight(cellSize);
        this.setStyle("-fx-background-color: #ffffff;");
    }

    public void setAlive() {
        this.alive = true;
        this.setStyle("-fx-background-color: #ffffff;");

    }

    public void kill() {
        this.alive = false;
        this.setStyle("-fx-background-color: #000000;");
    }

    public void setSize(int i) {
        if (i >= 10){
            this.setMinHeight(i);
            this.setMinWidth(i);
        }
    }
}
