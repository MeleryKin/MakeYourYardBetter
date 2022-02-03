package com.example.makeyouryardbetter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainScreenFragment extends Fragment {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            final Context context = getActivity().getApplicationContext();
            LinearLayout layout = new LinearLayout(context);
            layout.setBackgroundColor(Color.WHITE);
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(FileWork.newWidth(240), FileWork.newHeight(280));
            p.leftMargin = 0;
            p.topMargin = FileWork.newHeight(-320);
            layout.setLayoutParams(p);
            layout.setOrientation(LinearLayout.VERTICAL);

            String stringConfig = FileWork.readConfig(getActivity());
            JSONObject jsonConfig = new JSONObject(stringConfig);
            JSONObject inf = jsonConfig.getJSONObject("mainScreen");

            int lastCoord = 0;

            MenuButton newGame = new MenuButton(context);
            JSONObject butParam = inf.getJSONObject("newGameButton");
            p = new LinearLayout.LayoutParams(FileWork.newWidth(butParam.getInt("width")), FileWork.newHeight(butParam.getInt("height")));
            p.topMargin = FileWork.newHeight(butParam.getInt("top")) - lastCoord;
            p.leftMargin = FileWork.newWidth(butParam.getInt("left"));
            lastCoord = p.height + p.topMargin;
            newGame.setParams(p);
            newGame.text.setText(butParam.getString("text"));
            newGame.text.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            newGame.text.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            newGame.text.setAutoSizeTextTypeUniformWithConfiguration(1, 17,1, TypedValue.COMPLEX_UNIT_DIP);
            newGame.image.setBackgroundColor(context.getResources().getColor(R.color.baseGreen));

            View.OnClickListener newGameClick = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        String jsonStringScript = FileWork.readScript(getActivity());
                        JSONObject jsonObjectScript = new JSONObject(jsonStringScript); //заполение объекта сценария
                        int masAchSize = jsonObjectScript.getInt("AchCount");
                        SaveStruct save = new SaveStruct(masAchSize, jsonObjectScript);
                        JSONObject saveObject = FileWork.makeJsonSaveObject(save);
                        FileWork.writeSaveFile(saveObject.toString(), getActivity());
                        System.out.println(saveObject.toString());
                    }
                    catch(Exception e)  {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(getActivity(), GameActivity.class);
                    startActivity(intent);
                }
            };
            newGame.text.setOnClickListener(newGameClick);
            newGame.image.setOnClickListener(newGameClick);

            layout.addView(newGame.text);
            layout.addView(newGame.image);

            MenuButton cont = new MenuButton(context);
            butParam = inf.getJSONObject("continueButton");
            p = new LinearLayout.LayoutParams(FileWork.newWidth(butParam.getInt("width")), FileWork.newHeight(butParam.getInt("height")));
            p.topMargin = FileWork.newHeight(butParam.getInt("top")) - lastCoord;
            p.leftMargin = FileWork.newWidth(butParam.getInt("left"));
            lastCoord = p.height + p.topMargin;
            cont.setParams(p);
            cont.text.setText(butParam.getString("text"));
            cont.text.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            cont.text.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            cont.text.setAutoSizeTextTypeUniformWithConfiguration(1, 17,1, TypedValue.COMPLEX_UNIT_DIP);
            cont.image.setBackgroundColor(context.getResources().getColor(R.color.baseGreen));

            View.OnClickListener contClick = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ChapterMenuActivity.class);
                    startActivity(intent);
                }
            };
            cont.text.setOnClickListener(contClick);
            cont.image.setOnClickListener(contClick);

            layout.addView(cont.text);
            layout.addView(cont.image);


            return layout;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}

