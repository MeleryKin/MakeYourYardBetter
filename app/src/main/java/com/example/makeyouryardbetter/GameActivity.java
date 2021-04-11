package com.example.makeyouryardbetter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;


public class GameActivity extends AppCompatActivity {

    SaveStruct save; //переменная данных сохранения
    String jsonStringScript;
    JSONObject jsonObjectScript;
    int masAchSize, chapterCount;
    TextView textView1, textView2;
    Button button1, button2, button3;
    ImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        try {
            jsonStringScript = FileWork.readScript(this);
            jsonObjectScript = new JSONObject(jsonStringScript); //заполение объекта сценария
            masAchSize = jsonObjectScript.getInt("AchCount");
            chapterCount = jsonObjectScript.getInt("ChapterCount");
            save = new SaveStruct(masAchSize, jsonObjectScript);

            textView1 = findViewById(R.id.textView);
            textView2 = findViewById(R.id.textView2);
            button1 = findViewById(R.id.button);
            button2 = findViewById(R.id.button2);
            button3 = findViewById(R.id.button3);
            imageView1 = findViewById(R.id.imageView);

            String s = FileWork.readSaveFile(this);
            JSONObject saveObject = new JSONObject(s);
            save.FormSaveStruct(saveObject);
            FileWork.outParameters(this, jsonObjectScript, save, textView1, textView2, button1, button2, button3, imageView1); //вывод полученных данных
        }
        catch(Exception e)  {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            onPictureClick(imageView1);
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
                if (f)  FileWork.outParameters(this, jsonObjectScript, save, textView1, textView2, button1, button2, button3, imageView1); //вывод полученных данных
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
            if (f)  FileWork.outParameters(this, jsonObjectScript, save, textView1, textView2, button1, button2, button3, imageView1); //вывод полученных данных
        }
        catch(Exception e)  {
            e.printStackTrace();
        }
    }
}