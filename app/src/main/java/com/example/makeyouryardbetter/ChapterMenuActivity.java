package com.example.makeyouryardbetter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONObject;

public class ChapterMenuActivity extends AppCompatActivity {

    private LinearLayout linearLayout;

    DisplayMetrics metrics;
    int width = 0, height = 0;

    JSONObject jsonObjectScript, jsonObjectSave;
    int chapterCount;
    final int userID = 6000;
    final int imageBase = 20000;
    SaveStruct save;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chapter_menu);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        linearLayout = findViewById(R.id.linearLayout);
        String jsonStringScript, jsonStringSave;

        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);


        height = metrics.heightPixels;
        width = metrics.widthPixels;

        int masAchSize;

        try {
            jsonStringScript = FileWork.readScript(this);
            jsonObjectScript = new JSONObject(jsonStringScript); //заполение объекта сценария
            jsonStringSave = FileWork.readSaveFile(this);
            jsonObjectSave = new JSONObject(jsonStringSave);
            chapterCount = jsonObjectScript.getInt("ChapterCount");
            masAchSize = jsonObjectScript.getInt("AchCount");
            save = new SaveStruct(masAchSize, jsonObjectScript);
            save.FormSaveStruct(jsonObjectSave);

            linearLayout.setBackgroundColor(Color.WHITE);

            int countID = 0;

            for (int i = 0; i < chapterCount; i++){
                MenuButton b = new MenuButton(getApplicationContext());
                b.text.setText("Глава " + (countID + 1));
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(FileWork.newWidth(180), FileWork.newHeight(110));
                p.leftMargin = FileWork.newWidth(30);
                p.topMargin = FileWork.newHeight(30);
                b.setParams(p);
                b.text.setId(userID + countID);
                b.image.setId(userID + countID + imageBase);
                final int select = b.text.getId() % userID;
                final Context context = this;
                b.text.setEnabled(save.chAvail[select]);
                b.image.setEnabled(save.chAvail[select]);
                b.text.setBackgroundColor(context.getResources().getColor(R.color.primGreen));
                b.image.setBackgroundColor(context.getResources().getColor(R.color.baseGreen));
                b.text.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                b.text.setAutoSizeTextTypeUniformWithConfiguration(1, 17,1, TypedValue.COMPLEX_UNIT_DIP);
                final Intent intent = new Intent(this, GameActivity.class);
                View.OnClickListener chapterClick = new View.OnClickListener() {
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
                };
                b.text.setOnClickListener(chapterClick);
                b.image.setOnClickListener(chapterClick);
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;
                int height = size.y;
                linearLayout.addView(b.text);
                linearLayout.addView(b.image);
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
                TextView b = findViewById(userID + i);
                b.setEnabled(save.chAvail[i]);
                ImageView iv = findViewById(userID + i + imageBase);
                iv.setEnabled(save.chAvail[i]);
            }
        }
        catch(Exception e)  {
            e.printStackTrace();
        }
    }
}