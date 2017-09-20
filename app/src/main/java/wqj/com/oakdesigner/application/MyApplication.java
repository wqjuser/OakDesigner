package wqj.com.oakdesigner.application;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.youth.xframe.XFrame;

/**
 * Created by WQJ on 2017/9/19
 * Descripyion:
 * Version: 1
 * Modify Person :
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        XFrame.init(this);
        Fresco.initialize(this);
    }
}
