package com.xuexiang.xutildemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.xuexiang.xutil.XUtil;
import com.xuexiang.xutil.app.IntentUtils;
import com.xuexiang.xutil.display.BarUtils;
import com.xuexiang.xutil.display.ColorUtils;
import com.xuexiang.xutil.display.Colors;
import com.xuexiang.xutil.file.FileUtils;
import com.xuexiang.xutil.tip.ToastUtil;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button:
                Log.e("xuexiang", FileUtils.getDiskDir());
                break;
            default:
                break;
        }
    }
}
