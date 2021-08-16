package com.cjf.pre.core;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.cjf.pre.interfaces.PreRunnable;
import com.cjf.pre.interfaces.PreTask;
import com.cjf.pre.interfaces.PreTaskLoaderListener;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Title: LiveDataLoaderManager </p>
 * <p>Description: 需要在Application中使用 </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/6/28 17:55
 */
public final class PreTaskLoaderManager {

    @NonNull
    private final Map<Object, PreTaskLoader<?>> taskMap = new ConcurrentHashMap<>();

    @Nullable
    private PreTaskLoaderListener mPreTaskLoaderListener;

    @NonNull
    public <T> PreTaskLoader<T> put(@NonNull Object tag, @NonNull PreRunnable<T> task) {
        PreTaskLoader<T> liveDataLoader = new PreTaskLoader<>(tag, task);
        if (mPreTaskLoaderListener != null) {
            mPreTaskLoaderListener.initTask(liveDataLoader);
        }
        taskMap.put(tag, liveDataLoader);
        return liveDataLoader;
    }

    @NonNull
    public <T> PreTaskLoader<T> put(@NonNull Object tag, @NonNull MutableLiveData<T> liveData, @NonNull PreRunnable<T> task) {
        PreTaskLoader<T> liveDataLoader = new PreTaskLoader<>(tag, liveData, task);
        if (mPreTaskLoaderListener != null) {
            mPreTaskLoaderListener.initTask(liveDataLoader);
        }
        taskMap.put(tag, liveDataLoader);
        return liveDataLoader;
    }

    @NonNull
    public <T> PreTaskLoader<T> putTask(@NonNull Object tag, @NonNull PreTask<T> task) {
        PreTaskLoader<T> liveDataLoader = new PreTaskLoader<>(tag, task);
        if (mPreTaskLoaderListener != null) {
            mPreTaskLoaderListener.initTask(liveDataLoader);
        }
        taskMap.put(tag, liveDataLoader);
        return liveDataLoader;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Nullable
    public <T> PreTaskLoader<T> get(@NonNull Object tag) {
        if (!taskMap.containsKey(tag)) {
            return null;
        }
        return (PreTaskLoader) taskMap.get(tag);
    }

    @NonNull
    public <T> PreTaskLoader<T> getTask(@NonNull Object tag, @NonNull Function<Object, PreTask<T>> function) {
        PreTaskLoader<T> controller = get(tag);
        if (controller != null) {
            return controller;
        }
        return putTask(tag, function.apply(tag));
    }

    @NonNull
    public <T> PreTaskLoader<T> get(@NonNull Object tag, @NonNull Function<Object, PreRunnable<T>> function) {
        PreTaskLoader<T> controller = get(tag);
        if (controller != null) {
            return controller;
        }
        return put(tag, function.apply(tag));
    }

    @NonNull
    public <T> PreTaskLoader<T> get(@NonNull Object tag, @NonNull MutableLiveData<T> liveData, @NonNull Function<Object, PreRunnable<T>> function) {
        PreTaskLoader<T> controller = get(tag);
        if (controller != null) {
            return controller;
        }
        return put(tag, liveData, function.apply(tag));
    }

    @NonNull
    public <T> PreTaskLoader<T> observeForever(@NonNull Object tag, @NonNull PreRunnable<T> task, @NonNull Observer<T> listener) {
        PreTaskLoader<T> liveDataLoader = put(tag, task);
        liveDataLoader.observeForever(listener);
        return liveDataLoader;
    }

    @NonNull
    public <T> PreTaskLoader<T> observeForever(@NonNull Object tag, @NonNull MutableLiveData<T> liveData, @NonNull PreRunnable<T> task, @NonNull Observer<T> listener) {
        PreTaskLoader<T> liveDataLoader = put(tag, liveData, task);
        liveDataLoader.observeForever(listener);
        return liveDataLoader;
    }

    @NonNull
    public <T> PreTaskLoader<T> observeTaskForever(@NonNull Object tag, @NonNull PreTask<T> task, @NonNull Observer<T> listener) {
        PreTaskLoader<T> liveDataLoader = putTask(tag, task);
        liveDataLoader.observeForever(listener);
        return liveDataLoader;
    }

    @NonNull
    public <T> PreTaskLoader<T> observe(@NonNull Object tag, @NonNull final LifecycleOwner owner, @NonNull PreRunnable<T> task, @NonNull Observer<T> listener) {
        PreTaskLoader<T> liveDataLoader = put(tag, task);
        liveDataLoader.observe(owner, listener);
        return liveDataLoader;
    }

    @NonNull
    public <T> PreTaskLoader<T> observe(@NonNull Object tag, @NonNull MutableLiveData<T> liveData, @NonNull final LifecycleOwner owner, @NonNull PreRunnable<T> task, @NonNull Observer<T> listener) {
        PreTaskLoader<T> liveDataLoader = put(tag, liveData, task);
        liveDataLoader.observe(owner, listener);
        return liveDataLoader;
    }

    @NonNull
    public <T> PreTaskLoader<T> observeTask(@NonNull Object tag, @NonNull final LifecycleOwner owner, @NonNull PreTask<T> task, @NonNull Observer<T> listener) {
        PreTaskLoader<T> liveDataLoader = putTask(tag, task);
        liveDataLoader.observe(owner, listener);
        return liveDataLoader;
    }

    public void setPreTaskLoaderListener(@Nullable PreTaskLoaderListener mPreTaskLoaderListener) {
        this.mPreTaskLoaderListener = mPreTaskLoaderListener;
    }

    public boolean exists(@NonNull Object tag) {
        return taskMap.containsKey(tag);
    }


    public void remove(@Nullable Object... tags) {
        if (tags == null) {
            return;
        }
        for (Object tag : tags) {
            if (!taskMap.containsKey(tag)) {
                continue;
            }
            PreTaskLoader<?> liveDataLoadTask = taskMap.get(tag);
            if (liveDataLoadTask != null) {
                liveDataLoadTask.onDestroy();
                taskMap.remove(tag);
            }
        }
    }

    public void onDestroy() {
        Set<Object> keySet = taskMap.keySet();
        if (keySet == null) {
            return;
        }
        for (Object key : keySet) {
            PreTaskLoader<?> liveDataLoadTask = taskMap.get(key);
            if (liveDataLoadTask != null) {
                liveDataLoadTask.onDestroy();
            }
        }
        setPreTaskLoaderListener(null);
        taskMap.clear();
    }
}
