package com.tian.zhihu.constant;

/**
 * Created by tianshuguang on 15/11/30.
 */
public interface AppConstant {

    /**基本URL*/
    String BaseUrl="http://news-at.zhihu.com/";
    /**1. 启动界面图像获取*/
    String method_startimage="api/4/start-image/1080*1776";
    /**2. 软件版本查询*/
    String method_version="api/4/version/android/2.3.0";
    /**3. 最新消息*/
    String method_news="api/4/news/latest";
    /**4. 消息内容获取与离线下载*/
    String method_news_content="api/4/news/3892357";
    /**5. 过往消息*/
    String method_news_before="api/4/news/before/20131119";
    /**6. 新闻额外信息*/
    String method_news_extra="api/4/story-extra/#{id}";
    /**7. 新闻对应长评论查看*/
    String method_long_comments="api/4/story/#{id}/long-comments";
    /**8. 新闻对应短评论查看*/
    String method_short_comments="api/4/story/4232852/short-comments";

    /**9. 主题日报列表查看*/
    String method_themes="api/4/themes";
    /**10. 主题日报内容查看*/
    String method_themes_content="api/4/theme/11";




}
