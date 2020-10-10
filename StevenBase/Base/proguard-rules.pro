##-------------------------系统混淆配置-------------------------------
## 迭代优化次数
#-optimizationpasses 5
## 混淆时不会产生混合的类名
#-dontusemixedcaseclassnames
## 指定不去忽略非公共的库类
#-dontskipnonpubliclibraryclasses
##不跳过library中的非public的类
#-dontskipnonpubliclibraryclassmembers
## 不做预校验
#-dontpreverify
##不进行优化
#-dontoptimize
## 混淆时记录日志
#-verbose
##忽略警告
#-ignorewarnings
##-------------------------系统混淆配置-------------------------------
#
##-------------------------默认保留区---------------------------------
## 保护注解，保护内部类
#-keepattributes *Annotation*,InnerClasses
#-keepattributes JavascriptInterface
## 保护泛型
#-keepattributes Signature
## 保持代码行号
#-keepattributes SourceFile,LineNumberTable
## 保持自定义异常
#-keep public class * extends java.lang.Exception
#
#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Application
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep public class * extends android.content.ContentProvider
#-keep public class * extends android.app.backup.BackupAgentHelper
#-keep public class * extends android.preference.Preference
#-keep public class * extends android.view.View
#
##保留native方法
#-keepclasseswithmembernames class * {
#    native <methods>;
#}
#-keepclassmembers class * extends android.app.Activity{
#    public void *(android.view.View);
#}
#-keepclassmembers enum * {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}
#-keep public class * extends android.view.View{
#    *** get*();
#    void set*(***);
#    public <init>(android.content.Context);
#    public <init>(android.content.Context, android.util.AttributeSet);
#    public <init>(android.content.Context, android.util.AttributeSet, int);
#}
#-keepclasseswithmembers class * {
#    public <init>(android.content.Context, android.util.AttributeSet);
#    public <init>(android.content.Context, android.util.AttributeSet, int);
#}
#-keepclassmembers class * implements java.io.Serializable {
#    static final long serialVersionUID;
#    private static final java.io.ObjectStreamField[] serialPersistentFields;
#    private void writeObject(java.io.ObjectOutputStream);
#    private void readObject(java.io.ObjectInputStream);
#    java.lang.Object writeReplace();
#    java.lang.Object readResolve();
#}
#-keep class **.R$* {
# *;
#}
#-keepclassmembers class * {
#    void *(**On*Event);
#}