package com.example.makeyouryardbetter;

public abstract class ScreenTypes {
    long curID;
    String screenType;
    long nextID;

    public static class PrintText extends ScreenTypes {
        String text;
    }
    public static class ImageSlide extends ScreenTypes {
        String text;
        int coordX;
        int coordY;
    }
    public static class ImageScreen extends ScreenTypes {
        String text;
        int coord;
    }
    public static class ImageWithButtons extends ScreenTypes {
        String text;
        int coord;
    }
    public static class ImageWithText extends ScreenTypes {
        String text;
        int coord;
    }
}
