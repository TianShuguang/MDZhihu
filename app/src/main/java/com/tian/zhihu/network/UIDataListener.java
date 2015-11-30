package com.tian.zhihu.network;

/**
 * Created by tianshuguang on 15/11/30.
 */
public interface UIDataListener<T> {
    void onDataChanged(T data);
    void onErrorHappened(String errorCode, String errorMessage);
}
