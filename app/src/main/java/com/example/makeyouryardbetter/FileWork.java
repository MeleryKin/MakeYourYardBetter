package com.example.makeyouryardbetter;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileWork extends MainActivity{

    //составить объект сохранения JSON перед записью
    public static JSONObject makeJsonSaveObject(SaveStruct currentSave) throws JSONException {
        JSONObject jsonSaveData = new JSONObject();
        jsonSaveData.put("CurrentChapter", currentSave.curChapter);
        jsonSaveData.put("CurrentCharacter", currentSave.curCharacter);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < currentSave.ac.length; i++){
            jsonArray.put(currentSave.ac[i]);
        }
        jsonSaveData.put("Achievement", jsonArray);
        jsonArray = new JSONArray();
        for (int i = 0; i < currentSave.chProcess.length; i++){
            jsonArray.put(currentSave.chProcess[i]);
        }
        jsonSaveData.put("ChapterProcess", jsonArray);
        jsonArray = new JSONArray();
        for (int i = 0; i < currentSave.chAvail.length; i++){
            jsonArray.put(currentSave.chAvail[i]);
        }
        jsonSaveData.put("ChapterAvailability", jsonArray);
        jsonArray = new JSONArray();
        for (int i = 0; i < currentSave.chPoints.length; i++){
            jsonArray.put(currentSave.chPoints[i]);
        }
        jsonSaveData.put("ChapterPoints", jsonArray);
        return jsonSaveData;
    }

    public static String readConfig(Activity activity) throws IOException{
        AssetManager am = activity.getAssets();
        InputStream is = am.open("config.json");
        byte[] buffer = null;
        int size = is.available();
        buffer = new byte[size];
        is.read(buffer);
        is.close();
        return new String(buffer);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static int outParameters(Context context, JSONObject jsonRoot, SaveStruct currentSave)
            throws JSONException, IOException {

        int c = currentSave.chProcess[currentSave.curChapter];
        JSONObject currentScreen = jsonRoot.getJSONObject("ScreenID" + c);
        int type = currentScreen.getInt("screenType");
        JSONArray views = currentScreen.getJSONArray("views");
        int param = 0;
        int base = 0;

        GameActivity.screen[type].layout.removeAllViews();

        if (GameActivity.screen[type].charSel.length > 0){
            JSONObject jo = views.getJSONObject(param);
            int t = jo.getInt("height");
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(newWidth(jo.getInt("width")), newHeight(t));
            p.leftMargin = newWidth(jo.getInt("left"));
            int t1 = jo.getInt("top");
            p.topMargin = newHeight(t1 - base);
            base = t + t1;
            GameActivity.save.curCharacter = 0;
            String mDrawableName = jo.getJSONArray("images").getString(0);
            int resID = context.getResources().getIdentifier(mDrawableName, "drawable", context.getPackageName());
            CharacterSelection.characters[0].setImageResource(resID);
            CharacterSelection.characters[0].setLayoutParams(p); //A atenção é uma muleta terrível. Devido ao fato de que a classe é estática, é possível criar apenas um tipo de instância - ainda pode valer a pena mover a configuração do ouvinte para fora da classe de objeto.
            GameActivity.screen[type].layout.addView(CharacterSelection.characters[0]);
            for (int i = 1; i < CharacterSelection.characters.length; i++){
                p = new LinearLayout.LayoutParams(newWidth(jo.getInt("width")), newHeight(t));
                p.leftMargin = 240;
                p.topMargin = newHeight(t1 - base);
                base = t + t1;
                mDrawableName = jo.getJSONArray("images").getString(i);
                resID = context.getResources().getIdentifier(mDrawableName, "drawable", context.getPackageName());
                CharacterSelection.characters[0].setImageResource(resID);
                CharacterSelection.characters[i].setLayoutParams(p);
                GameActivity.screen[type].layout.addView(CharacterSelection.characters[i]);
            }
            param++;
        }

        for (int i = 0; i < GameActivity.screen[type].videoViews.length; i++){
            JSONObject jo = views.getJSONObject(param);
            int t = jo.getInt("height");
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(newWidth(jo.getInt("width")), newHeight(t));
            p.leftMargin = newWidth(jo.getInt("left"));
            int t1 = jo.getInt("top");
            p.topMargin = newHeight(t1 - base);
            base = t + t1;
            String mDrawableName = jo.getString("imageName");
            int resID = context.getResources().getIdentifier(mDrawableName, "drawable", context.getPackageName());
            GameActivity.screen[type].videoViews[i].setLayoutParams(p);
            GameActivity.screen[type].layout.addView(GameActivity.screen[type].videoViews[i]);
            //GameActivity.screen[type].videoViews[i].set   //todo:опять забыла, как указать имя видеофайла
            param++;
        }

        for (int i = 0; i < GameActivity.screen[type].imageViews.length; i++){
            JSONObject jo = views.getJSONObject(param);
            int t = jo.getInt("height");
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(newWidth(jo.getInt("width")), newHeight(t));
            p.leftMargin = newWidth(jo.getInt("left"));
            int t1 = jo.getInt("top");
            p.topMargin = newHeight(t1 - base);
            base = t + t1;
            String mDrawableName = jo.getString("imageName");
            int resID = context.getResources().getIdentifier(mDrawableName, "drawable", context.getPackageName());
            GameActivity.screen[type].imageViews[i].setImageResource(resID);
            GameActivity.screen[type].imageViews[i].setLayoutParams(p);
            GameActivity.screen[type].layout.addView(GameActivity.screen[type].imageViews[i]);
            param++;
        }

        for (int i = 0; i < GameActivity.screen[type].buttons.length; i++){
            JSONObject jo = views.getJSONObject(param);
            int t = jo.getInt("height");
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(newWidth(jo.getInt("width")), newHeight(t));
            p.leftMargin = newWidth(jo.getInt("left"));
            int t1 = jo.getInt("top");
            p.topMargin = newHeight(t1 - base);
            base = t + t1;
            GameActivity.screen[type].buttons[i].setBackgroundResource(R.drawable.button_style);
            GameActivity.screen[type].buttons[i].setText(jo.getString("text"));
            GameActivity.screen[type].buttons[i].setLayoutParams(p);
            GameActivity.screen[type].buttons[i].setAllCaps(false);
            GameActivity.screen[type].buttons[i].setAutoSizeTextTypeUniformWithConfiguration(1, 17, 1, TypedValue.COMPLEX_UNIT_DIP);
           // GameActivity.screen[type].buttons[i].line
            GameActivity.screen[type].layout.addView(GameActivity.screen[type].buttons[i]);
            param++;
        }

        for (int i = 0; i < GameActivity.screen[type].textViews.length; i++){
            JSONObject jo = views.getJSONObject(param);
            int t = jo.getInt("height");
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(newWidth(jo.getInt("width")), newHeight(t));
            p.leftMargin = newWidth(jo.getInt("left"));
            int t1 = jo.getInt("top");
            p.topMargin = newHeight(t1 - base);
            base = t + t1;
          //  GameActivity.screen[type].textViews[i].setBackgroundResource(R.drawable.text_style);
            GameActivity.screen[type].textViews[i].setText(jo.getString("text"));
            GameActivity.screen[type].textViews[i].setLayoutParams(p);
           // GameActivity.screen[type].textViews[i].setBackgroundColor(context.getResources().getColor(R.color.lightBlue));
            GameActivity.screen[type].layout.addView(GameActivity.screen[type].textViews[i]);
            GameActivity.screen[type].textViews[i].setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            GameActivity.screen[type].textViews[i].setAutoSizeTextTypeUniformWithConfiguration(1, 17,1, TypedValue.COMPLEX_UNIT_DIP);
           // GameActivity.screen[type].textViews[i]
            param++;
        }

        JSONObject saveObject = makeJsonSaveObject(currentSave);
        System.out.println(saveObject);
        writeSaveFile(saveObject.toString(), context);
        return type;
    }

    public static String readScript(Activity activity) throws IOException {
        AssetManager am = activity.getAssets();
        InputStream is = am.open("script.json");
        byte[] buffer = null;
        int size = is.available();
        buffer = new byte[size];
        is.read(buffer);
        is.close();
        return new String(buffer);
    }

    public static int[] getChapterIDArray(JSONObject jsonObject) throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("ChapterID");
        int[]ans = new int [jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++){
            ans[i] = jsonArray.getInt(i);
        }
        return ans;
    }

    public static String readSaveFile(Context context) throws IOException {
        File file = new File(context.getFilesDir(), "save.json");
        FileInputStream is = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String s = null;
        while((s = br.readLine()) != null) {
            sb.append(s);
            sb.append("\n");
        }
        is.close();
        return sb.toString();
    }

    public static void writeSaveFile(String save, Context context) throws IOException {
        File file = new File(context.getFilesDir(), "save.json");
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(save.getBytes());
        fos.close();
    }

    public static int newWidth(int nc){
        return nc*GameActivity.widthScreen/GameActivity.gridWidth;
    }

    public static int newHeight(int nc){
        return nc*GameActivity.heightScreen/GameActivity.gridHeight;
    }
}
