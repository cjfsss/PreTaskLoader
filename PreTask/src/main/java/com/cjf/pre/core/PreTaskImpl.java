package com.cjf.pre.core;


import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.cjf.pre.interfaces.PreRunnable;
import com.cjf.pre.interfaces.PreTask;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PreTaskImpl<T> implements PreTask<T> {

    @Nullable
    MutableLiveData<T> liveData = new MutableLiveData<>();
    @NonNull
    PreRunnable<T> loadTask;

    int startIndex = 1;
    int pageSize = 30;
    int pageIndex = startIndex;

    @NonNull
    final Map<String, Object> param = new HashMap<>();
    @NonNull
    final List<Object> paramList = new LinkedList<>();

    PreTaskImpl(@NonNull MutableLiveData<T> liveData, @NonNull PreRunnable<T> loadTask) {
        this.loadTask = loadTask;
        this.liveData = liveData;
    }

    PreTaskImpl(@NonNull PreRunnable<T> loadTask) {
        this.loadTask = loadTask;
    }

    @NonNull
    public MutableLiveData<T> getLiveData() {
        if (liveData == null) {
            return liveData = new MutableLiveData<>();
        }
        return liveData;
    }

    @Override
    public void post() {
        getLiveData().postValue(getLiveData().getValue());
    }

    @Override
    public void postValue(T data) {
        getLiveData().postValue(data);
    }

    @Override
    public boolean isUpdate() {
        return getStartIndex() == getPageIndex();
    }

    @Override
    public int getStartIndex() {
        return startIndex;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public int getPageIndex() {
        return pageIndex;
    }

    @Override
    @NonNull
    public Map<String, Object> getParam() {
        return param;
    }

    @NonNull
    @Override
    public List<Object> getParamList() {
        return paramList;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    @Override
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void addPage() {
        this.pageIndex++;
    }

    public void resetPage() {
        pageIndex = startIndex;
    }

    @MainThread
    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<T> listener) {
        getLiveData().observe(owner, listener);
    }

    @MainThread
    @Override
    public void observeForever(@NonNull final Observer<T> observer) {
        getLiveData().observeForever(observer);
    }

    @MainThread
    @Override
    public void removeObserver(@NonNull final Observer<T> observer) {
        getLiveData().removeObserver(observer);
    }

    @Override
    public void run() {
        if (loadTask != null) {
            loadTask.loadData(this);
        }
    }

    @Override
    public void setParam(@Nullable Map<String, Object> pairs) {
        if (pairs != null) {
            param.clear();
            param.putAll(pairs);
        }
    }

    @Override
    public void setParam(@Nullable List<Object> pairs) {
        if (pairs != null) {
            paramList.clear();
            paramList.addAll(pairs);
        }
    }

    @Override
    public void onDestroy() {
        param.clear();
        paramList.clear();
        loadTask = null;
        liveData = null;
    }

}
