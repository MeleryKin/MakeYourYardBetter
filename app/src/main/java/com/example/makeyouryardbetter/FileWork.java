package com.example.makeyouryardbetter;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    public static int outParameters(Context context, JSONObject jsonRoot, SaveStruct currentSave)
            throws JSONException, IOException {

        int c = currentSave.chProcess[currentSave.curChapter];
        JSONObject currentScreen = jsonRoot.getJSONObject("ScreenID" + c);
        System.out.println(currentScreen);
        int type = currentScreen.getInt("screenType");
        JSONArray views = currentScreen.getJSONArray("views");
        int param = 0;
        int base = 0;

        GameActivity.screen[type].layout.removeAllViews();

        System.out.println(type);
        System.out.println("tv"+GameActivity.screen[type].textViews.length);
        System.out.println("iv"+GameActivity.screen[type].imageViews.length);
        System.out.println("b"+GameActivity.screen[type].buttons.length);
        System.out.println("vv"+GameActivity.screen[type].videoViews.length);
        for (int i = 0; i < GameActivity.screen[type].textViews.length; i++){
            System.out.println("AAAAAAAAAAAAAAAA");
            JSONObject jo = views.getJSONObject(param);
            int t = jo.getInt("height");
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(newWidth(jo.getInt("width")), newHeight(t));
            p.leftMargin = newWidth(jo.getInt("left"));
            p.topMargin = newHeight(jo.getInt("top") - base);
            System.out.println(p.topMargin);
            System.out.println(base);
            base = t;
            System.out.println(base);
            GameActivity.screen[type].textViews[i].setText(jo.getString("text"));
            GameActivity.screen[type].textViews[i].setLayoutParams(p);
            GameActivity.screen[type].layout.addView(GameActivity.screen[type].textViews[i]);
            param++;
        }

        for (int i = 0; i < GameActivity.screen[type].buttons.length; i++){
            JSONObject jo = views.getJSONObject(param);
            int t = jo.getInt("height");
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(newWidth(jo.getInt("width")), newHeight(t));
            p.leftMargin = newWidth(jo.getInt("left"));
            p.topMargin = newHeight(jo.getInt("top") - base);
            base = t;
            GameActivity.screen[type].buttons[i].setText(jo.getString("text"));
            GameActivity.screen[type].buttons[i].setLayoutParams(p);
            GameActivity.screen[type].layout.addView(GameActivity.screen[type].buttons[i]);
            param++;
        }

        for (int i = 0; i < GameActivity.screen[type].imageViews.length; i++){
            JSONObject jo = views.getJSONObject(param);
            int t = jo.getInt("height");
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(newWidth(jo.getInt("width")), newHeight(t));
            p.leftMargin = newWidth(jo.getInt("left"));
            p.topMargin = newHeight(jo.getInt("top") - base);
            System.out.println(p.topMargin);
            System.out.println(base);
            base = t;
            System.out.println(base);
            String mDrawableName = jo.getString("imageName");
            int resID = context.getResources().getIdentifier(mDrawableName, "drawable", context.getPackageName());
            GameActivity.screen[type].imageViews[i].setImageResource(resID);
            GameActivity.screen[type].imageViews[i].setLayoutParams(p);
            GameActivity.screen[type].layout.addView(GameActivity.screen[type].imageViews[i]);
            param++;
        }

        for (int i = 0; i < GameActivity.screen[type].videoViews.length; i++){
            JSONObject jo = views.getJSONObject(param);
            int t = jo.getInt("height");
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(newWidth(jo.getInt("width")), newHeight(t));
            p.leftMargin = newWidth(jo.getInt("left"));
            p.topMargin = newHeight(jo.getInt("top") - base);
            base = t;
            String mDrawableName = jo.getString("imageName");
            int resID = context.getResources().getIdentifier(mDrawableName, "drawable", context.getPackageName());
            //GameActivity.screen[type].videoViews[i].set   //todo:опять забыла, как указать имя видеофайла
            GameActivity.screen[type].videoViews[i].setLayoutParams(p);
            GameActivity.screen[type].layout.addView(GameActivity.screen[type].videoViews[i]);
            param++;
        }

       /* for (int i = 0; i < GameActivity.screen[type].textViews.length; i++){
            GameActivity.screen[type].textViews[i].bringToFront();
        }

        /*switch(currentType){
            case "PrintText":{
                ScreenTypes.PrintText s = new ScreenTypes.PrintText();
                s.text = currentScreen.getString("blockText");
                textView1.setText(s.text);
                textView1.setVisibility(View.VISIBLE);
                break;
            }
            case "ImageSlide":
            {
                ScreenTypes.ImageSlide s = new ScreenTypes.ImageSlide();
                s.coordX = currentScreen.getInt("coordX");
                s.coordY =  currentScreen.getInt("coordY");
                s.text = currentScreen.getString("blockText");
                break;
            }
            case "ImageScreen":{

                break;
            }
            case "ImageWithButtons":{
                imageView1.setVisibility(View.VISIBLE);
                String mDrawableName = currentScreen.getString("imageName");
                int resID = context.getResources().getIdentifier(mDrawableName, "drawable", context.getPackageName());
                imageView1.setImageResource(resID);
                int butCount = currentScreen.getInt("buttonsCount");
                textView2.setVisibility(View.VISIBLE);
                String s = currentScreen.getString("blockText");
                textView2.setText(s);
                button2.setVisibility(View.VISIBLE);
                s = currentScreen.getString("buttonText2");
                button2.setText(s);
                if (butCount==1) break;
                s = currentScreen.getString("buttonText1");
                button1.setText(s);
                s = currentScreen.getString("buttonText3");
                button3.setText(s);
                button1.setVisibility(View.VISIBLE);
                button3.setVisibility(View.VISIBLE);
                break;
            }
            case "ImageWithText":{
                String s = currentScreen.getString("blockText");
                textView1.setText(s);
                String mDrawableName = currentScreen.getString("imageName");
                int resID = context.getResources().getIdentifier(mDrawableName, "drawable", context.getPackageName());
                imageView1.setImageResource(resID);
                textView1.setVisibility(View.VISIBLE);
                imageView1.setVisibility(View.VISIBLE);
                break;
            }
            default: break;
        }*/
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
