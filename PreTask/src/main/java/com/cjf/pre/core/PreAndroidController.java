package com.cjf.pre.core;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;

/**
 * <p>Title: PreViewModel </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/6/29 19:57
 */
public abstract class PreAndroidController extends PreController {

    @SuppressLint("StaticFieldLeak")
    private Application mApplication;

    public PreAndroidController(@NonNull Application application) {
        mApplication = application;
    }

    /**
     * Return the application.
     */
    @SuppressWarnings({"TypeParameterUnusedInFormals", "unchecked"})
    @NonNull
    public <T extends Application> T getApplication() {
        return (T) mApplication;
    }

    @Override
    public void onCleared() {
        mApplication = null;
        super.onCleared();
    }
}
