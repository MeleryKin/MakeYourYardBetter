package com.example.makeyouryardbetter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class VideoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = getActivity().getApplicationContext();
        LinearLayout layout = new LinearLayout(context);
        layout.setBackgroundColor(Color.YELLOW);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(FileWork.newWidth(240), FileWork.newHeight(280));
        layout.setLayoutParams(p);
        layout.setOrientation(LinearLayout.VERTICAL);
        TextView[] text = new TextView[100];
        for (int i = 0; i < 100; i++){
            text[i] = new TextView(context);
            text[i].setText("Video");
            layout.addView(text[i]);
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
        //return inflater.inflate(R.layout.fragment_video, container, false);
    }
}