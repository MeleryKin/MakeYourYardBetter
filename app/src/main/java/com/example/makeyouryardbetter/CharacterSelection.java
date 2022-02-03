package com.example.makeyouryardbetter;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.core.view.GestureDetectorCompat;

public class CharacterSelection {
    public static ImageView[] characters;

    private GestureDetectorCompat lSwipeDetector;

    private static final int SWIPE_MIN_DISTANCE = 50;
    private static final int SWIPE_MAX_DISTANCE = 300;
    private static final int SWIPE_MIN_VELOCITY = 50;

    CharacterSelection(Context context, int chCount){
        lSwipeDetector = new GestureDetectorCompat(context, new MyGestureListener());
        characters = new ImageView[chCount];
        for (int i = 0; i < chCount; i++){
            characters[i] = new ImageView(context);
            characters[i].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return lSwipeDetector.onTouchEvent(event);
                }
            });
        }
    }

    private static class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_DISTANCE)
                return false;
            if (Math.abs(e2.getX() - e1.getX()) > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_MIN_VELOCITY) {
                System.out.println("Swipe");
                if (e2.getX() - e1.getX() < 0){
                    GameActivity.save.curCharacter = (GameActivity.save.curCharacter + 1) % characters.length;
                }
                else {
                    GameActivity.save.curCharacter = (GameActivity.save.curCharacter + characters.length - 1) % characters.length;
                }
            }
            return false;
        }
    }
}
