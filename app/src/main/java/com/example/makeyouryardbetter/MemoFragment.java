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

import java.io.File;

public class MemoFragment extends Fragment {

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
            final JSONObject inf = jsonConfig.getJSONObject("memoScreen");

            int top = inf.getInt("distance");
            int left = inf.getInt("left");
            final JSONArray articleArray = inf.getJSONArray("articles");
            final int iconCount = inf.getInt("iconCount");
            final ImageView[] icons = new ImageView[iconCount];
            TextView[] art = new TextView[articleArray.length()];
            final ScrollView sv = new ScrollView(context);
            for (int i = 0; i < articleArray.length(); i++){
                LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(FileWork.newWidth(inf.getInt("width")), FileWork.newHeight(inf.getInt("height")));
                art[i] = new TextView(context);
                pm.topMargin = FileWork.newHeight(top);
                pm.leftMargin = FileWork.newWidth(left);
                art[i].setLayoutParams(pm);
                art[i].setBackgroundColor(Color.MAGENTA);
                art[i].setText(articleArray.getJSONObject(i).getString("title"));
                art[i].setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                art[i].setAutoSizeTextTypeUniformWithConfiguration(1, 17,1, TypedValue.COMPLEX_UNIT_DIP);
                layout.addView(art[i]);
                final int finalI = i;
                art[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            JSONArray text = articleArray.getJSONObject(finalI).getJSONArray("content");
                            //sv.removeAllViews();
                            layout.removeAllViews();
                            layout.setBackgroundColor(Color.MAGENTA);
                            sv.setBackgroundColor(Color.MAGENTA);
                            LinearLayout.LayoutParams psv = new LinearLayout.LayoutParams(FileWork.newWidth(200), FileWork.newHeight(240));
                            psv.leftMargin = FileWork.newWidth(20);
                            psv.topMargin = FileWork.newHeight(-300);
                            sv.setLayoutParams(psv);
                            TextView[] artText = new TextView[text.length()];
                            String mDrawableName = inf.getString("iconImageName");
                            int resID = context.getResources().getIdentifier(mDrawableName, "drawable", context.getPackageName());
                            int x = 0;
                            for (int i = 0; i < text.length(); i++){
                                artText[i] = new TextView(context);
                                artText[i].setBackgroundColor(Color.MAGENTA);
                                if (text.getJSONObject(i).getString("status").compareTo("point") == 0){
                                    icons[x] = new ImageView(context);
                                //    icons[x].setBackgroundColor(Color.GREEN);
                                    icons[x].setImageResource(resID);
                                    int size = inf.getInt("iconSize");
                                    LinearLayout.LayoutParams pImg = new LinearLayout.LayoutParams(FileWork.newWidth(size), FileWork.newHeight(size));
                                    pImg.topMargin = FileWork.newHeight(20);
                                    pImg.leftMargin = FileWork.newWidth(40);
                                    icons[x].setLayoutParams(pImg);
                                    LinearLayout.LayoutParams pArt = new LinearLayout.LayoutParams(FileWork.newWidth(100), FileWork.newHeight(20));
                                    pArt.topMargin = FileWork.newHeight(-size);
                                    pArt.leftMargin = FileWork.newWidth(40 + size + 20);
                                    artText[i].setLayoutParams(pArt);
                                    artText[i].setAutoSizeTextTypeUniformWithConfiguration(5, 17, 1, TypedValue.COMPLEX_UNIT_DIP);
                                    artText[i].setText(text.getJSONObject(i).getString("text"));
                                    layout.addView(icons[x]);
                                    layout.addView(artText[i]);
                                    x++;
                                }
                                else {
                                    LinearLayout.LayoutParams pArt = new LinearLayout.LayoutParams(FileWork.newWidth(100), FileWork.newHeight(20));
                                    pArt.topMargin = FileWork.newHeight(20);
                                    pArt.leftMargin = FileWork.newWidth(40);
                                    artText[i].setLayoutParams(pArt);
                                    // artText[i].setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                                    artText[i].setAutoSizeTextTypeUniformWithConfiguration(5, 17, 1, TypedValue.COMPLEX_UNIT_DIP);
                                    artText[i].setText(text.getJSONObject(i).getString("text"));
                                    layout.addView(artText[i]);
                                }
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }

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
            // return inflater.inflate(R.layout.fragment_memo, container, false);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}