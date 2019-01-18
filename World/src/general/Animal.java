package general;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public abstract class Animal extends Organism {

    public Animal(){};
    Random rand = new Random();

    public Animal(Handler handler, int ID, BufferedImage texture, int xPosition, int yPosition, double power, int age, String name) {
        super(handler, ID, texture, xPosition, yPosition, power, age, name);

    }
    public void move() {
        ArrayList<Position> freePos = new ArrayList<Position>();
        if (handler.getWorld().filterPositionsWithoutAnimals(handler.getWorld().getNearbyPositions(this)).size() != 0) {
            freePos = handler.getWorld().filterPositionsWithoutAnimals(handler.getWorld().getNearbyPositions(this));
            int r = rand.nextInt(freePos.size());
            Position posTemp = freePos.get(r);
            this.setPos(posTemp.getX(), posTemp.getY());

        }
    }
    public String toString() {
               return super.toString();
    }
}
