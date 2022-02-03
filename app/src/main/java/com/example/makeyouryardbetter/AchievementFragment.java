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


            String stringConfig = FileWork.readConfig(getActivity());
            JSONObject jsonConfig = new JSONObject(stringConfig);
            final JSONObject inf = jsonConfig.getJSONObject("achievementScreen");

            int distance = inf.getInt("distance");
            JSONArray ach = inf.getJSONArray("achievements");
            TextView[] ta = new TextView[ach.length()];

            int top = 0;
            int coord = distance;

            for (int i = 0; i < ach.length(); i++){
                ta[i] = new TextView(context);
                ta[i].setText(ach.getJSONObject(i).getString("title"));
                ta[i].setBackgroundColor(context.getResources().getColor(R.color.primGreen));
                LinearLayout.LayoutParams tp = new LinearLayout.LayoutParams(FileWork.newWidth(inf.getInt("width")), FileWork.newHeight(inf.getInt("height")));
                tp.topMargin = FileWork.newHeight(coord - top);
                if ((i+1) % 2 == 0) {
                    tp.leftMargin = FileWork.newWidth(30 + inf.getInt("width"));
                }
                else{
                    tp.leftMargin = FileWork.newWidth(10);
                }
                ta[i].setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                ta[i].setAutoSizeTextTypeUniformWithConfiguration(1, 14,1, TypedValue.COMPLEX_UNIT_DIP);
                ta[i].setLayoutParams(tp);
                layout.addView(ta[i]);
                top = ((i+1)/2+(i+1)%2) * (inf.getInt("height") + distance);
                if (i % 2 == 1) {
                    coord += inf.getInt("height") + distance;
                }
            }
            ScrollView sv = new ScrollView(context);
            sv.setBackgroundColor(Color.WHITE);
            LinearLayout.LayoutParams sp = new LinearLayout.LayoutParams(FileWork.newWidth(240), FileWork.newHeight(280));
            sp.leftMargin = 0;
            sp.topMargin = FileWork.newHeight(-320);
            sv.setLayoutParams(sp);
            sv.addView(layout);
            sv.setVerticalScrollBarEnabled(false);
            sv.setHorizontalScrollBarEnabled(false);
            sv.setOverScrollMode(View.OVER_SCROLL_NEVER);
            return sv;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}