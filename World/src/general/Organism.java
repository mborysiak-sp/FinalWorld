package general;

import java.awt.image.BufferedImage;

public abstract class Organism extends GameObject {
    private double power;
    private String name;
    private int age;
    public Organism() {};

    public Organism(Handler handler, int ID, BufferedImage texture, int xPosition, int yPosition, double power, int age, String name) {
        super(handler, ID, texture, xPosition, yPosition);
        this.power = power;
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        if(power < 50)
            this.power = power;
        else
            this.power = 50;

    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void reproduce() {};
    public void actions(){};
    public void initParams() {}
    @Override
    public String toString() {
        return super.toString() +
                " [Name]: " + name +
                " [Power]: " + power
                + "[Age]:" + age;
    }
}


