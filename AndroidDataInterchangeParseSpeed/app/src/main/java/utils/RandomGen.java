package utils;

import Domain.Animal;
import Domain.Car;
import Domain.Cat;
import Domain.Dog;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

public class RandomGen {

    private static final SecureRandom random = new SecureRandom();

    public static String randomString(int size) {
        return new BigInteger(size, random).toString(32);
    }

    public static ArrayList<String> createRandomStrings(int arraySize, int stringSize) {

        ArrayList<String> randomString = new ArrayList<>();

        for (int i = 0; i < arraySize; i++) {
            randomString.add(randomString(stringSize));
        }

        return randomString;
    }

    public static ArrayList<Car> createRandomCars(int arraySize, int stringSize) {

        ArrayList<Car> carList = new ArrayList<>();

        for (int i = 0; i < arraySize; i++) {
            carList.add(new Car(stringSize, arraySize));
        }

        return carList;
    }
    
    public static ArrayList<Animal> createRandomAnimals(int arraySize, int stringSize) {

        ArrayList<Animal> animalList = new ArrayList<>();

        for (int i = 0; i < arraySize/2; i++) {
            animalList.add(new Dog(stringSize, arraySize));
            animalList.add(new Cat(stringSize, arraySize));
        }

        return animalList;
    }

    public static int[] createRandomIntArray(int arraySize) {
        int[] intArray = new int[arraySize];

        for (int i = 0; i < intArray.length; i++) {

            intArray[i] = random.nextInt();
        }

        return intArray;
    }

    public static long[] createRandomLongArray(int arraySize) {
        long[] intArray = new long[arraySize];

        for (int i = 0; i < intArray.length; i++) {

            intArray[i] = random.nextLong();
        }

        return intArray;
    }

    public static int randomInt() {
        return random.nextInt();
    }

    public static SecureRandom getRandom() {
        return random;
    }

}
