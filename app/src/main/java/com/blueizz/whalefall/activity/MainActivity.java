package com.blueizz.whalefall.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.blueizz.reflection.ReflectionActivity;
import com.blueizz.whalefall.ActivityRouter;
import com.blueizz.whalefall.R;
import com.blueizz.whalefall.adapter.MainAdapter;
import com.blueizz.whalefall.presenter.MainPresenter;
import com.blueizz.whalefall.view.IMainView;

import java.util.List;

public class MainActivity extends Activity implements IMainView {
    private RecyclerView mRecyclerView;
    private MainAdapter mAdapter;

    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initPresenter();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.rv_main);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        //Item的高度是固定的，设置这个选项可以提高性能。总得来说就是就是避免整个布局绘制，就是避免requestLayout
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MainAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String activityName) {
                Class cls = ActivityRouter.getActivityMap().get(activityName);
                Intent intent = new Intent(MainActivity.this, cls);
                startActivity(intent);
            }
        });
    }

    private void initPresenter() {
        mPresenter = new MainPresenter(this);
        mPresenter.getData();
    }

    @Override
    public void updateData(List<String> data) {
        mAdapter.setData(data);
    }
}
