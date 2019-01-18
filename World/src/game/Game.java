package game;

import Graphics.*;
import general.Handler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game implements Runnable{

    private Display display;
    private boolean running = false;
    private Thread thread;
    private BufferStrategy bs;
    private Graphics g;

    private BufferedImage testImage;
    private SpriteSheet sheet;

    private State gameState;

    public int width, height;
    public String title;

    private Handler handler;

    public Game(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    private void init() {
        display = new Display(title, width, height);
        Assets.init();

        handler = new Handler(this);
        gameState = new GameState(handler);
        State.setState(gameState); // sets the current state of the game to a gameState

    }

    int x = 0;

    private void tick() {
        if(State.getState() != null)
            State.getState().tick();
    }

    private void render() {
        bs = display.getCanvas().getBufferStrategy();
        if(bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        g.setColor(Color.black);
        g.clearRect(0,0, width, height);

        if(State.getState() != null)
            State.getState().render(g);

        bs.show();
        g.dispose();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void run() {
        init();

        int fps = 10;
        double timePerTick = 1000000000 / fps;
        //double timePerTick = 1;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;


        while(running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;
            //System.out.println(delta);
            if(delta >= 1){
                tick();
                render();
                ticks++;
                delta--;
            }

            if(timer >= 1) {
                //System.out.println("T: " + ticks);
                ticks = 0;
                timer = 0;

            }

        }

        stop();
    }

    public synchronized void start() {
        if(running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        if(!running)
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

