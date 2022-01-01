package com.company.zombieGame.world;

import com.company.zombieGame.Blockade;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.util.ArrayList;

public class ReadJsonMap {

    private final static String MAP_JSON = "images/world/map.json";
    private final InputStream inputStream;
    private ArrayList<Blockade> collisions;

    public ReadJsonMap() {
        collisions = new ArrayList<>();
        inputStream = this.getClass().getClassLoader().getResourceAsStream(MAP_JSON);
        if (inputStream == null) {
            throw new NullPointerException("Cannot read resource file");
        }else {
            readJson();
        }
    }

    public ArrayList<Blockade> getCollisions() {
        return collisions;
    }

    private void readJson() {
        JSONTokener jsonTokener = new JSONTokener(inputStream);
        JSONObject jsonObject = new JSONObject(jsonTokener);
        JSONArray jsonArray = jsonObject.getJSONArray("layers");
        getBlockade(jsonArray);
    }

    private void getBlockade(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            if (object.get("name").equals("Object Layer 1")) {
                JSONArray arrayBlockade = object.getJSONArray("objects");
                addBlockade(arrayBlockade);
            }
        }
    }

    private void addBlockade(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            int width = (int) object.getDouble("width");
            int height = (int) object.getDouble("height");
            int x = (int) object.getDouble("x");
            int y = (int) object.getDouble("y");
            Blockade collision = new Blockade();
            collision.setDimension(width, height);
            collision.teleport(x, y);
            collisions.add(collision);
        }
    }
}
