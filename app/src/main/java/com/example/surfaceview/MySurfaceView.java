package com.example.surfaceview;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private MyThread myThread;

    public MySurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this); // !!!
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        myThread = new MyThread(getHolder());
        myThread.flag = true;
        myThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        boolean retry = true;
        myThread.flag = false;
        while (retry){
            try {
                myThread.join();
                retry = false;
                Log.d("RRR", "Stopped!");
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
