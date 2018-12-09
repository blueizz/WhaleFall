package com.blueizz.whalefall.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

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
        //自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.recyclerview_divider));
        mRecyclerView.addItemDecoration(divider);
        /**
         * Item的高度是固定的，设置这个选项可以提高性能。
         * 总得来说就是就是避免整个布局绘制，就是避免requestLayout
         */
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MainAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        setListener();
    }

    private void setListener() {
        mAdapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String key) {
                Class cls = ActivityRouter.getActivityMap().get(key);
                Intent intent = new Intent(MainActivity.this, cls);
                startActivity(intent);
            }
        });
    }

    private void initPresenter() {
        mPresenter = new MainPresenter(this, this);
        mPresenter.getData();
    }

    @Override
    public void updateData(List<String> data) {
        mAdapter.setData(data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
