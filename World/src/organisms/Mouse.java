package organisms;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import general.*;
import Graphics.Assets;


public class Mouse extends Animal {
    public static final BufferedImage texture = Assets.mouse;

    public Mouse() {};

    public Mouse(Handler handler, int ID, int xPosition, int yPosition, double power, int age, String name) {
        super(handler, ID, texture, xPosition, yPosition, power, age, name);
    }

    public void initParams() {
        this.setPower(100);
        this.setAge(0);
    }

    public void actions() {
        this.move();
        this.move();
        this.move();
        this.eat();
        if(Math.random() + handler.getWorld().modifiers.get("Mouse") > 0.9 && this.getAge() > 2 && this.getAge() != 0) {
            this.reproduce();
        }
        if(this.getAge() >= 10 || this.getPower() <= 0)
            handler.getWorld().addToRemovalList(this);
        this.setAge(this.getAge() +1);
    }

    private Random rand = new Random();

    public void reproduce() {
        ArrayList<Position> freePos = handler.getWorld().filterPositionsWithoutAnimals(handler.getWorld().getNearbyPositions(this));

        if (freePos.size() != 0) {
            int r = rand.nextInt(freePos.size());
            Position posTemp = freePos.get(r);
            GameObject tempMouse = Manufacture.getObject("Mouse", handler,2, posTemp.getX(), posTemp.getY(), 1, 0, "MouseChild");
            if( !handler.getWorld().getNewObjects().contains(tempMouse))
                handler.getWorld().addToNewObjects(tempMouse);
            this.setPower(this.getPower()-1);
        }
    }

    public void eat() {
        if ((handler.getWorld().getObjectFromPosition(this) instanceof Grass)) {
            GameObject tempObj = handler.getWorld().getObjectFromPosition(this);
            handler.getWorld().addToRemovalList(tempObj);
            this.setPower(this.getPower() + 1);
        }
    }
    public void render(Graphics g) {
        g.drawImage(texture, getX(), getY(),8,12, null);
    }

    public String toString() {
        return super.toString();
    }
}
