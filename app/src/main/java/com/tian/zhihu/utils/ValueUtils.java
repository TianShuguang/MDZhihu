package com.tian.zhihu.utils;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * Created by tianshuguang on 15/11/30.
 *
 * 数值判断帮助类
 */
public class ValueUtils {

    /**
     * @description 判断List是否非空
     * @param noteList
     * @return boolean
     */
    public static boolean isListNotEmpty(List<?> noteList) {
        return null != noteList && noteList.size() > 0;
    }
    /**
     * @description 判断List是否为空
     * @param noteList
     * @return boolean
     */
    public static boolean isListEmpty(List<?> noteList) {
        return null == noteList || noteList.size() == 0;
    }
    /**
     * @description 判断String字符串是否为空
     * @param value
     * @return boolean
     */
    public static boolean isStrEmpty(String value) {
        if (null == value || "".equals(value.trim())) {
            return true;
        }
        return false;
    }
    /**
     * @description 判断String字符串是否非空
     * @param value
     * @return boolean
     */
    public static boolean isStrNotEmpty(String value) {
        return !isStrEmpty(value);
    }
    /**
     * @description 判断对象是否非空
     * @param object
     * @return boolean
     */
    public static boolean isNotEmpty(Object object) {
        return null != object;
    }
    /**
     * @description 判断对象是否非空
     * @param object
     * @return boolean
     */
    public static boolean isEmpty(Object object) {
        return null == object;
    }

    /**
     * 判断在多个EditText或者TextView的内容中有一个为空就返回true
     *
     * @param views
     * @return
     */
    public static boolean isHasEmptyView(View... views) {
        for (View v : views) {
            if (!v.isShown()) {// 不可见的不做判断
                continue;
            }
            if (v instanceof EditText) {
                EditText et = (EditText) v;
                if (TextUtils.isEmpty(et.getText().toString().trim())) {
                    return true;
                }
            } else if (v instanceof TextView) {
                TextView tv = (TextView) v;
                if (TextUtils.isEmpty(tv.getText().toString().trim())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 将boolean true变成"1" false变成"0"
     *
     * @param b
     * @return
     */
    public static String bolean2String(boolean b) {
        return b ? "1" : "0";
    }

    /**
     * 精确到百分位，小数点后不足补零。
     * 	例如：20>20.00, 21.1111>21.11, 21.55555>21.56
     */
    public static String format2Percentile(String number) {
        String strFormat = "%,.2f";
        Double doubleMoney = 0.00;

        if (number == null || number.length() < 1) {
            doubleMoney = 0.00;
        } else {
            try {
                doubleMoney = Double.valueOf(number);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                doubleMoney = 0.00;
                return String.format(strFormat, doubleMoney);
            }
        }
        return String.format(strFormat, doubleMoney);
    }

    /**
     * 精确到百分位，小数点后不足补零。
     * 	例如：20>20.00, 21.1111>21.11, 21.55555>21.56
     */
    public static String format2Percentile(double number) {
        return format2Percentile(Double.toString(number));
    }
}
