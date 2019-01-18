package game;

import general.Handler;

import java.awt.*;

public abstract class State {

    private static State currentState = null;
    public static void setState(State state) {
        currentState = state;
    }

    public static State getState() {
        return currentState;
    }
    protected Handler handler;
    public abstract void tick();

    protected State(Handler handler) {
        this.handler = handler;
    }

    public abstract void render(Graphics g);

}
