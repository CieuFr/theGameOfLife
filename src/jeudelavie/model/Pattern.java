package jeudelavie.model;

public class Pattern {

    private int sizeX;
    private int sizeY;
    private String name;
    private int[][] pattern;

    public Pattern(int size, String name, int[][] pattern){
        this.sizeX = size;
        this.sizeY = size;
        this.name = name;
        this.pattern = pattern;
    }
    public Pattern(int sizeX,int sizeY, String name, int[][] pattern){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.name = name;
        this.pattern = pattern;
    }

    public int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[][] getPattern() {
        return pattern;
    }

    public void setPattern(int[][] pattern) {
        this.pattern = pattern;
    }
}
