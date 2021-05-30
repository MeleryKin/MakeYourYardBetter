package com.example.makeyouryardbetter;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONObject;

public class AppMenuFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            final Context context = getActivity().getApplicationContext();
            LinearLayout l = new LinearLayout(context);

            String stringConfig = FileWork.readConfig(getActivity());
            JSONObject jsonConfig = new JSONObject(stringConfig);
            JSONObject inf = jsonConfig.getJSONObject("menuPanel");

            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(FileWork.newWidth(inf.getInt("width")), FileWork.newHeight(inf.getInt("height")));
            p.topMargin = FileWork.newHeight(inf.getInt("top"));
            p.leftMargin = FileWork.newWidth(inf.getInt("left"));
            l.setOrientation(LinearLayout.VERTICAL);
            l.setLayoutParams(p);
            int countButtons = inf.getInt("countButtons");
            ImageView[] menuButton = new ImageView[countButtons];

            l.setBackgroundColor(getResources().getColor(R.color.white));

            JSONArray picInfArr = inf.getJSONArray("images");
            int lastCoord = 0;
            for (int i = 0; i < countButtons; i++) {
                JSONObject picInf = picInfArr.getJSONObject(i);

                menuButton[i] = new ImageView(context);
                String mDrawableName = picInf.getString("imageName");
                int resID = getResources().getIdentifier(mDrawableName, "drawable", context.getPackageName());
                menuButton[i].setImageResource(resID);
                p = new LinearLayout.LayoutParams(FileWork.newWidth(picInf.getInt("width")), FileWork.newHeight(picInf.getInt("height")));
                int tp = picInf.getInt("top");
                p.topMargin = FileWork.newHeight(tp - lastCoord);
                lastCoord = tp + picInf.getInt("width");
                p.leftMargin = FileWork.newWidth(picInf.getInt("left"));
                menuButton[i].setLayoutParams(p);
                l.addView(menuButton[i]);
            }

            menuButton[0].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Fragment mainScreenFragment = new MainScreenFragment();
                    ft.remove(fm.findFragmentByTag("Cur"));
                    ft.add(MainActivity.baseLayout.getId(), mainScreenFragment, "Cur");
                    MainActivity.currentFragment = mainScreenFragment.getId();
                    ft.commit();
                }
            });
            menuButton[1].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Fragment mainScreenFragment = new MemoFragment();
                    ft.remove(fm.findFragmentByTag("Cur"));
                    ft.add(MainActivity.baseLayout.getId(), mainScreenFragment, "Cur");
                    MainActivity.currentFragment = mainScreenFragment.getId();
                    ft.commit();
                }
            });
            menuButton[2].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Fragment mainScreenFragment = new AchievementFragment();
                    ft.remove(fm.findFragmentByTag("Cur"));
                    ft.add(MainActivity.baseLayout.getId(), mainScreenFragment, "Cur");
                    MainActivity.currentFragment = mainScreenFragment.getId();
                    ft.commit();
                }
            });
            menuButton[3].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Fragment mainScreenFragment = new VideoFragment();
                    ft.remove(fm.findFragmentByTag("Cur"));
                    ft.add(MainActivity.baseLayout.getId(), mainScreenFragment, "Cur");
                    MainActivity.currentFragment = mainScreenFragment.getId();
                    ft.commit();
                }
            });
        return l;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
       // return inflater.inflate(R.layout.fragment_app_menu, container, false);
    }
}