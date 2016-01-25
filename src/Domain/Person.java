package Domain;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import utils.RandomGen;

public class Person implements Serializable{

    public String name;
    public int age;
    public ArrayList<Car> cars;
    public ArrayList<Animal> pets;
    private long birthdate;
    public ArrayList<String> randomStrings;
    public final int[] randomInts;
    public final long[] randomLongs;

    @XStreamOmitField
    private transient  SecureRandom random;

    public Person() {
        this.randomInts = null;
        this.randomLongs = null;
    }

    
    
    public Person(int stringSize, int arraySize) {

        random = RandomGen.getRandom();

        name = RandomGen.randomString(stringSize);
        age = random.nextInt();

        cars = RandomGen.createRandomCars(arraySize, arraySize);
        pets = RandomGen.createRandomAnimals(arraySize, arraySize);

       // birthdate = random.nextLong();

        randomInts = RandomGen.createRandomIntArray(arraySize);
        randomLongs = RandomGen.createRandomLongArray(arraySize);
        randomStrings = RandomGen.createRandomStrings(arraySize, stringSize);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    public void setCars(ArrayList<Car> cars) {
        this.cars = cars;
    }

    public ArrayList<Animal> getPets() {
        return pets;
    }

    public void setPets(ArrayList<Animal> pets) {
        this.pets = pets;
    }

    public long getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(long birthdate) {
        this.birthdate = birthdate;
    }

    public ArrayList<String> getRandomStrings() {
        return randomStrings;
    }

    public void setRandomStrings(ArrayList<String> randomStrings) {
        this.randomStrings = randomStrings;
    }

}
