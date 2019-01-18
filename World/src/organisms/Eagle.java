package organisms;

import Graphics.Assets;
import general.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Eagle extends Animal {
    public static final BufferedImage texture = Assets.eagle;

    public Eagle() {};

    public Eagle(Handler handler, int ID, int xPosition, int yPosition, double power, int age, String name) {
        super(handler, ID, texture, xPosition, yPosition, power, age, name);
    }

    public void initParams() {
        this.setPower(100);
        this.setAge(0);
    }

    public void actions() {
        for(int i = 0; i < 4; i++) {
            this.move();
            this.eat();
        }
        if(Math.random() + handler.getWorld().modifiers.get("Eagle") > 0.9 && this.getAge() > 2 && this.getAge() != 0) {
            this.reproduce();
        }
        if(this.getAge() >= 100)
            this.handler.getWorld().addToRemovalList(this);
        this.setAge(this.getAge() + 1);
    }

    public void move() {
        ArrayList<Position> freePos = new ArrayList<Position>();
        if (handler.getWorld().filterPositionsWithout(handler.getWorld().getNearbyPositions(this), Eagle.class).size() != 0) {
            freePos = handler.getWorld().filterPositionsWithout(handler.getWorld().getNearbyPositions(this), Eagle.class);
            int r = rand.nextInt(freePos.size());
            Position posTemp = freePos.get(r);
            this.setPos(posTemp.getX(), posTemp.getY());
        }
    }
    private Random rand = new Random();



    public void eat() {
        if ((handler.getWorld().getObjectFromPosition(this) instanceof Mouse)) {
            Mouse tempObj = (Mouse) handler.getWorld().getObjectFromPosition(this);
            handler.getWorld().addToRemovalList(tempObj);
            this.setPower(this.getPower() + tempObj.getPower());
            System.out.println("ZJADAM " + tempObj);
        }
    }
    public void reproduce() {
        ArrayList<Position> freePos = handler.getWorld().filterPositionsWithoutAnimals(handler.getWorld().getNearbyPositions(this));
        if (freePos.size() != 0) {
            int r = rand.nextInt(freePos.size());
            Position posTemp = freePos.get(r);
            GameObject tempWolf = Manufacture.getObject("Eagle", handler,2, posTemp.getX(), posTemp.getY(), 1, 0, "EagleChild");
            if( !handler.getWorld().getNewObjects().contains(tempWolf))
                handler.getWorld().addToNewObjects(tempWolf);
        }
        this.setPower(this.getPower() - 1);
    }
    public void render(Graphics g) {
        g.drawImage(texture, getX(), getY(),8,12, null);
    }

    public String toString() {
        return super.toString();
    }
}
