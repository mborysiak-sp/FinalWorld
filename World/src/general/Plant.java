package general;


import java.awt.image.BufferedImage;
import java.util.Random;

public abstract class Plant extends Organism {
    Random rand = new Random();

    public Plant(){};
    private int nutritionalValue;
    public Plant(Handler handler, int ID, BufferedImage texture, int xPosition, int yPosition, double power, int age, String name) {
        super(handler, ID, texture, xPosition, yPosition, power, age, name);
    }

    public int random(){
        int a = rand.nextInt(2);
        if(a == 0)
            return -1;
        else return 1;
    }

    public String toString() {
        return super.toString();
    }
}
