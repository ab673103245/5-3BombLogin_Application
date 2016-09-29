package qianfeng.a5_3bomblogin_application;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        /*
         //注意：一定要在清单文件中配置application节点的name属性
           // 在build.gradle / sourceSets { main { jniLibs.srcDirs = ['libs'] } }
         */
        Bmob.initialize(this, "b70bf1999be9be6ce8ba60bbc7b7c10e");

    }
}
