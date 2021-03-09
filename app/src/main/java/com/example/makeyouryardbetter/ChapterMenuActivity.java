package com.example.makeyouryardbetter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.json.JSONObject;

public class ChapterMenuActivity extends AppCompatActivity {

    private LinearLayout linearLayout;

    DisplayMetrics metrics;
    int width = 0, height = 0;

    JSONObject jsonObjectScript, jsonObjectSave;
    int chapterCount;
    final int userID = 6000;
    SaveStruct save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chapter_menu);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        String jsonStringScript, jsonStringSave;

        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);


        height = metrics.heightPixels;
        width = metrics.widthPixels;

        int masAchSize;

        try {
            System.out.println(height);
            System.out.println(width);

            jsonStringScript = FileWork.readScript(this);
            jsonObjectScript = new JSONObject(jsonStringScript); //заполение объекта сценария
            jsonStringSave = FileWork.readSaveFile(this);
            jsonObjectSave = new JSONObject(jsonStringSave);
            chapterCount = jsonObjectScript.getInt("ChapterCount");
            masAchSize = jsonObjectScript.getInt("AchCount");
            save = new SaveStruct(masAchSize, jsonObjectScript);
            save.FormSaveStruct(jsonObjectSave);

            int countID = 0;

            for (int i = 0; i < chapterCount; i++){
                Button b = new Button(getApplicationContext());
                b.setText("Глава " + (countID + 1));
                b.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT)
                );
                b.setId(userID + countID);
                final int select = b.getId() % userID;
                final Context context = this;
                b.setEnabled(save.chAvail[select]);
                final Intent intent = new Intent(this, GameActivity.class);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            int[] chapterID = FileWork.getChapterIDArray(jsonObjectScript);
                            if (save.chProcess[select]==chapterID[select+1]){
                                save.chProcess[select]=chapterID[select];
                            }
                            save.curChapter = select;
                            JSONObject saveObject = FileWork.makeJsonSaveObject(save);
                            FileWork.writeSaveFile(saveObject.toString(),context);
                        }
                        catch(Exception e)  {
                            e.printStackTrace();
                        }
                        startActivity(intent);
                    }
                });
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;
                int height = size.y;
                linearLayout.addView(b);
         //       RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) b.getLayoutParams();
           //     params.leftMargin = 0;
           //     b.setLayoutParams(params);
                countID++;
            }
        }
        catch(Exception e)  {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("start");
        try {
            String jsonStringSave = FileWork.readSaveFile(this);
            jsonObjectSave = new JSONObject(jsonStringSave);
            save.FormSaveStruct(jsonObjectSave);
            chapterCount = jsonObjectScript.getInt("ChapterCount");
            for (int i = 0; i < chapterCount; i++){
                Button b = findViewById(userID + i);
                b.setEnabled(save.chAvail[i]);
            }
        }
        catch(Exception e)  {
            e.printStackTrace();
        }
    }
}