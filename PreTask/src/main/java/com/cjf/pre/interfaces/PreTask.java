package com.cjf.pre.interfaces;


import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.List;
import java.util.Map;

public interface PreTask<T> extends Runnable {

    @NonNull
    MutableLiveData<T> getLiveData();

    void post();

    void postValue(T data);

    boolean isUpdate();

    int getStartIndex();

    int getPageSize();

    int getPageIndex();

    void setStartIndex(int startIndex);

    void setPageIndex(int pageIndex);

    void setPageSize(int pageSize);

    void addPage();

    void resetPage();

    @NonNull
    Map<String, Object> getParam();

    @NonNull
    List<Object> getParamList();

    void setParam(@Nullable Map<String, Object> pairs);

    void setParam(@Nullable List<Object> pairs);

    @MainThread
    void observe(@NonNull LifecycleOwner owner, @NonNull final Observer<T> listener);

    @MainThread
    void observeForever(@NonNull final Observer<T> observer);

    @MainThread
    void removeObserver(@NonNull final Observer<T> observer);

    void onDestroy();

}
