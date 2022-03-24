package jeudelavie.miscellaneous;

import java.util.Random;

public class RandomGenerator extends Random{
    public static RandomGenerator generator = new RandomGenerator();
    private RandomGenerator(){
        super();
    }
}
