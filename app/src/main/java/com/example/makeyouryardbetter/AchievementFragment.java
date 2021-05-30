package com.example.makeyouryardbetter;

import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class AchievementFragment extends Fragment {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            Context context = getActivity().getApplicationContext();
            LinearLayout layout = new LinearLayout(context);
            layout.setBackgroundColor(Color.WHITE);
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(FileWork.newWidth(240), FileWork.newHeight(280));
            layout.setLayoutParams(p);
            layout.setOrientation(LinearLayout.VERTICAL);

            int top = 0;
            String stringConfig = FileWork.readConfig(getActivity());
            JSONObject jsonConfig = new JSONObject(stringConfig);
            final JSONObject inf = jsonConfig.getJSONObject("achievementScreen");

            int distance = inf.getInt("distance");
            JSONArray ach = inf.getJSONArray("achievements");
            TextView[] ta = new TextView[ach.length()];

            for (int i = 0; i < ach.length(); i += 2){
                ta[i] = new TextView(context);
                ta[i].setText(ach.getJSONObject(i).getString("title"));
                ta[i].setBackgroundColor(Color.GREEN);
                LinearLayout.LayoutParams tp = new LinearLayout.LayoutParams(FileWork.newWidth(inf.getInt("width")), FileWork.newHeight(inf.getInt("height")));
                tp.topMargin = FileWork.newHeight(distance);
                tp.leftMargin = FileWork.newWidth(10);
                ta[i].setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                ta[i].setAutoSizeTextTypeUniformWithConfiguration(1, 14,1, TypedValue.COMPLEX_UNIT_DIP);
                ta[i].setLayoutParams(tp);
                layout.addView(ta[i]);
                top += inf.getInt("height") + distance;
            }
            for (int i = 1; i < ach.length(); i += 2){
                ta[i] = new TextView(context);
                ta[i].setText(ach.getJSONObject(i).getString("title"));
                ta[i].setBackgroundColor(Color.GREEN);
                LinearLayout.LayoutParams tp = new LinearLayout.LayoutParams(FileWork.newWidth(inf.getInt("width")), FileWork.newHeight(inf.getInt("height")));
                tp.topMargin = FileWork.newHeight(distance - top);
                tp.leftMargin = FileWork.newWidth(30 + inf.getInt("width"));
                ta[i].setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                ta[i].setAutoSizeTextTypeUniformWithConfiguration(1, 14,1, TypedValue.COMPLEX_UNIT_DIP);
                ta[i].setLayoutParams(tp);
                layout.addView(ta[i]);
                top = 0;
            }
            ScrollView sv = new ScrollView(context);
            LinearLayout.LayoutParams sp = new LinearLayout.LayoutParams(FileWork.newWidth(240), FileWork.newHeight(280));
            sp.leftMargin = 0;
            sp.topMargin = FileWork.newHeight(-320);
            sv.setLayoutParams(sp);
            sv.addView(layout);
            sv.setVerticalScrollBarEnabled(false);
            sv.setHorizontalScrollBarEnabled(false);
            sv.setOverScrollMode(View.OVER_SCROLL_NEVER);
            return sv;
            // Inflate the layout for this fragment
            //return inflater.inflate(R.layout.fragment_achievement, container, false);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}