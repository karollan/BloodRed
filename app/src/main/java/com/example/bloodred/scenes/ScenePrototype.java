package com.example.bloodred.scenes;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import com.example.bloodred.gamebackground.Background;
/**
 * ScenePrototype is an abstract class which is the foundation of all scenes in game
 *
 **/
public abstract class ScenePrototype {

    //Bitmap and context for creating background
    protected Bitmap bitmap;
    protected Context context;

    //User phone resolution
    protected int mWidth;
    protected int mHeight;

    //Special class that manages all scenes
    protected SceneManager sceneManager;

    //Every scene have background set on creation
    protected Background background;

    //Scene is shown or not
    private boolean active;

    public ScenePrototype(Bitmap res, Context context, int mWidth, int mHeight, SceneManager sceneManager) {
        this.bitmap = res;
        this.context = context;
        this.mWidth = mWidth;
        this.mHeight = mHeight;
        this.sceneManager = sceneManager;
        background = new Background(bitmap);
    }

    //Scene managing by changing its active status
    public boolean isActive() {
        return active;
    }

    public void setActive() {
        active = true;
    }

    public void setInactive() {
        active = false;
    }

    public abstract void drawScene(Canvas canvas);
    public abstract void update();

    //Method to add multiline text to background, text is drawn on background image and then rendered
    protected Bitmap drawMultilineTextToBitmap(Context gContext, int gResId, String gText) {
        // Prepare canvas
        Resources resources = gContext.getResources();
        float scale = resources.getDisplayMetrics().density;
        Bitmap bitmap = BitmapFactory.decodeResource(resources, gResId);

        android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();
        // Set default bitmap config if none
        if(bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        // Resource bitmaps are imutable,
        // So we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);

        // New antialiased Paint
        TextPaint paint=new TextPaint(Paint.ANTI_ALIAS_FLAG);
        // Text color - #3D3D3D
        paint.setColor(Color.rgb(255,255,255));
        // Text size in pixels
        paint.setTextSize((int) (30 * scale));
        // Text shadow
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

        // Set text width to canvas width minus 100dp padding
        int textWidth = canvas.getWidth() - (int) (100 * scale);

        // Init StaticLayout for text
        StaticLayout textLayout = new StaticLayout(
                gText, paint, textWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);

        // Get height of multiline text
        int textHeight = textLayout.getHeight();

        // Get position of text's top left corner
        float x = (bitmap.getWidth() - textWidth)/2;
        float y = (bitmap.getHeight() - textHeight)/3;

        // Draw text to the Canvas center
        canvas.save();
        canvas.translate(x, y);
        textLayout.draw(canvas);
        canvas.restore();

        return bitmap;
    }

}
