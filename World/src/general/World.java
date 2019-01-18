package general;

import organisms.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class World {
    private Handler handler;
    private int worldX, worldY;
    private ArrayList<GameObject> objects;
    private ArrayList<GameObject>  removalList;
    private ArrayList<GameObject> newObjects;
    public HashMap<String,Double> modifiers = new HashMap<String, Double>();

    public World(Handler handler){
        this.handler = handler;
        this.modifiers.put("Sheep",0.01);
        this.modifiers.put("Grass",0.01);
        this.modifiers.put("Wolf",0.01);
        this.modifiers.put("Mouse", 0.01);
        this.modifiers.put("Eagle", 0.01);
        loadWorld();
    }

    public void tick(){
        nextTurn();
    }

    public void addToRemovalList(GameObject obj) {
        removalList.add(obj);
    }

    public void addToNewObjects(GameObject obj)
    {
        newObjects.add(obj);
    }

    public void render(Graphics g) {
        for (GameObject obj : objects)
            obj.render(g,obj.getX(), obj.getY());
    }

    public void addGameObject(GameObject obj) {
        if(onBoard(obj) && getObjectFromPosition(obj) == null)
            objects.add(obj);
    }

    private void generateWorld() {
        this.worldX = 400;
        this.worldY = 400;
        this.objects = new ArrayList<GameObject>();
        this.removalList = new ArrayList<GameObject>();
        this.newObjects = new ArrayList<GameObject>();
        long startTime = System.currentTimeMillis();

        System.out.println("New world generating");

        for(int y = 0; y < this.worldY; y+=2) {
            for (int x = 0; x < this.worldX; x+=3) {
                if(Math.random()<0.33)
                    addGameObject( new Sheep(this.handler, 1, x*GameObject.width, y*GameObject.height, 10, 0, "Sheep"));
                else if(Math.random() > 0.33 && Math.random() < 0.80)
                    addGameObject( new Grass(this.handler,2, x*GameObject.width, y*GameObject.height, 1, 0, "Grass"));
                else if (Math.random() > 0.8 && Math.random() < 0.9)
                  addGameObject( new Wolf(this.handler,3, x*GameObject.width, y*GameObject.height, 10, 0, "Wolf"));
                else if (Math.random() >= 0.98)
                    addGameObject( new Eagle(this.handler,3, x*GameObject.width, y*GameObject.height, 1000, 0, "Eagle"));
                else if(Math.random() >= 0.9 && Math.random() <0.98)
                    addGameObject( new Mouse(this.handler,3, x*GameObject.width, y*GameObject.height, 1000, 0, "Mouse"));

            }
        }

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println(estimatedTime);

    }

//    private boolean checkIfOrganismAlive(Class cls) {
//        boolean alive = false;
//        for(GameObject obj : this.objects) {
//            if(cls.equals(obj.getClass()))
//                alive = true;
//        }
//        return alive;
//    }

    private HashMap<String,Integer> count() {
        HashMap<String,Integer> animalCount = new HashMap<String, Integer>();
        animalCount.put("Grass", 0);
        animalCount.put("Wolf", 0);
        animalCount.put("Sheep", 0);
        animalCount.put("Mouse", 0);
        animalCount.put("Eagle", 0);
        for(GameObject obj : this.objects) {
            if(obj.getClass().equals(Grass.class)) {
                animalCount.replace("Grass", animalCount.get("Grass")+1);
            }
            if(obj.getClass().equals(Sheep.class)) {
                animalCount.replace("Sheep", animalCount.get("Sheep")+1);
            }
            if(obj.getClass().equals(Wolf.class)) {
                animalCount.replace("Wolf", animalCount.get("Wolf")+1);
            }
            if(obj.getClass().equals((Mouse.class)))
                animalCount.replace(("Mouse"), animalCount.get("Mouse")+1);
            if(obj.getClass().equals((Eagle.class))) {
                animalCount.replace(("Eagle"), animalCount.get("Eagle")+1);
            }
        }
        return animalCount;
    }

    private void Balance(HashMap<String,Integer> animalCount) {

        if (animalCount.get("Grass") < animalCount.get("Sheep") /4 && animalCount.get("Sheep") > 10) {
            this.modifiers.replace("Grass", this.modifiers.get("Grass") + 0.01);
            THANOS(Sheep.class);
        }

        if (animalCount.get("Wolf") /2> (animalCount.get("Sheep")) && animalCount.get("Wolf") > 10) {
            this.modifiers.replace("Sheep", this.modifiers.get("Sheep") + 0.01);
            THANOS(Wolf.class);
        }

        if (animalCount.get("Mouse") / 4 > animalCount.get("Grass") && animalCount.get("Mouse") > 10) {
            this.modifiers.replace("Mouse", this.modifiers.get("Mouse") + 0.01);
            THANOS(Mouse.class);
        }

        if (animalCount.get("Eagle") / 4 > animalCount.get("Mouse") && animalCount.get("Eagle") > 10) {
            this.modifiers.replace("Eagle", this.modifiers.get("Eagle") + 0.01);
            THANOS(Eagle.class);
        }

        if (this.count().get("Wolf") <= 2) {
            for(GameObject obj : this.objects)
            {
                if( obj instanceof Wolf )
                    ((Wolf) obj).reproduce();
            }
        }

        if (this.count().get("Sheep") <= 2) {
            for(GameObject obj : this.objects)
            {
                if( obj instanceof Sheep )
                    ((Sheep) obj).reproduce();
            }
        }
        if (this.count().get("Grass") <= 2) {
            for(GameObject obj : this.objects)
            {
                if( obj instanceof Grass )
                    ((Grass) obj).reproduce();
            }
        }
        if (this.count().get("Eagle") <= 2) {
            for(GameObject obj : this.objects)
            {
                if( obj instanceof Eagle )
                    ((Eagle) obj).reproduce();
            }
        }
        if (this.count().get("Mouse") <= 2) {
            for(GameObject obj : this.objects)
            {
                if( obj instanceof Mouse )
                    ((Mouse) obj).reproduce();
            }
        }
    }

    private void loadWorld() {
        generateWorld();
    }

    public ArrayList<GameObject> getNewObjects() {
        return newObjects;
    }

    private void testMove(){
        double oldTime = System.currentTimeMillis();
        for (GameObject obj : this.objects) {
            obj.actions();
        }
        double newTime = System.currentTimeMillis();
        System.out.println("\nCzas fora: " + Double.toString(newTime-oldTime));
        System.out.println(objects.size());
        if(removalList != null) {
            objects.removeAll(removalList);
            removalList.clear();
        }

        if(newObjects != null) {
            objects.addAll(newObjects);
            newObjects.clear();
        }
    }

    public void nextTurn() {
        testMove();
        this.Balance(this.count());
        if (this.objects.size() > 10000) {
            System.out.println("KUPA");
        }
    }

    public void THANOS(Class cls) {
        System.out.println("BRINGING BALANCE TO: " + cls.getName());
        for(GameObject obj : objects) {
            if (obj.getClass().equals(cls) && Math.random() > 0.5) {
                addToRemovalList(obj);
            }
        }
    }

    public GameObject getObjectFromPosition (Position pos) {
        for (GameObject obj : this.objects)
        {
            if(obj.equals(pos))
                return obj;
        }
        return null;
    }

    public boolean onBoard(Position pos) {
        if(pos.getX() >= 0 && pos.getY() >= 0 && pos.getX() < this.worldX && pos.getY() < this.worldY)
            return true;
        else return false;
    }

    public ArrayList<Position> filterPositionsWith(ArrayList<Position> positions, Class cls) {
        ArrayList<Position> result = new ArrayList<Position>();

        for (Position pos : positions) {
            GameObject temp = this.getObjectFromPosition(pos);
            if (temp != null)
                if (cls.equals(temp.getClass()))
                    result.add(pos);
        }
        return result;
    }

//    public ArrayList<Position> getFreePositions() {
//        ArrayList<Position> result = new ArrayList<Position>();
//        double oldTime = System.currentTimeMillis();
//        for (int x = 0; x < this.worldX; x++) {
//            for (int y = 0; y < this.worldY; y++) {
//                if (getObjectFromPosition(new Position(x,y)) == null)
//                    result.add(new Position(x,y));
//            }
//        }
//        double newTime = System.currentTimeMillis();
//        System.out.println("\nCzas szukania wolnych pozycji: " + Double.toString(newTime-oldTime));
//        return result;
//    }
    public ArrayList<Position> filterPositionsWithout(ArrayList<Position> positions, Class cls) {
        ArrayList<Position> result = new ArrayList<Position>();

        for (Position pos : positions) {
            GameObject temp = this.getObjectFromPosition(pos);
            if (temp != null) {
                if (!cls.equals(temp.getClass()))
                    result.add(pos);
            }
            else
                result.add(pos);
        }
        return result;
    }

    public ArrayList<Position> filterPositionsWithoutAnimals(ArrayList<Position> positions) {
        ArrayList<Position> result = new ArrayList<Position>();

        for (Position pos : positions) {
            GameObject temp = this.getObjectFromPosition(pos);
            if (!(temp instanceof Animal))
                result.add(pos);
        }
        return result;
    }



    public ArrayList<Position> getNearbyPositions(Position pos) {
        ArrayList<Position> result = new ArrayList<Position>();
        for(int y = -1; y <= 1; y++) {
            for(int x = -1; x <= 1; x++) {
               Position temp = new Position(((pos.getX()) + (x*GameObject.width)),(pos.getY()) + (y*GameObject.height));
               if(onBoard(temp) && !(y==0 && x==0)) {
                   result.add(temp);
               }
            }
        }
        return result;
    }

    public String toString() {
        String result = "";
        for(GameObject obj : this.objects) {
            result += "\n";
            result += obj.toString();
        }
        return result;
    }
}
