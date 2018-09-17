package com.zhitou.job.main.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.WrapperListAdapter;

import com.zhitou.job.R;
import com.zhitou.job.main.adapter.CommentAdapter;
import com.zhitou.job.main.adapter.ImageAdapter;
import com.zhitou.job.main.been.Comment;
import com.zhitou.job.main.been.CommentForTwoHand;
import com.zhitou.job.main.been.ImageBeen;
import com.zhitou.job.main.been.TwoHand;
import com.zhitou.job.main.utils.GlideUtils;
import com.zhitou.job.main.utils.KeyBoardUtils;
import com.zhitou.job.main.view.NoScrollListView;
import com.zhitou.job.parttimejob.base.BaseActivity;
import com.zhitou.job.parttimejob.been.MyUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 二手详情页面
 */
public class TwoHandDetailActivity extends BaseActivity {

    private TwoHand twoHand;
    private NoScrollListView mLvImages;
    private TextView mTvPrice;
    private TextView mTvContent;
    private TextView mTvName;
    private TextView mTvUserName;
    private EditText mEdtComment;
    private ImageView mIvUserImage;
    private NoScrollListView mLvComment;
    private LinearLayout mLlNull;
    private List<CommentForTwoHand> commentList;
    private boolean isCommentTwoHand = true;
    private CommentForTwoHand currentComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_hand_detail);
        //获取详情
        twoHand = (TwoHand)getIntent().getSerializableExtra("twoHand");
        initView();
        //获取评论
        getCommentList();

    }

    private void initView() {
        mLvImages = (NoScrollListView) findViewById(R.id.lv_images);
        mLvImages.setAdapter(new ImageAdapter(this,twoHand.getImageBeens()));
        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvName.setText(twoHand.getTitle());
        mTvPrice = (TextView) findViewById(R.id.tv_price);
        mTvPrice.setText(twoHand.getPrice());
        mTvContent = (TextView) findViewById(R.id.tv_content);
        mTvContent.setText(twoHand.getContent());
        mTvUserName = (TextView) findViewById(R.id.tv_user_name);
        mTvUserName.setText(twoHand.getPushUser().getNickName());
        mIvUserImage = (ImageView) findViewById(R.id.iv_user_logo);
        GlideUtils.showPic(this,twoHand.getPushUser().getUserImage(),mIvUserImage);
        mEdtComment = (EditText) findViewById(R.id.edt_comment_content);

        mLvComment = (NoScrollListView)findViewById(R.id.listview_comment);
        mLlNull = (LinearLayout)findViewById(R.id.ll_null);

        //问卖家
        findViewById(R.id.ll_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCommentTwoHand = true;
                findViewById(R.id.ll_edt_comment).setVisibility(View.VISIBLE);
                findViewById(R.id.ll_bottom_menu).setVisibility(View.GONE);
                KeyBoardUtils.showSoftInput(TwoHandDetailActivity.this,mEdtComment);
            }
        });

        //回复评论
        mLvComment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                isCommentTwoHand = false;
                findViewById(R.id.ll_edt_comment).setVisibility(View.VISIBLE);
                findViewById(R.id.ll_bottom_menu).setVisibility(View.GONE);
                mEdtComment.setHint("回复"+commentList.get(i).getUser().getNickName()+":"+commentList.get(i).getContent());
                KeyBoardUtils.showSoftInput(TwoHandDetailActivity.this,mEdtComment);
                currentComment = commentList.get(i);
            }
        });

        //发布评论
        findViewById(R.id.tv_push_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = mEdtComment.getText().toString().trim();
                if (!content.equals("")){
                    if (isCommentTwoHand){
                        pushComment(content);
                    }
                    else
                    {
                        returnComment(currentComment,content);
                    }

                }else {
                    showToast("请输入留言内容！");
                }
            }
        });

        //下单
        findViewById(R.id.tv_buy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(TwoHandDetailActivity.this,AffirmOrderActivity.class);
//                intent.putExtra("twoHand",twoHand);
//                startActivity(intent);
            }
        });
    }

    /**
     * 回复评论
     * @param commentForTwoHand
     */
    private void returnComment(CommentForTwoHand commentForTwoHand,String content) {
        showLoading();
        ArrayList<Comment> returnComment = commentForTwoHand.getReturnCommnet();
        if (returnComment == null)
            returnComment = new ArrayList<>();
        returnComment.add(new Comment(BmobUser.getCurrentUser(MyUser.class),commentForTwoHand.getUser(),content));

        commentForTwoHand.setReturnCommnet(returnComment);

        commentForTwoHand.update(commentForTwoHand.getObjectId(),new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    mEdtComment.setText("");
                    findViewById(R.id.ll_edt_comment).setVisibility(View.GONE);
                    findViewById(R.id.ll_bottom_menu).setVisibility(View.VISIBLE);
                    KeyBoardUtils.hideSoftInput(TwoHandDetailActivity.this,mEdtComment);
                    showToast("留言成功！");
                    getCommentList();
                }else {
                    showToast("留言失败！");
                }

                dismissLoading();
            }
        });
    }

    private void pushComment(String content) {
        showLoading();
        CommentForTwoHand comment = new CommentForTwoHand();
        comment.setUser(BmobUser.getCurrentUser(MyUser.class));
        comment.setTwoHand(twoHand);
        comment.setContent(content);
        comment.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null){
                    mEdtComment.setText("");
                    findViewById(R.id.ll_edt_comment).setVisibility(View.GONE);
                    findViewById(R.id.ll_bottom_menu).setVisibility(View.VISIBLE);
                    KeyBoardUtils.hideSoftInput(TwoHandDetailActivity.this,mEdtComment);
                    showToast("留言成功！");
                    getCommentList();
                }else {
                    showToast("留言失败！");
                }

                dismissLoading();
            }
        });
    }

    public void getCommentList() {
        BmobQuery<CommentForTwoHand> query = new BmobQuery<>();
        query.addWhereEqualTo("twoHand",new BmobPointer(twoHand));
        query.order("-createdAt");
        query.include("user");
        query.findObjects(new FindListener<CommentForTwoHand>() {
            @Override
            public void done(List<CommentForTwoHand> list, BmobException e) {
                if (e == null){
                    Log.e("qpf","查询到的评论 -- " + list.size());
                    if (list != null && list.size() > 0){
                        commentList = list;
                        mLlNull.setVisibility(View.GONE);
                        mLvComment.setAdapter(new CommentAdapter(TwoHandDetailActivity.this, list, new CommentAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClickListener(CommentForTwoHand commentForTwoHand,Comment comment) {
                                isCommentTwoHand = false;
                                findViewById(R.id.ll_edt_comment).setVisibility(View.VISIBLE);
                                findViewById(R.id.ll_bottom_menu).setVisibility(View.GONE);
                                mEdtComment.setHint("回复"+comment.getUser().getNickName()+":"+comment.getContent());
                                KeyBoardUtils.showSoftInput(TwoHandDetailActivity.this,mEdtComment);
                                currentComment = commentForTwoHand;
                            }
                        }));
                    }
                }
            }
        });
    }
}
