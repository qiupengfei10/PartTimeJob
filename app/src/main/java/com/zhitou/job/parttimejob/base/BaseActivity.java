package com.zhitou.job.parttimejob.base;

import android.app.AlertDialog;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhitou.job.parttimejob.R;

/**
 * Created by qiupengfei on 2017/10/16.
 */
public abstract class BaseActivity extends FragmentActivity{
    protected TextView mTvTitle;
    protected ImageView mIvBack;
    protected TextView mTvTitleRight;
    private AlertDialog aler;

    protected void dismissAler() {
        if (aler != null){
            aler.dismiss();
            aler = null;
        }
    }

    protected void setTitle(String title) {
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mIvBack = (ImageView) findViewById(R.id.iv_title_left);
        mTvTitleRight = (TextView) findViewById(R.id.iv_title_right);
        mTvTitleRight.setVisibility(View.GONE);

        mTvTitle.setText(title);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void setTitle(String title,boolean canBack) {
        setTitle(title);
        mIvBack.setVisibility(View.GONE);
    }

    protected void showDialogTwoBtn(String content,String btn1,String btn2,OnClickListenerForDialogTwoBtn onClick) {
        View view = LayoutInflater.from(this).inflate(R.layout.base_dialog_view,null);
        TextView mTvContent = (TextView) view.findViewById(R.id.tv_content);
        TextView mTvBtn1 = (TextView) view.findViewById(R.id.tv_btn1);
        TextView mTvBtn2 = (TextView) view.findViewById(R.id.tv_btn2);
        mTvContent.setText(content);
        mTvBtn1.setText(btn1);
        mTvBtn2.setText(btn2);
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setView(view);
        aler = builder.show();
        onClick.onClickListenerForDialog(mTvBtn1,mTvBtn2);


    }
    protected void showToast(String text) {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.finshActivity(this);
    }


    public interface OnClickListenerForDialogTwoBtn{
        void onClickListenerForDialog(TextView tvBtn1,TextView tvBtn2);
    }


}
