##  StevenBase
#### 简介
- StevenBase是一款集成Retrofit+MVP+RxJava于一体的一款框架。
- 包含多样的自定义控件，比如标签控件、底部导航控件、果冻滑动视图等。
- 封装常用的工具类，比如文件计算工具、时间工具、设备信息工具、精确计算工具等。
- MVP模式解耦model和view层，利用契约类管理接口，实现完美封装。
- rxjava结合retrofit处理接口请求、线程调度。
- activity、fragment、callback实现完美封装。

#### 用到的开源框架
- 'com.orhanobut:logger:2.1.1' 管理日志
- 'com.jakewharton:butterknife:8.8.1' 注解工具
- 'org.greenrobot:greendao:3.0.1'  数据库框架
- 'com.meituan.android.walle:library:1.1.6'   美团walle打包工具
- 'com.squareup.retrofit2:retrofit:2.0.0'  网络框架
- "io.reactivex.rxjava2:rxjava:2.1.1"
- 'com.lcodecorex:tkrefreshlayout:1.0.7' 下拉刷新框架
- 'com.android.support:design:26.0.0-alpha1'

```
        defaultConfig {
           .........
           multiDexEnabled true
           manifestPlaceholders = [
                JPUSH_PKGNAME: applicationId,
                JPUSH_APPKEY : "7756d6f8678d967e4be435d7",
                JPUSH_CHANNEL: "developer-default"
            ]
        }
 
        repositories {
            flatDir {
                dirs 'libs', '../Base/libs'
            }
        }

        local.properties
         ====>
            wxWeChatId="23223"
            wxWeChatSec="123123"
            buglyAppId="123132"
```

### app里添加fileProvider配置

