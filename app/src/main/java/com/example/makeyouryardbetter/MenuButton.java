package com.example.makeyouryardbetter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuButton {
    TextView text;
    ImageView image;

    public MenuButton(Context context){
        text = new TextView(context);
        image = new ImageView(context);
    }

    public void setParams(LinearLayout.LayoutParams p){
        p.height /= 3;
        this.text.setLayoutParams(p);
        LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(p.width, p.height);
        p1.topMargin = 0;
        p1.height = p.height * 2;
        p1.leftMargin = p.leftMargin;
        this.image.setLayoutParams(p1);
    }
}
