package com.monkeyshen.appframe.myexpandrecyclerview.entity;

/**
 * Description:
 * Created by MonkeyShen on 2017/8/3.
 * Mail:shenminjie92@sina.com
 */

public class Goods {
    private BuyCar buyCar;
    private boolean isLast;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public BuyCar getBuyCar() {
        return buyCar;
    }

    public void setBuyCar(BuyCar buyCar) {
        this.buyCar = buyCar;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }
}
