package com.zhitou.job.main.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.zhitou.job.R;
import com.zhitou.job.main.been.AddressForSH;
import com.zhitou.job.parttimejob.base.BaseActivity;
import com.zhitou.job.parttimejob.been.MyUser;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.b.name;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class AddAddressSHActivity extends BaseActivity {

    private EditText mEdtName;
    private EditText mEdtPhone;
    private EditText mEdtYzCode;
    private EditText mEdtProvinceCity;
    private EditText mEdtDetailAddress;

    private String name;
    private String phone;
    private String code;
    private String provinceCity;
    private String detailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address_sh);
        initView();
    }

    private void initView() {
        mEdtName = (EditText)findViewById(R.id.edt_name);
        mEdtPhone = (EditText)findViewById(R.id.edt_phone);
        mEdtYzCode = (EditText)findViewById(R.id.edt_yz_code);
        mEdtProvinceCity = (EditText)findViewById(R.id.edt_province_city);
        mEdtDetailAddress = (EditText)findViewById(R.id.edt_detail_address);

        findViewById(R.id.tv_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verify()){
                    save();
                }
            }
        });
    }

    private boolean verify() {
        name = mEdtName.getText().toString().trim();
        phone = mEdtPhone.getText().toString().trim();
        code = mEdtYzCode.getText().toString().trim();
        provinceCity = mEdtProvinceCity.getText().toString().trim();
        detailAddress = mEdtDetailAddress.getText().toString().trim();

        if (name.equals("")){
            showToast("请输入联系人姓名！");
            return false;
        }
        if (phone.equals("")){
            showToast("请输入手机号！");
            return false;
        }
        if (code.equals("")){
            showToast("请输入邮政编号！");
            return false;
        }
        if (provinceCity.equals("")){
            showToast("请输入所在地区！");
            return false;
        }
        if (detailAddress.equals("")){
            showToast("请输入详细地址！");
            return false;
        }

        return true;

    }
    private void save() {
        AddressForSH addressForSH = new AddressForSH(
                BmobUser.getCurrentUser().getObjectId(),
                "武汉",
                detailAddress,
                "江夏",
                BmobUser.getCurrentUser(MyUser.class).getMobilePhoneNumber(),
                "湖北省",
                name,
                code);
        addressForSH.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null){
                    showToast("添加地址成功！");
                    finish();
                }
            }
        });
    }
}
