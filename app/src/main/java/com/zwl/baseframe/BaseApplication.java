package com.zwl.baseframe;

import android.app.Activity;
import android.app.Application;

import com.facebook.stetho.Stetho;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.squareup.leakcanary.LeakCanary;
import com.zwl.baseframe.domain.ui.implementz.di.component.ActivityCompontent;
import com.zwl.baseframe.domain.ui.implementz.di.module.ActivityModule;
import com.zwl.baseframe.implementz.di.component.AppComponent;
import com.zwl.baseframe.implementz.di.component.DaggerAppComponent;
import com.zwl.baseframe.implementz.di.module.AppModule;


/**
 * @author velen
 * @date 2017/3/23
 */
public class BaseApplication extends Application {
    private static volatile BaseApplication sInstance;
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        if(!initLeakCanary()){
            return;
        }

        initLogger();

        initStetho();

        initAppComponent();
    }

    private void initLogger() {
        //支持log格式化，log开关，保存到文件
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("Logger")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy){
            @Override public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }

    private void initStetho() {
        //chrome://inspect. Click the "Inspect" button to begin.
        Stetho.initializeWithDefaults(this);
    }

    private boolean initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return false;
        }else {
            LeakCanary.install(this);
            return true;
        }
    }

    private void initAppComponent() {
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(sInstance)).build();
    }

    public AppComponent component() {
        return mAppComponent;
    }

    public ActivityCompontent getSampleActivityComponent(Activity activity){
        return  mAppComponent.newActivityCompontent(new ActivityModule(activity));
    }

    public static BaseApplication getInstance() {
        return sInstance;
    }

}
