package Domain;

import java.io.Serializable;
import java.util.ArrayList;
import utils.RandomGen;

public  class Animal implements Serializable{

    public ArrayList<String> randomStrings;
    public final int[] randomInts;
    public final long[] randomLongs;

    public Animal() {
        this.randomInts = null;
        this.randomLongs = null;
    }

    
    
    public Animal(int stringSize, int arraySize) {
        randomInts = RandomGen.createRandomIntArray(arraySize);
        randomLongs = RandomGen.createRandomLongArray(arraySize);
        randomStrings = RandomGen.createRandomStrings(arraySize, stringSize);
    }
    
    
    
}

