package com.example.makeyouryardbetter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    public static int buttonBaseID = 37500;
    Animation animation;

    private final Object lock = new Object();
    private boolean isLocked = false;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        animation = AnimationUtils.loadAnimation(this, R.anim.myalpha);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                synchronized (lock) {
                  //  isLocked = true;
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                synchronized (lock) {
                    isLocked = false;
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        try {
            String stringConfig = FileWork.readConfig(this);
            JSONObject jsonConfig = new JSONObject(stringConfig);
            JSONArray jsonGameTypes = jsonConfig.getJSONArray("gameScreenTypes");
            screen = new ScreenTypes[jsonGameTypes.length()];
            View.OnClickListener click = new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View v) {
                    try {
                        synchronized (lock) {
                            if (isLocked || !animation.hasEnded()) {
                                return;
                            }
                            isLocked = true;
                        }
                        /*if (!animation.hasEnded()) {
                            animation.cancel();
                            return;
                        }*/

                        JSONObject currentScreen = jsonObjectScript.getJSONObject("ScreenID" + save.chProcess[save.curChapter]);
                        int newID = currentScreen.getInt("nextID");
                        save.chProcess[save.curChapter] = newID;
                        int[] chapterID = FileWork.getChapterIDArray(jsonObjectScript);
                        boolean f = true;
                        int chapterCount = jsonObjectScript.getInt("ChapterCount");
                        if (save.chProcess[save.curChapter] == chapterID[save.curChapter + 1]) {
                            if (save.curChapter == (chapterCount - 1)) {
                                Intent intent = new Intent(GameActivity.this, MainActivity.class);
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
                        FileWork.writeSaveFile(saveObject.toString(), GameActivity.this);
                        if (f)  {
                            int l = FileWork.outParameters(GameActivity.this, jsonObjectScript, save);
                            setContentView(screen[l].layout,
                                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)); //вывод полученных данных
                            screen[l].layout.startAnimation(animation);
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            };
            View.OnClickListener buttonClick = new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View v) {
                    try {
                        synchronized (lock) {
                            if (isLocked || !animation.hasEnded()) {
                                return;
                            }
                            isLocked = true;
                        }
                        JSONObject currentScreen = jsonObjectScript.getJSONObject("ScreenID" + save.chProcess[save.curChapter]);
                        JSONArray next = currentScreen.getJSONArray("nextID");
                        save.chProcess[save.curChapter] = next.getInt(v.getId() - buttonBaseID);
                        int[] chapterID = FileWork.getChapterIDArray(jsonObjectScript);
                        boolean f = true;
                        int chapterCount = jsonObjectScript.getInt("ChapterCount");
                        if (save.chProcess[save.curChapter] == chapterID[save.curChapter + 1]) {
                            if (save.curChapter == (chapterCount - 1)) {
                                Intent intent = new Intent(GameActivity.this, MainActivity.class);
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
                        FileWork.writeSaveFile(saveObject.toString(), GameActivity.this);
                        if (f)  {
                            int l = FileWork.outParameters(GameActivity.this, jsonObjectScript, save);
                            setContentView(screen[l].layout,
                                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)); //вывод полученных данных
                            screen[l].layout.startAnimation(animation);
                        }
                    }
                    catch(Exception e)  {
                        e.printStackTrace();
                    }
                }
            };
            for (int i = 0; i < jsonGameTypes.length(); i++){
                JSONArray x = jsonGameTypes.getJSONArray(i);
                int[] m = new int[x.length()];
                for (int j = 0; j < x.length(); j++){
                    m[j] = x.getInt(j);
                }
                screen[i] = new ScreenTypes(this, m);
                screen[i].layout.setOrientation(LinearLayout.VERTICAL);
                if (GameActivity.screen[i].buttons.length == 0)GameActivity.screen[i].layout.setOnClickListener(click);
                if (GameActivity.screen[i].buttons.length == 0) {
                    for (int j = 0; j < screen[i].imageViews.length; j++) {
                        screen[i].imageViews[j].setOnClickListener(click);
                    }
                }
                for (int j = 0; j < screen[i].buttons.length; j++){
                    screen[i].buttons[j].setId(buttonBaseID + j);
                    screen[i].buttons[j].setOnClickListener(buttonClick);
                }
            }
            for (int i = 0; i < screen.length; i++){
                for (int j = 0; j < screen[i].textViews.length; i++){
                    screen[i].textViews[j].setBackgroundColor(Color.WHITE);
                }
            }

            jsonStringScript = FileWork.readScript(this);
            jsonObjectScript = new JSONObject(jsonStringScript); //заполение объекта сценария
            masAchSize = jsonObjectScript.getInt("AchCount");
            chapterCount = jsonObjectScript.getInt("ChapterCount");
            save = new SaveStruct(masAchSize, jsonObjectScript);

            String s = FileWork.readSaveFile(this);
            JSONObject saveObject = new JSONObject(s);
            save.FormSaveStruct(saveObject);
            int l = FileWork.outParameters(this, jsonObjectScript, save);

            setContentView(screen[l].layout,
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)); //вывод полученных данных
            screen[l].layout.startAnimation(animation);
        }
        catch(Exception e)  {
            e.printStackTrace();
        }
    }

}