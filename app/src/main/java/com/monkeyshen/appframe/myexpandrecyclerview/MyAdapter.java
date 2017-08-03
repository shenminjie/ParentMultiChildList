package com.monkeyshen.appframe.myexpandrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.monkeyshen.appframe.myexpandrecyclerview.entity.BuyCar;
import com.monkeyshen.appframe.myexpandrecyclerview.entity.Goods;

import java.util.List;

/**
 * Description:
 * <p>
 * 数据用于模拟父子结构的列表，购物车跟商品的关系
 * 一对多，一个购物车有多个商品，多个购物车就出现多个商品，用列表显示
 * 解决，recyclerView 里面嵌套 recyclerView 的问题
 * <p>
 * 思路：
 * 1.一个购物车中，有多个商品数据,分别用两个集合，记录着他们的index，并且每个商品绑定着各自的购物车
 * 2.再根据之前记录的索引进行获取数据，如果能获取数据则表示此位置是哪一个item
 * <p>
 * main:
 * 解决每个位置的视图对应那个数据，使用SparseArray进行绑定数据。
 * <p>
 * Created by MonkeyShen on 2017/8/3.
 * Mail:shenminjie92@sina.com
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BuyCar> mDatas;
    private SparseArray<Goods> mGoods;
    private SparseArray<BuyCar> mBuyCars;
    private int mTotalView;
    private OnItemSelectListener mListener;

    /**
     * 记录商品viewHolder创建的次数跟购物车viewholder的次数
     */
    private static int mCreateGoodsTimes;
    private static int mCreateBuyTimes;


    enum VIEWTYPE {
        BUYCAR, GOODS;
    }

    public MyAdapter(List<BuyCar> mDatas, OnItemSelectListener mListener) {
        this.mDatas = mDatas;
        this.mListener = mListener;
        mGoods = new SparseArray<>();
        mBuyCars = new SparseArray<>();
        updateViewCount();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEWTYPE.BUYCAR.ordinal()) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_buycar, parent, false);
            return new BuyCarViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods, parent, false);
            return new GoodsViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BuyCarViewHolder) {
            final BuyCar buyCar = mBuyCars.get(position);
            BuyCarViewHolder viewHolder = (BuyCarViewHolder) holder;
            viewHolder.tv.setText("购物车---视图位置:" + position + ";");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onBuyCarSelectListener(buyCar);
                }
            });
        }
        if (holder instanceof GoodsViewHolder) {
            final Goods goods = mGoods.get(position);
            GoodsViewHolder viewHolder = (GoodsViewHolder) holder;
            viewHolder.tv.setText("商品---视图位置:" + position + ";集合位置:" + goods.getPosition());
            viewHolder.bottom.setVisibility(goods.isLast() ? View.VISIBLE : View.GONE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onGoodsSelectListener(goods);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        BuyCar buyCar = mBuyCars.get(position);
        if (buyCar != null) {
            return VIEWTYPE.BUYCAR.ordinal();
        }
        Goods goods = mGoods.get(position);
        if (goods != null) {
            return VIEWTYPE.GOODS.ordinal();
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mTotalView;
    }

    /**
     * 根据数据源设计每个数据的视图位置
     */
    private void updateViewCount() {
        //清空数据
        mGoods.clear();
        mBuyCars.clear();
        mCreateGoodsTimes = 0;
        mCreateBuyTimes = 0;

        int size = 0;
        for (int i = 0; i < mDatas.size(); i++) {
            size++;
            BuyCar buyCar = mDatas.get(i);
            //记录坐标
            mBuyCars.put(size - 1, buyCar);
            for (int j = 0; j < buyCar.getGoods().size(); j++) {
                size++;
                Goods goods = buyCar.getGoods().get(j);
                //设置基础数据
                goods.setBuyCar(buyCar);
                goods.setLast(j == buyCar.getGoods().size() - 1);
                //记录坐标
                mGoods.put(size - 1, goods);
            }
        }
        mTotalView = size;
    }

    /**
     * 更新数据源
     */
    public void updateData() {
        updateViewCount();
        notifyDataSetChanged();
    }


    class GoodsViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        View bottom;

        GoodsViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
            bottom = itemView.findViewById(R.id.bottom);
            mCreateGoodsTimes++;
            Log.e("tag", "创建的goods viewHolder 次数:" + mCreateGoodsTimes);
        }
    }

    class BuyCarViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        BuyCarViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
            mCreateBuyTimes++;
            Log.e("tag", "创建的buycar viewHolder 次数:" + mCreateGoodsTimes);
        }
    }


    /**
     * 点击回调
     */
    interface OnItemSelectListener {

        /**
         * 购物点击
         */
        void onBuyCarSelectListener(BuyCar buyCar);

        /**
         * 商品点击
         */
        void onGoodsSelectListener(Goods goods);
    }

}
