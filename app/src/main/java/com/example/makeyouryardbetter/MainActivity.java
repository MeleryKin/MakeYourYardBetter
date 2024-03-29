package com.example.makeyouryardbetter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;


public class MainActivity extends AppCompatActivity {

    public static LinearLayout baseLayout;
    int idBaseLayout = 23948;
    public static int currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        GameActivity.widthScreen = size.x;
        GameActivity.heightScreen = size.y;

        baseLayout = new LinearLayout(this);

        baseLayout.setOrientation(LinearLayout.VERTICAL);
        baseLayout.setId(idBaseLayout);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment mainScreenFragment = new MainScreenFragment();
        Fragment menuFragment = new AppMenuFragment();

        ft.add(baseLayout.getId(), menuFragment, "menu");
        ft.add(baseLayout.getId(), mainScreenFragment, "Cur");

        ft.commit();
        currentFragment = mainScreenFragment.getId();

        LinearLayout l1 = new LinearLayout(this);
        LinearLayout.LayoutParams bp = new LinearLayout.LayoutParams(FileWork.newWidth(240), FileWork.newHeight(280));
        bp.leftMargin = FileWork.newWidth(0);
        bp.topMargin = FileWork.newHeight(0);
        l1.setLayoutParams(bp);
       // l1.addView(button);

        setContentView(baseLayout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        File file = new File(this.getFilesDir(), "save.json");
        try {
            String jsonStringScript = FileWork.readScript(this); // чтение скрипта
            if (!(file.exists())) {
                JSONObject jsonObjectScript = new JSONObject(jsonStringScript); //заполение объекта сценария
                int masAchSize = jsonObjectScript.getInt("AchCount"); // массив достижений
                SaveStruct save = new SaveStruct(masAchSize, jsonObjectScript); // пустое сохранение
                JSONObject saveObject = FileWork.makeJsonSaveObject(save);
                FileWork.writeSaveFile(saveObject.toString(), this); //создание файла сохранения
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

            File file = new File(this.getFilesDir(), "save.json");
            if (file.exists()){
                //todo: if save file is already existed then save achievement array
            }

            SaveStruct save = new SaveStruct(masAchSize, jsonObjectScript);
            JSONObject saveObject = FileWork.makeJsonSaveObject(save);
            FileWork.writeSaveFile(saveObject.toString(), this);
        }
        catch(Exception e) {
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

