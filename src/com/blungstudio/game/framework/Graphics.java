package com.blungstudio.game.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public interface Graphics {
    public static enum PixmapFormat {
        ARGB8888, ARGB4444, RGB565
    }

    public Pixmap newPixmap(String fileName, PixmapFormat format);

    public Pixmap newPixmap(Bitmap bitmap);

    public void clear(int color);

    public void drawPixel(int x, int y, int color);

    public void drawLine(int x, int y, int x2, int y2, int color);

    public void drawRect(int x, int y, int width, int height, int color);

    public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY,
            int srcWidth, int srcHeight);

    public void drawPixmap(Pixmap pixmap, int x, int y);
    
    public void drawPixmap(Pixmap pixmap, int x, int y ,float sx, float sy,float degrees,float px, float py);
    
    public void drawPixel(int x, int y, int color, Paint paint);

    public void drawLine(int x, int y, int x2, int y2, int color, Paint paint);

    public void drawRect(int x, int y, int width, int height, int color, Paint paint);

    public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY,
            int srcWidth, int srcHeight, Paint paint);

    public void drawPixmap(Pixmap pixmap, int x, int y, Paint paint);
    
    public void drawPixmap(Pixmap pixmap);
    
    public void drawPixmap(Pixmap pixmap, Paint paint);
    
    public void drawText(String text, int x, int y, Paint paint);

    public int getWidth();

    public int getHeight();
    
    public Canvas getCanvas();
}
