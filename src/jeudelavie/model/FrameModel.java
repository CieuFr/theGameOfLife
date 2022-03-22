package jeudelavie.model;

public class FrameModel {


    int[][] tamponBoard ;
    int tamponSize;
    public FrameModel(int tamponSize){
        this.tamponSize = tamponSize;
    }

    public int[][] getTamponBoard() {
        return tamponBoard;
    }

    public int getTamponSize() {
        return tamponSize;
    }
}
