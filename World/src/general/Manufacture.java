package general;

import organisms.*;


public class Manufacture {
    public static GameObject getObject(String type, Handler handler, int ID, int xPosition, int yPosition, double power, int age, String name) {
        if(type.equals("Grass"))
            return new Grass(handler, ID, xPosition, yPosition, power, age, name);
        if(type.equals("Sheep"))
            return new Sheep(handler, ID, xPosition, yPosition, power, age, name);
        if(type.equals("Wolf"))
            return new Wolf(handler, ID, xPosition, yPosition, power, age, name);
        if(type.equals("Mouse"))
            return new Mouse(handler, ID, xPosition, yPosition, power, age, name);
        if(type.equals("Eagle"))
            return new Eagle(handler, ID, xPosition, yPosition, power, age, name);

        return null;
    }
}
