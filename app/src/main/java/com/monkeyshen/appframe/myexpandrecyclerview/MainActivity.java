package com.monkeyshen.appframe.myexpandrecyclerview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.monkeyshen.appframe.myexpandrecyclerview.entity.BuyCar;
import com.monkeyshen.appframe.myexpandrecyclerview.entity.Goods;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnItemSelectListener {

    List<BuyCar> mDatas = new ArrayList<>();
    MyAdapter mAdapter;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.updateData();
            }
        });

        for (int i = 0; i < 100; i++) {
            BuyCar buyCar = new BuyCar();
            List<Goods> goodsList = new ArrayList<>();
            for (int j = 0; j < i + 1; j++) {
                Goods goods = new Goods();
                goods.setPosition(j);
                goodsList.add(goods);
            }
            buyCar.setGoods(goodsList);
            mDatas.add(buyCar);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyAdapter(mDatas, this);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onBuyCarSelectListener(BuyCar buyCar) {
        Log.e("购物车点击事件", buyCar + "");
    }

    @Override
    public void onGoodsSelectListener(Goods goods) {
        Log.e("商品点击事件", goods + ",buycar:" + goods.getBuyCar());
    }
}
