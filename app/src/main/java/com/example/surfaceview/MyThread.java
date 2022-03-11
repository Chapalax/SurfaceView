package com.example.surfaceview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class MyThread extends Thread {

    private Paint paint;
    // "держатель" SurfaceView
    private SurfaceHolder surfaceHolder;
    public boolean flag;

    MyThread(SurfaceHolder holder) {
        this.surfaceHolder = holder;
        this.flag = false;
        paint = new Paint();
        paint.setAntiAlias(true); // Сглаживание
        paint.setStyle(Paint.Style.FILL);
    }

    public long getTime() {
        return System.nanoTime()/1000;
    }

    long redrawTime = 0;

    @Override
    public void run() {
        Canvas canvas;
        while(flag) {
            long currentTime = getTime();
            long elapsedTime = currentTime - redrawTime;
            if(elapsedTime < 50000) {
                continue;
            }
            // Логика отрисовки...
            canvas = surfaceHolder.lockCanvas();
            drawCircle(canvas);
            surfaceHolder.unlockCanvasAndPost(canvas);
            redrawTime = getTime();
        }
    }

    public void drawCircle(Canvas c) {
        int height = c.getHeight();
        int width = c.getWidth();
        float maxR = (float)Math.min(height,width);
        int[] colors = {Color.RED, Color.BLACK, Color.BLUE, Color.GREEN, Color.YELLOW, Color.WHITE};
        c.drawColor(colors[(int) (Math.random() * colors.length)]); // Фон
        paint.setColor(colors[(int) (Math.random() * colors.length)]); // Цвет круга
        float koef = (float)Math.random(); // < 1
        c.drawCircle(width/2,height/2,maxR*koef,paint);
    }
}
