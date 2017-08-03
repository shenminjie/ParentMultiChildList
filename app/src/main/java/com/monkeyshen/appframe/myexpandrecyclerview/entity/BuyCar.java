package com.monkeyshen.appframe.myexpandrecyclerview.entity;

import java.util.List;

/**
 * Description:
 * Created by MonkeyShen on 2017/8/3.
 * Mail:shenminjie92@sina.com
 */

public class BuyCar {
    private List<Goods> goods;

    public List<Goods> getGoods() {
        return goods;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }

    @Override
    public String toString() {
        return "BuyCar{" +
                "goods=" + goods +
                '}';
    }
}
