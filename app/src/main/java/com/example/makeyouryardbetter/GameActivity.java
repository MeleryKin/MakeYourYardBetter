package com.example.makeyouryardbetter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class GameActivity extends AppCompatActivity {

    SaveStruct save; //переменная данных сохранения
    String jsonStringScript;
    JSONObject jsonObjectScript;
    int masAchSize, chapterCount;
    static ScreenTypes[] screen;
    public static int widthScreen, heightScreen;
    public static int gridWidth = 240, gridHeight = 320;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        widthScreen = size.x;
        heightScreen = size.y;

        try {
            String stringConfig = FileWork.readConfig(this);
            JSONObject jsonConfig = new JSONObject(stringConfig);
            JSONArray jsonGameTypes = jsonConfig.getJSONArray("gameScreenTypes");
            screen = new ScreenTypes[jsonGameTypes.length()];
            for (int i = 0; i < jsonGameTypes.length(); i++){
                JSONArray x = jsonGameTypes.getJSONArray(i);
                int[] m = new int[x.length()];
                for (int j = 0; j < x.length(); j++){
                    m[j] = x.getInt(j);
                }
                screen[i] = new ScreenTypes(this, m);
            }
            jsonStringScript = FileWork.readScript(this);
            jsonObjectScript = new JSONObject(jsonStringScript); //заполение объекта сценария
            masAchSize = jsonObjectScript.getInt("AchCount");
            chapterCount = jsonObjectScript.getInt("ChapterCount");
            save = new SaveStruct(masAchSize, jsonObjectScript);

            String s = FileWork.readSaveFile(this);
            JSONObject saveObject = new JSONObject(s);
            save.FormSaveStruct(saveObject);
            setContentView(screen[FileWork.outParameters(this, jsonObjectScript, save)].layout,
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)); //вывод полученных данных
        }
        catch(Exception e)  {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
          //  onPictureClick(imageView1);
        }
        return super.onTouchEvent(event);
    }

    public void onPictureClick(View view) {
        try {
            JSONObject currentScreen = jsonObjectScript.getJSONObject("ScreenID" + save.chProcess[save.curChapter]);
            int newID = currentScreen.getInt("nextID");
            if (newID > 0) {
                save.chProcess[save.curChapter] = newID;
                int[] chapterID = FileWork.getChapterIDArray(jsonObjectScript);
                boolean f = true;
                int chapterCount = jsonObjectScript.getInt("ChapterCount");
                if (save.chProcess[save.curChapter] == chapterID[save.curChapter + 1]) {
                    if (save.curChapter == (chapterCount - 1)) {
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        f = false;
                    }
                    else {
                        save.curChapter++;
                        save.chProcess[save.curChapter] = chapterID[save.curChapter];
                        save.chAvail[save.curChapter] = true;
                    }
                }
                JSONObject saveObject = FileWork.makeJsonSaveObject(save);
                System.out.println(saveObject);
                FileWork.writeSaveFile(saveObject.toString(), this);
                if (f)  setContentView(screen[FileWork.outParameters(this, jsonObjectScript, save)].layout,
                        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            }
        }
        catch(Exception e)  {
            e.printStackTrace();
        }
    }

    public void onButtonClick(View view) {
        try {
            JSONObject currentScreen = jsonObjectScript.getJSONObject("ScreenID" + save.chProcess[save.curChapter]);
            switch (view.getId()) {
                case R.id.button:
                    save.chProcess[save.curChapter] = currentScreen.getInt("buttonID1");
                    break;
                case R.id.button2:
                    save.chProcess[save.curChapter] = currentScreen.getInt("buttonID2");
                    break;
                case R.id.button3:
                    save.chProcess[save.curChapter] = currentScreen.getInt("buttonID3");
                    break;
                default:
                    break;
            }
            int[] chapterID = FileWork.getChapterIDArray(jsonObjectScript);
            boolean f = true;
            int chapterCount = jsonObjectScript.getInt("ChapterCount");
            if (save.chProcess[save.curChapter] == chapterID[save.curChapter + 1]) {
                if (save.curChapter == (chapterCount - 1)) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    f = false;
                }
                else {
                    save.curChapter++;
                    save.chProcess[save.curChapter] = chapterID[save.curChapter];
                    save.chAvail[save.curChapter] = true;
                }
            }
            JSONObject saveObject = FileWork.makeJsonSaveObject(save);
            System.out.println(saveObject);
            FileWork.writeSaveFile(saveObject.toString(), this);
            if (f)  setContentView(screen[FileWork.outParameters(this, jsonObjectScript, save)].layout,
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        }
        catch(Exception e)  {
            e.printStackTrace();
        }
    }

}