package com.blungstudio.game.framework.impl;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;

import com.blungstudio.game.framework.Graphics;
import com.blungstudio.game.framework.Pixmap;

public class AndroidGraphics implements Graphics {
    AssetManager assets;
    Bitmap frameBuffer;
    Canvas canvas;
    Paint paint;
    Rect srcRect = new Rect();
    Rect dstRect = new Rect();
    Matrix transform = new Matrix();
    
    public AndroidGraphics(AssetManager assets, Bitmap frameBuffer) {
        this.assets = assets;
        this.frameBuffer = frameBuffer;
        this.canvas = new Canvas(frameBuffer);
        this.paint = new Paint();
    }

   
    public Pixmap newPixmap(String fileName, PixmapFormat format) {
        Config config = null;
        if (format == PixmapFormat.RGB565)
            config = Config.RGB_565;
        else if (format == PixmapFormat.ARGB4444)
            config = Config.ARGB_4444;
        else
            config = Config.ARGB_8888;

        Options options = new Options();
        options.inPreferredConfig = config;

        InputStream in = null;
        Bitmap bitmap = null;
        try {
            in = assets.open(fileName);
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in,null,options);
            options.inSampleSize = 1;
            options.inJustDecodeBounds = false;
            options.inTempStorage = new byte[32*1024];
            options.inDither=false;                     
            options.inPurgeable=true;                   
            options.inInputShareable=true;  
            bitmap = BitmapFactory.decodeStream(in, null, options);
            if (bitmap == null)
                throw new RuntimeException("Couldn't load bitmap from asset '"
                        + fileName + "'");
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load bitmap from asset '"
                    + fileName + "'");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }

        if (bitmap.getConfig() == Config.RGB_565)
            format = PixmapFormat.RGB565;
        else if (bitmap.getConfig() == Config.ARGB_4444)
            format = PixmapFormat.ARGB4444;
        else
            format = PixmapFormat.ARGB8888;

        return new AndroidPixmap(bitmap, format);
    }

    
    public void clear(int color) {
        canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8,
                (color & 0xff));
    }

    
    public void drawPixel(int x, int y, int color) {
        paint.setColor(color);
        canvas.drawPoint(x, y, paint);
    }

    
    public void drawLine(int x, int y, int x2, int y2, int color) {
        paint.setColor(color);
        canvas.drawLine(x, y, x2, y2, paint);
    }

   
    public void drawRect(int x, int y, int width, int height, int color) {
        paint.setColor(color);
        paint.setStyle(Style.FILL);
        canvas.drawRect(x, y, x + width - 1, y + width - 1, paint);
    }

    
    public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY,
            int srcWidth, int srcHeight) {
        srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth - 1;
        srcRect.bottom = srcY + srcHeight - 1;

        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + srcWidth - 1;
        dstRect.bottom = y + srcHeight - 1;

        if(!pixmap.isDisposed())
        	canvas.drawBitmap(((AndroidPixmap) pixmap).bitmap, srcRect, dstRect,
                null);
    }
    
    
    public void drawPixmap(Pixmap pixmap, int x, int y) {
    	if(!pixmap.isDisposed())
        	 canvas.drawBitmap(((AndroidPixmap)pixmap).bitmap, x, y, null);
    }
    
    
    public void drawPixel(int x, int y, int color, Paint paint) {
        canvas.drawPoint(x, y, paint);
    }

    
    public void drawLine(int x, int y, int x2, int y2, int color, Paint paint) {
        canvas.drawLine(x, y, x2, y2, paint);
    }

    
    public void drawRect(int x, int y, int width, int height, int color, Paint paint) {
        canvas.drawRect(x, y, x + width - 1, y + width - 1, paint);
    }

    
    public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY,
            int srcWidth, int srcHeight, Paint paint) {
        srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth - 1;
        srcRect.bottom = srcY + srcHeight - 1;

        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + srcWidth - 1;
        dstRect.bottom = y + srcHeight - 1;

        if(!pixmap.isDisposed())
        	 canvas.drawBitmap(((AndroidPixmap) pixmap).bitmap, srcRect, dstRect,
                paint);
    }
    
   
    public void drawPixmap(Pixmap pixmap, int x, int y, Paint paint) {
    	if(!pixmap.isDisposed())
        	 canvas.drawBitmap(((AndroidPixmap)pixmap).bitmap, x, y, paint);
    }

    
    public int getWidth() {
        return frameBuffer.getWidth();
    }

    
    public int getHeight() {
        return frameBuffer.getHeight();
    }

	
	public Pixmap newPixmap(Bitmap id) {
		frameBuffer = id;
		return new AndroidPixmap(frameBuffer);
	}

	
	public void drawText(String text, int x, int y, Paint paint) {
		canvas.drawText(text, x, y, paint);
	}


	@Override
	public void drawPixmap(Pixmap pixmap, int x, int y, float sx, float sy,
			float degrees, float px, float py) {
		transform.reset();
    	transform.preRotate(degrees, px+x,py+y);
    	transform.preScale(sx, sy);
    	transform.preTranslate(x,y);
    	if(!pixmap.isDisposed())
    	canvas.drawBitmap(((AndroidPixmap)pixmap).bitmap, transform, paint);
	}


	@Override
	public Canvas getCanvas() {
		return this.canvas;
	}


	@Override
	public void drawPixmap(Pixmap pixmap) {
		if(!pixmap.isDisposed())
	    	canvas.drawBitmap(((AndroidPixmap)pixmap).bitmap, pixmap.getMatrix(), pixmap.getPaint());
	}


	@Override
	public void drawPixmap(Pixmap pixmap, Paint paint) {
		if(!pixmap.isDisposed())
	    	canvas.drawBitmap(((AndroidPixmap)pixmap).bitmap, pixmap.getMatrix(), paint);
	}
}
