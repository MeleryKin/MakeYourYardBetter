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

    public ScreenTypes(Context context, int[] componentCount) {
        layout = new LinearLayout(context);
        textViews = new TextView[componentCount[0]];
        for (int i=0;i<componentCount[0];i++){
            textViews[i] = new TextView(context);
        }
        buttons = new Button[componentCount[1]];
        for (int i=0;i<componentCount[1];i++){
            buttons[i] = new Button(context);
        }
        imageViews = new ImageView[componentCount[2]];
        for (int i=0;i<componentCount[2];i++){
            imageViews[i] = new ImageView(context);
        }
        videoViews = new VideoView[componentCount[3]];
        for (int i=0;i<componentCount[3];i++){
            videoViews[i] = new VideoView(context);
        }
    }

    public static class PrintText extends ScreenTypes {
        String text;

        public PrintText(Context context, int[] componentCount) {
            super(context, componentCount);
        }
    }
    public static class ImageSlide extends ScreenTypes {
        String text;
        int coordX;
        int coordY;

        public ImageSlide(Context context, int[] componentCount) {
            super(context, componentCount);
        }
    }
    public static class ImageScreen extends ScreenTypes {
        String text;
        int coord;

        public ImageScreen(Context context, int[] componentCount) {
            super(context, componentCount);
        }
    }
    public static class ImageWithButtons extends ScreenTypes {
        String text;
        int coord;

        public ImageWithButtons(Context context, int[] componentCount) {
            super(context, componentCount);
        }
    }
    public static class ImageWithText extends ScreenTypes {
        String text;
        int coord;

        public ImageWithText(Context context, int[] componentCount) {
            super(context, componentCount);
        }
    }
}
