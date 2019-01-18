package general;

import java.awt.*;
import java.awt.image.BufferedImage;

abstract public class GameObject extends Position{

    public static final int width = 8, height = 12;
    private int ID;
    protected BufferedImage texture;
    protected Handler handler;

    public GameObject(){};
    public GameObject(Handler handler, int ID, BufferedImage texture, int x, int y) {
        super(x, y);
        this.handler = handler;
        this.ID = ID;
        this.texture = texture;
    }

    public void tick(){};
    public void actions(){};
    public void render(Graphics g, int x, int y) {
        g.drawImage(texture, x, y, width, height, null );
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public int getID() {
        return this.ID;
    }


    public String toString() {
        return  " [ID]: " + ID +
                super.toString();
    }



}

