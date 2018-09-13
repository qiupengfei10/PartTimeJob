package com.zhitou.job.main.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Window;

import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.zhitou.job.R;


/**
 * Created by Administrator on 2017/12/1.
 */

public class DialogCallBack extends StringCallback {

    private LoadingView pd;

    public DialogCallBack(Activity activity) {
        super();
        initDialog(activity);
    }

    private void initDialog(Activity activity) {
        if (activity == null){
            return;
        }
        if (pd == null ) {
            pd = new LoadingView(activity, R.style.CustomDialog);
        }

        pd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pd.setCanceledOnTouchOutside(false);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("等待中...");
    }

    @Override
    public void onSuccess(Response<String> response) {

    }

    @Override
    public void onStart(Request<String, ? extends Request> request) {
        super.onStart(request);
        if (pd != null && !pd.isShowing()) {
            pd.show();
        }
    }

    @Override
    public void onFinish() {
        super.onFinish();
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }
}
