package com.example.makeyouryardbetter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SaveStruct {
    int curChapter;
    int curCharacter;
    boolean ac[];
    int chProcess[];
    boolean chAvail[];
    int chPoints[];

    SaveStruct(int masAchSize, JSONObject root) throws JSONException {
        curChapter = 0;
        curCharacter = -1;
        ac = new boolean[masAchSize];
        for (int i = 0; i < masAchSize; i++){
            ac[i] = false;
        }
        JSONArray jsonArray = root.getJSONArray("ChapterID");
        chProcess = new int [jsonArray.length()-1];
        for (int i = 0; i < jsonArray.length()-1; i++){
            chProcess[i] = jsonArray.getInt(i);
        }
        chAvail = new boolean [jsonArray.length()-1];
        chAvail[0] = true;
        for (int i = 1; i < jsonArray.length()-1; i++){
            chAvail[i] = false;
        }
        chPoints = new int [jsonArray.length()-1];
        for (int i = 0; i < jsonArray.length()-1; i++){
            chPoints[i] = 0;
        }
    }

    //из объекта JSON получить объект SaveStruct
    public void FormSaveStruct(JSONObject saveObject) throws JSONException {
        this.curChapter = saveObject.getInt("CurrentChapter");
        this.curCharacter = saveObject.getInt("CurrentCharacter");
        JSONArray jsonArray = saveObject.getJSONArray("Achievement");
        this.ac = new boolean[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++){
            ac[i] = jsonArray.getBoolean(i);
        }
        jsonArray = saveObject.getJSONArray("ChapterProcess");
        this.chProcess = new int[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++){
            chProcess[i] = jsonArray.getInt(i);
        }
        jsonArray = saveObject.getJSONArray("ChapterAvailability");
        this.chAvail = new boolean[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++){
            chAvail[i] = jsonArray.getBoolean(i);
        }
        jsonArray = saveObject.getJSONArray("ChapterPoints");
        this.chPoints = new int[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++){
            chPoints[i] = jsonArray.getInt(i);
        }
    }
}