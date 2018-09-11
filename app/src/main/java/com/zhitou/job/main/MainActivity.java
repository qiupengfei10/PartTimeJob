package com.zhitou.job.main;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.zhitou.job.main.fragment.HomeFragment;
import com.zhitou.job.parttimejob.R;
import com.zhitou.job.parttimejob.base.BaseActivity;
import com.zhitou.job.parttimejob.fragments.FragmentBanner;

public class MainActivity extends BaseActivity {
    RadioGroup radioGroup;

    private FragmentTransaction transaction;
    private Fragment[] fragments = new Fragment[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);
        initView();
        initClick();
    }

    private void initClick() {
        findViewById(R.id.tv_push).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("点击发布按钮！");
                startActivity(new Intent(MainActivity.this,PushTwoHandActivity.class));
            }
        });
    }

    private void initView() {
        showFragment(0);
        radioGroup = (RadioGroup) findViewById(R.id.rg);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.rb1:
                        showFragment(0);
                        break;
                    case R.id.rb2:
                        showFragment(1);
                        break;
                    case R.id.rb3:
                        showFragment(2);
                        break;
                    case R.id.rb4:
                        showFragment(3);
                        break;
                }
            }
        });
    }

    public void showFragment(int position){
        transaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment();
        if (fragments[position] != null){
            transaction.show(fragments[position]);
        }else {
            switch (position){
                case 0:
                    transaction.add(R.id.fl_content,new HomeFragment());
                    break;
                case 1:
                    transaction.add(R.id.fl_content,new HomeFragment());
                    break;
                case 2:
                    transaction.add(R.id.fl_content,new HomeFragment());
                    break;
                case 3:
                    transaction.add(R.id.fl_content,new HomeFragment());
                    break;
            }
        }

        transaction.commit();
    }

    private void hideAllFragment() {
        for (int i = 0; i < fragments.length;i++){
            if (fragments[i] != null){
                transaction.hide(fragments[i]);
            }
        }
    }
}
