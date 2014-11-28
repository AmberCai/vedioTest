package com.example.vediotest03;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

    private VedioSurface vedioSurface;
    private Button startRecord, stopRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vedioSurface = new VedioSurface(this, null);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {

        vedioSurface = (VedioSurface) findViewById(R.id.vedioSurface);

        startRecord = (Button) findViewById(R.id.startRecord);
        stopRecord = (Button) findViewById(R.id.stopRecord);

        // 停止刻录按钮初始化为不可点击
        stopRecord.setEnabled(false);

        startRecord.setOnClickListener(this);
        stopRecord.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.startRecord:
                String title = "Title";
                // Date time = new Date(System.currentTimeMillis());
                int width = vedioSurface.getWidth();
                int height = vedioSurface.getHeight();
                vedioSurface.startPreview(title, width, height);
                Log.d("Tag", "startPreview");
                vedioSurface.surfaceCreated(vedioSurface.getHolder());
                Log.d("Tag", "surfaceCreated in MainActivity");

                // vedioSurface.isRecording = true;

                startRecord.setEnabled(false);
                stopRecord.setEnabled(true);
                break;
            case R.id.stopRecord:

                // vedioSurface.isRecording = false;

                // vedioSurface.surfaceDestroyed(vedioSurface.getHolder());

                startRecord.setEnabled(true);
                stopRecord.setEnabled(false);
                break;
            default:
                break;
        }
    }

}
