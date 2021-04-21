package com.example.makeyouryardbetter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;


public class MainActivity extends AppCompatActivity {

    LinearLayout baseLayout;
    LinearLayout menuLayout;
    LinearLayout[] screensLayout;

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
        menuLayout = new LinearLayout(this);
        LinearLayout.LayoutParams menuParam = new LinearLayout.LayoutParams(FileWork.newWidth(240), FileWork.newHeight(40));
        menuParam.leftMargin = FileWork.newWidth(0);
        menuParam.topMargin = FileWork.newHeight(280);
        menuLayout.setBackgroundColor(getResources().getColor(R.color.purple));
        menuLayout.setLayoutParams(menuParam);

        menuLayout.setOrientation(LinearLayout.VERTICAL);
        baseLayout.setOrientation(LinearLayout.VERTICAL);

        ImageView[] menuButton = new ImageView[5];
        int baseCoord = 5;
        int lastCoord = 0;
        for (int i = 0; i < 5; i++){
            menuButton[i] = new ImageView(this);
            String mDrawableName = "m" + (i + 1);
            int resID = getResources().getIdentifier(mDrawableName, "drawable", getPackageName());
            menuButton[i].setImageResource(resID);
          //  menuButton[i].setBackgroundColor(getResources().getColor(R.color.black));
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(FileWork.newWidth(30), FileWork.newHeight(30));
            p.topMargin = FileWork.newHeight(5-lastCoord);
            lastCoord = 35;
            p.leftMargin = FileWork.newWidth(baseCoord);
            baseCoord += 50;
            menuButton[i].setLayoutParams(p);
            menuLayout.addView(menuButton[i]);
        }

        Button button = new Button(this);
        LinearLayout.LayoutParams bp = new LinearLayout.LayoutParams(FileWork.newWidth(100), FileWork.newHeight(60));
        bp.topMargin = FileWork.newHeight(20);
        bp.leftMargin = FileWork.newWidth(20);
        button.setLayoutParams(bp);
        button.setText("AAAAA");

        LinearLayout l1 = new LinearLayout(this);
        bp = new LinearLayout.LayoutParams(FileWork.newWidth(240), FileWork.newHeight(280));
        bp.leftMargin = FileWork.newWidth(0);
        bp.topMargin = FileWork.newHeight(0);
        l1.setLayoutParams(bp);
       // l1.addView(button);

        baseLayout.addView(menuLayout);
        baseLayout.addView(button);

        baseLayout.getViewTreeObserver();

        //    baseLayout.addView(l1);
        setContentView(baseLayout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        /*LinearLayout main = new LinearLayout(this);
        main.setBackgroundColor(getResources().getColor(R.color.purple));

        LinearLayout l1 = new LinearLayout(this);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(FileWork.newWidth(100), FileWork.newHeight(60));
        p.topMargin = FileWork.newHeight(20);
        p.leftMargin = FileWork.newWidth(60);
        l1.setLayoutParams(p);
        l1.setBackgroundColor(getResources().getColor(R.color.black));
        main.addView(l1);

        setContentView(main, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));*/

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
            /*LinearLayout mainLayout = new LinearLayout(this);
            mainLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(FileWork.newWidth(60), FileWork.newHeight(30));
            String stringConfig = FileWork.readConfig(this);
            JSONObject jsonConfig = new JSONObject(stringConfig);
            JSONArray jsonGameTypes = jsonConfig.getJSONArray("gameScreenTypes");

            LinearLayout l1 = new LinearLayout(this);
            mainLayout.addView(l1);*/
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
            System.out.println(saveObject.toString());
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

