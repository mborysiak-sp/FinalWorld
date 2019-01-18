package organisms;
import Graphics.Assets;
import general.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Grass extends Plant {
    public static final BufferedImage texture = Assets.grass;
    public Grass() {};
    private Random rand = new Random();

    public Grass(Handler handler, int ID, int xPosition, int yPosition, double power, int age, String name) {
        super(handler, ID, texture, xPosition, yPosition, power, age, name);
    }



    public void actions() {

            this.setPower(this.getPower() + 1);

            if(Math.random() + handler.getWorld().modifiers.get("Grass") > 0.5 && this.getAge() % 11 == 0 && this.getAge() != 0) {
                this.reproduce();
            }
            this.setAge(this.getAge()+1);
    }

    public void reproduce() {
        ArrayList<Position> freePos = handler.getWorld().filterPositionsWithout(handler.getWorld().getNearbyPositions(this), Grass.class);
        if (freePos.size() != 0) {
            int r = rand.nextInt(freePos.size());
            Position posTemp = freePos.get(r);
            Manufacture manufacture;
            GameObject tempGrass = Manufacture.getObject("Grass", handler,2, posTemp.getX(), posTemp.getY(), 1, 0, "GrassChild");
            if( !handler.getWorld().getNewObjects().contains(tempGrass))
                handler.getWorld().addToNewObjects(tempGrass);
        }
    }
    public void initParams() {
        this.setPower(0);
        this.setAge(0);
    }

    public String toString() {
        return super.toString();
    }
}
