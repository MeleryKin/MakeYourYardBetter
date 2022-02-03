package com.example.makeyouryardbetter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
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
import android.widget.VideoView;

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

            final ScrollView sv = new ScrollView(context);

            String stringConfig = FileWork.readConfig(getActivity());
            JSONObject jsonConfig = new JSONObject(stringConfig);
            final JSONObject inf = jsonConfig.getJSONObject("videoScreen");

            final JSONArray videos = inf.getJSONArray("videos");
            MenuButton[] videoButton = new MenuButton[videos.length()];

            for (int i = 0; i < videos.length(); i++){
                final int finalI = i;
                View.OnClickListener click = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            System.out.println("AAAAAAAAAAAAA");
                            String videoName = videos.getJSONObject(finalI).getString("videoName");
                            layout.removeAllViews();
                            layout.setBackgroundColor(context.getResources().getColor(R.color.black));
                            sv.setBackgroundColor(context.getResources().getColor(R.color.black));
                            LinearLayout.LayoutParams psv = new LinearLayout.LayoutParams(FileWork.newWidth(240), FileWork.newHeight(280));
                            psv.leftMargin = FileWork.newWidth(0);
                            psv.topMargin = FileWork.newHeight(-320);
                            sv.setLayoutParams(psv);

                            VideoView video = new VideoView(context);
                            Uri myVideoUri= Uri.parse( "android.resource://" + context.getPackageName() + "/" + R.raw.video1);
                            video.setVideoURI(myVideoUri);

                            LinearLayout.LayoutParams vp = new LinearLayout.LayoutParams(FileWork.newWidth(240), FileWork.newHeight(280));
                            vp.leftMargin = FileWork.newWidth(5);
                            vp.topMargin = FileWork.newHeight(0);
                            video.setLayoutParams(vp);
                            layout.addView(video);

                            video.start();
                        }
                        catch (Exception e){
                            e.printStackTrace();;
                        }
                    }
                };
                videoButton[i] = new MenuButton(context);
                videoButton[i].image.setOnClickListener(click);
                videoButton[i].text.setOnClickListener(click);
                videoButton[i].text.setText(videos.getJSONObject(i).getString("title"));
                videoButton[i].text.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                videoButton[i].text.setAutoSizeTextTypeUniformWithConfiguration(1, 17,1, TypedValue.COMPLEX_UNIT_DIP);
                videoButton[i].text.setBackgroundColor(context.getResources().getColor(R.color.primGreen));
                videoButton[i].image.setBackgroundColor(context.getResources().getColor(R.color.baseGreen));
                LinearLayout.LayoutParams pv = new LinearLayout.LayoutParams(FileWork.newWidth(inf.getInt("width")), FileWork.newHeight(inf.getInt("height")));
                pv.leftMargin = FileWork.newWidth(inf.getInt("left"));
                pv.topMargin = FileWork.newHeight(inf.getInt("distance"));
                videoButton[i].setParams(pv);
                layout.addView(videoButton[i].text);
                layout.addView(videoButton[i].image);
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
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_video, container, false);
    }
}