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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class VideoFragment extends Fragment {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            final Context context = getActivity().getApplicationContext();
            final LinearLayout layout = new LinearLayout(context);
            layout.setBackgroundColor(Color.WHITE);
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(FileWork.newWidth(240), FileWork.newHeight(280));
            layout.setLayoutParams(p);
            layout.setOrientation(LinearLayout.VERTICAL);

            String stringConfig = FileWork.readConfig(getActivity());
            JSONObject jsonConfig = new JSONObject(stringConfig);
            final JSONObject inf = jsonConfig.getJSONObject("videoScreen");

            JSONArray videos = inf.getJSONArray("videos");
            MenuButton[] videoButton = new MenuButton[videos.length()];

            for (int i = 0; i < videos.length(); i++){
                videoButton[i] = new MenuButton(context);
                videoButton[i].text.setText(videos.getJSONObject(i).getString("title"));
                videoButton[i].text.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                videoButton[i].text.setAutoSizeTextTypeUniformWithConfiguration(1, 17,1, TypedValue.COMPLEX_UNIT_DIP);
                videoButton[i].text.setBackgroundColor(Color.GREEN);
                videoButton[i].image.setBackgroundColor(Color.MAGENTA);
                LinearLayout.LayoutParams pv = new LinearLayout.LayoutParams(FileWork.newWidth(inf.getInt("width")), FileWork.newHeight(inf.getInt("height")));
                pv.leftMargin = FileWork.newWidth(inf.getInt("left"));
                pv.topMargin = FileWork.newHeight(inf.getInt("distance"));
                videoButton[i].setParams(pv);
                layout.addView(videoButton[i].text);
                layout.addView(videoButton[i].image);
            }

            final ScrollView sv = new ScrollView(context);
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
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_video, container, false);
    }
}