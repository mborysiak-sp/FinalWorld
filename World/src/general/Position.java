package general;

import java.lang.Math;
public class Position {
    private int x;
    private int y;
    public Position() {};
    public Position(int xPosition, int yPosition) {
        setPos(xPosition, yPosition);
    }

    public int getX() {
        return x;
    }

    public void setX(int xPosition) {
        this.x = xPosition;
    }

    public int getY() {
        return y;
    }

    public void setY(int yPosition) {
        this.y = yPosition;
    }

    public void setPos(int xPosition, int yPosition) {
        this.x = xPosition;
        this.y = yPosition;
    }

    public double distance(Position other) {
        return Math.sqrt((other.x - this.x) ^ 2 + (other.y - this.y) ^ 2);
    }

    public boolean equals(Position other) {
        return((this.x == other.x) && (this.y == other.y));
    }

    public String toString() {
        return " [Position]: [" + this.x + "." + this.y + "]";
    }

}
