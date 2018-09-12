package com.zhitou.job.parttimejob.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.zhitou.job.R;
import com.zhitou.job.parttimejob.base.BaseActivity;

import java.sql.Time;

/**
 * 引导页
 */
public class GuideActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

    }

    public void click(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.btn_login:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_register:
                intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }


    /**
     * 监听Back键按下事件,方法2:
     * 注意:
     * 返回值表示:是否能完全处理该事件
     * 在此处返回false,所以会继续传播该事件.
     * 在具体项目中此处的返回值视情况而定.
     */
    long firstDown = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                long time = System.currentTimeMillis() - firstDown;
                if (time < 700){
                    finish();
                    return true;
                }else {
                    firstDown = System.currentTimeMillis();
                    showToast("双击返回键即可退出程序！");
                    return true;
                }
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }
}
