package com.example.vediotest03;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class VedioSurface extends SurfaceView implements SurfaceHolder.Callback {

    private MediaRecorder mediaRecorder;
    private String title;
    private SurfaceHolder surfaceHolder;
    private Date time;

    public boolean isRecording;

    // 构造函数
    public VedioSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        // this.setKeepScreenOn(true);
        // this.setFocusable(true);
        // surfaceHolder = this.getHolder();
        // surfaceHolder.addCallback(this);
        // isRecording = false;
    }

    // 开始预览
    public void startPreview(String title, int width, int height) {

        this.title = title;
        // this.time = time;
        SurfaceHolder sHolder = getHolder();
        sHolder.setFixedSize(width, height);
        // 保持屏幕常亮
        sHolder.setKeepScreenOn(true);
        // 添加回调函数后执行surfaceCreated方法
        sHolder.addCallback(this);
    }

    // 录像
    private void record() {
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        // String date = sdf.format(time);
        String dirURL = Environment.getExternalStorageState()
                + "/DamageAccess/vedios";
        File file = new File(dirURL);
        if (!file.exists()) {
            file.mkdirs();
        }
        mediaRecorder = new MediaRecorder();
        // 设置视频录入源
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        // 设置视频输出格式为3GP
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        // 设置视频解码方式
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        // 设置预览显示
        mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
        // 设置输出文件路径
        // mediaRecorder.setOutputFile(dirURL + File.separator + title + date
        // + ".3gp");
        mediaRecorder.setOutputFile(dirURL + File.separator + title + ".3gp");
        try {
            // 录制准备
            mediaRecorder.prepare();
            // 录制开始
            mediaRecorder.start();
        }
        catch (IllegalStateException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 释放资源
    public void release(Context context) {
        Toast.makeText(context, "释放资源", Toast.LENGTH_SHORT).show();
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("Tag", "surfaceCreated in VedioSurface");
        // if (isRecording == false) {
        surfaceHolder = holder;
        record();
        Log.d("Tag", "recorda() is called");
        // }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
        Log.d("Tag", "surfaceChanged");
        surfaceHolder = holder;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d("Tag", "surfaceDestroyed");
        // if (isRecording == true) {
        surfaceHolder = null;
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
        // }
    }

}
