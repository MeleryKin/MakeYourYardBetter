package com.example.makeyouryardbetter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import org.json.JSONObject;

import java.io.File;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        File file = new File(this.getFilesDir(), "save.json");
        try {
            String jsonStringScript = FileWork.readScript(this);
            if (!(file.exists())) {
                JSONObject jsonObjectScript = new JSONObject(jsonStringScript); //заполение объекта сценария
                int masAchSize = jsonObjectScript.getInt("AchCount");
                SaveStruct save = new SaveStruct(masAchSize, jsonObjectScript);
                JSONObject saveObject = FileWork.makeJsonSaveObject(save);
                FileWork.writeSaveFile(saveObject.toString(), this);
            }
        }
        catch(Exception e)  {
            e.printStackTrace();
        }
    }

    public void onNewGameClick(View view){
        try{
            String jsonStringScript = FileWork.readScript(this);
            JSONObject jsonObjectScript = new JSONObject(jsonStringScript); //заполение объекта сценария
            int masAchSize = jsonObjectScript.getInt("AchCount");
            SaveStruct save = new SaveStruct(masAchSize, jsonObjectScript);
            JSONObject saveObject = FileWork.makeJsonSaveObject(save);
            FileWork.writeSaveFile(saveObject.toString(), this);
        }
        catch(Exception e)  {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void onContinueClick(View view){
        Intent intent = new Intent(this, ChapterMenuActivity.class);
        startActivity(intent);
    }
}

