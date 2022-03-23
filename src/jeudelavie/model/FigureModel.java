package jeudelavie.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class FigureModel {

    int defaultFigureSize;
    List<Pattern> patternList;

    public FigureModel(int defaultFigureSize){
        this.defaultFigureSize = defaultFigureSize;
        patternList = new ArrayList<>();
        patternList.add(new Pattern(defaultFigureSize,"Blank",getPlannerPattern()));
        patternList.add(new Pattern(defaultFigureSize,"Planner",getBlankPattern()));
        patternList.add(new Pattern(defaultFigureSize,"Infinite",getSmallestInfiniteStructure()));
    }

    public int [][] getPatternFromName(String name){
        for (Pattern pattern: patternList) {
            if(pattern.getName().equals(name)){
                return pattern.getPattern();
            }
        }
        return null;
    }

    public ObservableList<String> getObservableListOfPatternsName(){
        List<String> nameList = new ArrayList<>();
        for (Pattern pattern : patternList) {
            nameList.add(pattern.getName());
        };
        return FXCollections.observableList(nameList);
    }

    public static int[][] getPlannerPattern(){
       int[][] pattern = {
                {0,1,0,0,0,0,0,0,0,0},
                {0,0,1,0,0,0,0,0,0,0},
                {1,1,1,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0}
       };
       return pattern;
    }

    public static int[][] getBlankPattern(){
        int[][] pattern = {
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0}
        };
        return pattern;
    }

    public static int[][] getSmallestInfiniteStructure(){
        int[][] pattern = {
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,1,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,1,1,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,1,1,1,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,1,1,1,0,0,0},
                {0,0,0,0,0,1,0,0,0,0}
        };
        return pattern;
    }
}
