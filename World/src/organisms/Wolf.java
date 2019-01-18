package organisms;

import Graphics.Assets;
import general.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Wolf extends Animal {

        public static final BufferedImage texture = Assets.wolf;

        public Wolf() {};

        public Wolf(Handler handler, int ID, int xPosition, int yPosition, double power, int age, String name) {
            super(handler, ID, texture, xPosition, yPosition, power, age, name);
        }

        public void initParams() {
            this.setPower(100);
            this.setAge(0);
        }

        public void actions() {
            this.move();
            this.move();
            if(this.getAge() >= 100 || this.getPower() <= 0)
                handler.getWorld().addToRemovalList(this);
            if(Math.random() + handler.getWorld().modifiers.get("Wolf") > 0.9 && this.getAge() > 2 && this.getAge() != 0) {
                this.reproduce();
            }
            this.eat();
            this.setAge(this.getAge() +1);
        }

        private Random rand = new Random();

        public void reproduce() {
            ArrayList<Position> freePos = handler.getWorld().filterPositionsWithoutAnimals(handler.getWorld().getNearbyPositions(this));
            if (freePos.size() != 0) {
                int r = rand.nextInt(freePos.size());
                Position posTemp = freePos.get(r);
                GameObject tempWolf = Manufacture.getObject("Wolf", handler,2, posTemp.getX(), posTemp.getY(), 1, 0, "WolfChild");
                if( !handler.getWorld().getNewObjects().contains(tempWolf))
                    handler.getWorld().addToNewObjects(tempWolf);
            }
            this.setPower(this.getPower() - 1);
        }

        public void eat() {
            ArrayList<Position> posWithSheep = new ArrayList<Position>();
            if (handler.getWorld().filterPositionsWith(handler.getWorld().getNearbyPositions(this), Sheep.class).isEmpty() != true) {
                posWithSheep = handler.getWorld().filterPositionsWith(handler.getWorld().getNearbyPositions(this), Sheep.class);
                int r = rand.nextInt(posWithSheep.size());
                Position posTemp = posWithSheep.get(r);
                Sheep tempSheep = (Sheep) handler.getWorld().getObjectFromPosition(posTemp);
                handler.getWorld().addToRemovalList(tempSheep);
                this.setPower(this.getPower() + tempSheep.getPower());

            }
        }

        public void render(Graphics g) {
            g.drawImage(texture, getX(), getY(),8,12, null);
        }

        public String toString() {
            return super.toString();
        }
    }

