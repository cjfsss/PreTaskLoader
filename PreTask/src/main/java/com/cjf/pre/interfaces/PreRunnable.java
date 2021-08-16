package com.cjf.pre.interfaces;

import androidx.annotation.NonNull;

import com.cjf.pre.core.PreTaskImpl;

public interface PreRunnable<T> {

    /**
     * loadData
     * run in main thread
     *
     * @return T
     */
    void loadData(@NonNull PreTaskImpl<T> liveData);
}
