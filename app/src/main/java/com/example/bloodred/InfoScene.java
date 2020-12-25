package com.example.bloodred;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import androidx.core.content.ContextCompat;

import com.example.bloodred.gameobject.Sprite;
import com.example.bloodred.gamepanel.ResumeButton;


public class InfoScene {


    private final SceneManager sceneManager;
    private Bitmap bitmap;
    private final ResumeButton resumeButton;
    private final Context context;
    private boolean active = false;

    public InfoScene(Bitmap res, Context context, int mWidth, int mHeight, SceneManager sceneManager) {
        this.context = context;
        this.sceneManager = sceneManager;

        bitmap = res;
        resumeButton = new ResumeButton(context, mWidth - 120, mHeight - 120, 0.4f);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive() {
        active = true;
    }

    public void setInactive() {
        active = false;
    }

    public void draw(Canvas canvas) {
        Rect dstRect = new Rect();
        canvas.getClipBounds(dstRect);
        canvas.drawBitmap(bitmap, null, dstRect, null);
        resumeButton.draw(canvas);

        switch (sceneManager.getCurrentGameStage()) {
            case 0:

                break;

            case 1:
                String text = context.getResources().getString(R.string.Stage2);
                bitmap = drawMultilineTextToBitmap(context, R.drawable.menubg, text);
                break;

            case 2:

                break;
        }

    }

    public void clickEvents(double x, double y) {
        if (Sprite.isClicked(resumeButton, x, y)) {
            sceneManager.drawGameScene();
        }
    }
    public Bitmap drawMultilineTextToBitmap(Context gContext,
                                            int gResId,
                                            String gText) {

        // prepare canvas
        Resources resources = gContext.getResources();
        float scale = resources.getDisplayMetrics().density;
        Bitmap bitmap = BitmapFactory.decodeResource(resources, gResId);

        android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();
        // set default bitmap config if none
        if(bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);

        // new antialiased Paint
        TextPaint paint=new TextPaint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        paint.setColor(Color.rgb(255,255,255));
        // text size in pixels
        paint.setTextSize((int) (30 * scale));
        // text shadow
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

        // set text width to canvas width minus 16dp padding
        int textWidth = canvas.getWidth() - (int) (16 * scale);

        // init StaticLayout for text
        StaticLayout textLayout = new StaticLayout(
                gText, paint, textWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);

        // get height of multiline text
        int textHeight = textLayout.getHeight();

        // get position of text's top left corner
        float x = (bitmap.getWidth() - textWidth)/2;
        float y = (bitmap.getHeight() - textHeight)/2;

        // draw text to the Canvas center
        canvas.save();
        canvas.translate(x, y);
        textLayout.draw(canvas);
        canvas.restore();

        return bitmap;
    }

}
