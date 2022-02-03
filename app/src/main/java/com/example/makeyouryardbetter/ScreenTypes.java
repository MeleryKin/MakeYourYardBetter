package com.example.makeyouryardbetter;

import android.content.Context;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

public class ScreenTypes {

    LinearLayout layout;
    TextView[] textViews;
    Button[] buttons;
    ImageView[] imageViews;
    VideoView[] videoViews;
    CharacterSelection[] charSel;

    public ScreenTypes(Context context, int[] componentCount) {
        layout = new LinearLayout(context);

        textViews = new TextView[componentCount[4]];
        for (int i = 0; i < componentCount[4]; i++){
            textViews[i] = new TextView(context);
        }
        buttons = new Button[componentCount[3]];
        for (int i = 0; i < componentCount[3]; i++){
            buttons[i] = new Button(context);
        }
        imageViews = new ImageView[componentCount[2]];
        for (int i = 0; i < componentCount[2]; i++){
            imageViews[i] = new ImageView(context);
        }
        videoViews = new VideoView[componentCount[1]];
        for (int i = 0; i < componentCount[1]; i++){
            videoViews[i] = new VideoView(context);
        }
        if (componentCount[0] > 0){
            charSel = new CharacterSelection[1];
            charSel[0] = new CharacterSelection(context, componentCount[0]);
        }
        else {
            charSel = new CharacterSelection[0];
        }
    }
}
