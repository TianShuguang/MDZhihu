# Material Design Zhihu

### 参考网址：
1.[volley二次封装](http://www.cnblogs.com/summers/p/4398679.html "悬停显示")

  
2.[项目实战①—高仿知乎日报（1）逼真的开场动画](http://blog.csdn.net/jack_king007/article/details/41751573 "悬停显示")
  
3.[高仿知乎日报](https://github.com/iKrelve/KuaiHu "悬停显示")

### 已完成功能
    1.启动界面图像获取及动画实现
    2.首页日报列表获取、显示
    3.首页最新消息（包含顶部Bunner）
    4.主题日报内容查看
    5.消息内容获取与展示
    6.新闻对应长评论查看
    
### 待实现功能
    1.消息收藏（保存本地）
    2.夜间模式
    3.设置（无图模式，清空缓存，反馈等）
    4.分享功能
    5.网络请求增加进度条

### 亮点
    1.RecyclerView没有addHeaderView方法,在Adapter中根据position实现
    2.基类Activity和基类Fragment封装，及App中Activity管理
    3.使用封装后的Volley框架实现联网功能

### 缺点
    1.侧边栏滑动和首页ViewPager滑动冲突
    2.已封装的volley框架中Request应增加requestID，方便同一界面对多请求的区分
    3.未实现加载更多

### Dependencies

    classpath 'com.android.tools.build:gradle:1.5.0'

    compile 'com.android.support:appcompat-v7:23.1.1'
    compile 'com.android.support:design:23.1.1'
    compile 'com.mcxiaoke.volley:library:1.0.19'
    compile 'com.android.support:cardview-v7:23.1.1'
    compile 'com.android.support:recyclerview-v7:23.1.1'

### 如果说我看得比别人更远些，那是因为我站在巨人的肩膀上。<br> 对以上作者表示感谢！！！


  
