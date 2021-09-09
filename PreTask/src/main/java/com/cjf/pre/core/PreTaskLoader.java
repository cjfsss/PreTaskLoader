package com.cjf.pre.core;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.core.util.Pair;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.cjf.pre.interfaces.PreRunnable;
import com.cjf.pre.interfaces.PreTask;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: LiveDataLoader </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/6/29 9:04
 */
public class PreTaskLoader<T> {

    @Nullable
    private PreTask<T> mLiveDataLoadTask;
    @NonNull
    private final Object tag;

    public PreTaskLoader(@NonNull Object tag, @NonNull PreTask<T> liveDataLoadTask) {
        this.mLiveDataLoadTask = liveDataLoadTask;
        this.tag = tag;
    }

    public PreTaskLoader(@NonNull Object tag, @NonNull PreRunnable<T> task) {
        this.mLiveDataLoadTask = new PreTaskImpl<>(task);
        this.tag = tag;
    }

    public PreTaskLoader(@NonNull Object tag, @NonNull MutableLiveData<T> liveData, @NonNull PreRunnable<T> task) {
        this.mLiveDataLoadTask = new PreTaskImpl<>(liveData, task);
        this.tag = tag;
    }

    @NonNull
    public Object getTag() {
        return tag;
    }

    @SuppressLint("RestrictedApi")
    public void observeForever(@NonNull final Observer<T> observer) {
        if (mLiveDataLoadTask == null) {
            return;
        }
        if (isMainThread()) {
            mLiveDataLoadTask.observeForever(observer);
        } else {
            ArchTaskExecutor.getInstance().postToMainThread(new Runnable() {
                @Override
                public void run() {
                    if (mLiveDataLoadTask != null) {
                        mLiveDataLoadTask.observeForever(observer);
                    }
                }
            });
        }
    }

    @SuppressLint("RestrictedApi")
    public void observe(@NonNull final LifecycleOwner owner, @NonNull final Observer<T> observer) {
        if (mLiveDataLoadTask == null) {
            return;
        }
        if (isMainThread()) {
            mLiveDataLoadTask.observe(owner, observer);
        } else {
            ArchTaskExecutor.getInstance().postToMainThread(new Runnable() {
                @Override
                public void run() {
                    if (mLiveDataLoadTask != null) {
                        mLiveDataLoadTask.observe(owner, observer);
                    }
                }
            });
        }
    }

    @SuppressLint("RestrictedApi")
    public void removeObserver(@NonNull final Observer<T> observer) {
        if (mLiveDataLoadTask == null) {
            return;
        }
        if (isMainThread()) {
            mLiveDataLoadTask.removeObserver(observer);
        } else {
            ArchTaskExecutor.getInstance().postToMainThread(new Runnable() {
                @Override
                public void run() {
                    if (mLiveDataLoadTask != null) {
                        mLiveDataLoadTask.removeObserver(observer);
                    }
                }
            });
        }
    }

    @SafeVarargs
    @NonNull
    public final PreTaskLoader<T> setSize(int pageSize, @Nullable Pair<String, Object>... pairs) {
        if (mLiveDataLoadTask != null) {
            mLiveDataLoadTask.setPageSize(pageSize);
            mLiveDataLoadTask.setParam(pairConvertToMap(pairs));
        }
        return this;
    }

    @NonNull
    public final PreTaskLoader<T> setSize(int pageSize, @Nullable Map<String, Object> pairs) {
        if (mLiveDataLoadTask != null) {
            mLiveDataLoadTask.setPageSize(pageSize);
            mLiveDataLoadTask.setParam(pairs);
        }
        return this;
    }

    @SafeVarargs
    @NonNull
    public final PreTaskLoader<T> setStart(int startIndex, @Nullable Pair<String, Object>... pairs) {
        if (mLiveDataLoadTask != null) {
            mLiveDataLoadTask.setStartIndex(startIndex);
            mLiveDataLoadTask.setParam(pairConvertToMap(pairs));
        }
        return this;
    }

    @NonNull
    public final PreTaskLoader<T> setStart(int startIndex, @Nullable Map<String, Object> pairs) {
        if (mLiveDataLoadTask != null) {
            mLiveDataLoadTask.setStartIndex(startIndex);
            mLiveDataLoadTask.setParam(pairs);
        }
        return this;
    }

    @SafeVarargs
    @NonNull
    public final PreTaskLoader<T> setIndex(int pageIndex, @Nullable Pair<String, Object>... pairs) {
        if (mLiveDataLoadTask != null) {
            mLiveDataLoadTask.setPageIndex(pageIndex);
            mLiveDataLoadTask.setParam(pairConvertToMap(pairs));
        }
        return this;
    }

    @NonNull
    public final PreTaskLoader<T> setIndex(int pageIndex, @Nullable Map<String, Object> pairs) {
        if (mLiveDataLoadTask != null) {
            mLiveDataLoadTask.setPageIndex(pageIndex);
            mLiveDataLoadTask.setParam(pairs);
        }
        return this;
    }

    @SafeVarargs
    @NonNull
    public final PreTaskLoader<T> set(int startIndex, int pageSize, @Nullable Pair<String, Object>... pairs) {
        if (mLiveDataLoadTask != null) {
            mLiveDataLoadTask.setStartIndex(startIndex);
            mLiveDataLoadTask.setPageSize(pageSize);
            mLiveDataLoadTask.setParam(pairConvertToMap(pairs));
        }
        return this;
    }

    @NonNull
    public final PreTaskLoader<T> set(int startIndex, int pageSize, @Nullable Map<String, Object> pairs) {
        if (mLiveDataLoadTask != null) {
            mLiveDataLoadTask.setStartIndex(startIndex);
            mLiveDataLoadTask.setPageSize(pageSize);
            mLiveDataLoadTask.setParam(pairs);
        }
        return this;
    }

    public final PreTaskLoader<T> setPairs(@Nullable Map<String, Object> pairs) {
        if (mLiveDataLoadTask != null) {
            mLiveDataLoadTask.setParam(pairs);
        }
        return this;
    }

    @NonNull
    @SafeVarargs
    public final PreTaskLoader<T> setPairs(@Nullable Pair<String, Object>... pairs) {
        if (mLiveDataLoadTask != null) {
            mLiveDataLoadTask.setParam(pairConvertToMap(pairs));
        }
        return this;
    }

    @SafeVarargs
    public final void autoRefresh(@Nullable Pair<String, Object>... pairs) {
        autoRefresh(pairConvertToMap(pairs));
    }

    @SafeVarargs
    public final void autoRefresh(boolean isThread, @Nullable Pair<String, Object>... pairs) {
        autoRefresh(isThread, pairConvertToMap(pairs));
    }

    @SafeVarargs
    public final void autoLoadMore(@Nullable Pair<String, Object>... pairs) {
        autoLoadMore(pairConvertToMap(pairs));
    }

    @SafeVarargs
    public final void autoLoadMore(boolean isThread, @Nullable Pair<String, Object>... pairs) {
        autoLoadMore(isThread, pairConvertToMap(pairs));
    }

    @SuppressLint("RestrictedApi")
    @SafeVarargs
    public final void autoRefreshWork(@Nullable Pair<String, Object>... pairs) {
        autoRefreshWork(pairConvertToMap(pairs));
    }

    @SuppressLint("RestrictedApi")
    @SafeVarargs
    public final void autoLoadMoreWork(@Nullable Pair<String, Object>... pairs) {
        autoLoadMoreWork(pairConvertToMap(pairs));
    }

    public final void autoRefresh(@Nullable Map<String, Object> pairs) {
        if (mLiveDataLoadTask != null) {
            mLiveDataLoadTask.resetPage();
            if (pairs != null) {
                mLiveDataLoadTask.setParam(pairs);
            }
            mLiveDataLoadTask.run();
        }
    }

    public final void autoRefresh(boolean isThread, @Nullable Map<String, Object> pairs) {
        if (isThread) {
            autoRefreshWork(pairs);
            return;
        }
        autoRefresh(pairs);
    }

    public final void autoLoadMore(@Nullable Map<String, Object> pairs) {
        if (mLiveDataLoadTask != null) {
            mLiveDataLoadTask.addPage();
            if (pairs != null) {
                mLiveDataLoadTask.setParam(pairs);
            }
            mLiveDataLoadTask.run();
        }
    }

    public final void autoLoadMore(boolean isThread, @Nullable Map<String, Object> pairs) {
        if (isThread) {
            autoLoadMoreWork(pairs);
            return;
        }
        autoLoadMore(pairs);
    }

    @SuppressLint("RestrictedApi")
    public final void autoRefreshWork(@Nullable Map<String, Object> pairs) {
        if (mLiveDataLoadTask != null) {
            mLiveDataLoadTask.resetPage();
            if (pairs != null) {
                mLiveDataLoadTask.setParam(pairs);
            }
            ArchTaskExecutor.getInstance().executeOnDiskIO(mLiveDataLoadTask);
        }
    }

    @SuppressLint("RestrictedApi")
    public final void autoLoadMoreWork(@Nullable Map<String, Object> pairs) {
        if (mLiveDataLoadTask != null) {
            mLiveDataLoadTask.addPage();
            if (pairs != null) {
                mLiveDataLoadTask.setParam(pairs);
            }
            ArchTaskExecutor.getInstance().executeOnDiskIO(mLiveDataLoadTask);
        }
    }

    @NonNull
    public final PreTaskLoader<T> setSizeAny(int pageSize, @Nullable Object... objects) {
        if (mLiveDataLoadTask != null) {
            mLiveDataLoadTask.setPageSize(pageSize);
            if (objects != null) {
                mLiveDataLoadTask.setParam(Arrays.asList(objects));
            }
        }
        return this;
    }

    @NonNull
    public final PreTaskLoader<T> setSizeAny(int pageSize, @Nullable List<Object> pairs) {
        if (mLiveDataLoadTask != null) {
            mLiveDataLoadTask.setPageSize(pageSize);
            mLiveDataLoadTask.setParam(pairs);
        }
        return this;
    }

    @NonNull
    public final PreTaskLoader<T> setStartAny(int startIndex, @Nullable Object... objects) {
        if (mLiveDataLoadTask != null) {
            mLiveDataLoadTask.setStartIndex(startIndex);
            if (objects != null) {
                mLiveDataLoadTask.setParam(Arrays.asList(objects));
            }
        }
        return this;
    }

    @NonNull
    public final PreTaskLoader<T> setStartAny(int startIndex, @Nullable List<Object> pairs) {
        if (mLiveDataLoadTask != null) {
            mLiveDataLoadTask.setStartIndex(startIndex);
            mLiveDataLoadTask.setParam(pairs);
        }
        return this;
    }

    @NonNull
    public final PreTaskLoader<T> setIndexAny(int pageIndex, @Nullable Object... objects) {
        if (mLiveDataLoadTask != null) {
            mLiveDataLoadTask.setPageIndex(pageIndex);
            if (objects != null) {
                mLiveDataLoadTask.setParam(Arrays.asList(objects));
            }
        }
        return this;
    }

    @NonNull
    public final PreTaskLoader<T> setIndexAny(int pageIndex, @Nullable List<Object> pairs) {
        if (mLiveDataLoadTask != null) {
            mLiveDataLoadTask.setPageIndex(pageIndex);
            mLiveDataLoadTask.setParam(pairs);
        }
        return this;
    }

    @NonNull
    public final PreTaskLoader<T> setAny(int startIndex, int pageSize, @Nullable Object... objects) {
        if (mLiveDataLoadTask != null) {
            mLiveDataLoadTask.setStartIndex(startIndex);
            mLiveDataLoadTask.setPageSize(pageSize);
            if (objects != null) {
                mLiveDataLoadTask.setParam(Arrays.asList(objects));
            }
        }
        return this;
    }

    @NonNull
    public final PreTaskLoader<T> setAny(int startIndex, int pageSize, @Nullable List<Object> pairs) {
        if (mLiveDataLoadTask != null) {
            mLiveDataLoadTask.setStartIndex(startIndex);
            mLiveDataLoadTask.setPageSize(pageSize);
            mLiveDataLoadTask.setParam(pairs);
        }
        return this;
    }

    public final PreTaskLoader<T> setAny(@Nullable List<Object> pairs) {
        if (mLiveDataLoadTask != null) {
            mLiveDataLoadTask.setParam(pairs);
        }
        return this;
    }

    @NonNull
    public final PreTaskLoader<T> setAny(@Nullable Object... objects) {
        if (mLiveDataLoadTask != null) {
            if (objects != null) {
                mLiveDataLoadTask.setParam(Arrays.asList(objects));
            }
        }
        return this;
    }

    public final void autoRefreshAny(@Nullable Object... objects) {
        if (objects != null) {
            autoRefreshAny(Arrays.asList(objects));
        } else {
            autoRefreshAny(new LinkedList<Object>());
        }
    }

    public final void autoRefreshAny(boolean isThread, @Nullable Object... objects) {
        if (objects != null) {
            autoRefreshAny(isThread, Arrays.asList(objects));
        } else {
            autoRefreshAny(isThread, new LinkedList<Object>());
        }
    }

    public final void autoLoadMoreAny(@Nullable Object... objects) {
        if (objects != null) {
            autoLoadMoreAny(Arrays.asList(objects));
        } else {
            autoLoadMoreAny(new LinkedList<Object>());
        }
    }

    public final void autoLoadMoreAny(boolean isThread, @Nullable Object... objects) {
        if (objects != null) {
            autoLoadMoreAny(isThread, Arrays.asList(objects));
        } else {
            autoLoadMoreAny(isThread, new LinkedList<Object>());
        }
    }

    @SuppressLint("RestrictedApi")
    public final void autoRefreshAnyWork(@Nullable Object... objects) {
        if (objects != null) {
            autoRefreshAnyWork(Arrays.asList(objects));
        } else {
            autoRefreshAnyWork(new LinkedList<Object>());
        }
    }

    @SuppressLint("RestrictedApi")
    public final void autoLoadMoreAnyWork(@Nullable Object... objects) {
        if (objects != null) {
            autoLoadMoreAnyWork(Arrays.asList(objects));
        } else {
            autoLoadMoreAnyWork(new LinkedList<Object>());
        }
    }

    public final void autoRefreshAny(@Nullable List<Object> pairs) {
        if (mLiveDataLoadTask != null) {
            mLiveDataLoadTask.resetPage();
            if (pairs != null) {
                mLiveDataLoadTask.setParam(pairs);
            }
            mLiveDataLoadTask.run();
        }
    }

    public final void autoRefreshAny(boolean isThread, @Nullable List<Object> pairs) {
        if (isThread) {
            autoRefreshAnyWork(pairs);
            return;
        }
        autoRefreshAny(pairs);
    }

    public final void autoLoadMoreAny(@Nullable List<Object> pairs) {
        if (mLiveDataLoadTask != null) {
            mLiveDataLoadTask.addPage();
            if (pairs != null) {
                mLiveDataLoadTask.setParam(pairs);
            }
            mLiveDataLoadTask.run();
        }
    }

    public final void autoLoadMoreAny(boolean isThread, @Nullable List<Object> pairs) {
        if (isThread) {
            autoLoadMoreAnyWork(pairs);
            return;
        }
        autoLoadMoreAny(pairs);
    }

    @SuppressLint("RestrictedApi")
    public final void autoRefreshAnyWork(@Nullable List<Object> pairs) {
        if (mLiveDataLoadTask != null) {
            mLiveDataLoadTask.resetPage();
            if (pairs != null) {
                mLiveDataLoadTask.setParam(pairs);
            }
            ArchTaskExecutor.getInstance().executeOnDiskIO(mLiveDataLoadTask);
        }
    }

    @SuppressLint("RestrictedApi")
    public final void autoLoadMoreAnyWork(@Nullable List<Object> pairs) {
        if (mLiveDataLoadTask != null) {
            mLiveDataLoadTask.addPage();
            if (pairs != null) {
                mLiveDataLoadTask.setParam(pairs);
            }
            ArchTaskExecutor.getInstance().executeOnDiskIO(mLiveDataLoadTask);
        }
    }

    @Nullable
    public final T getData(){
        if (mLiveDataLoadTask == null) {
            return null;
        }
        return mLiveDataLoadTask.getData();
    }

    public final void post() {
        if (mLiveDataLoadTask != null) {
            mLiveDataLoadTask.post();
        }
    }

    @SuppressLint("RestrictedApi")
    public boolean isMainThread() {
        return ArchTaskExecutor.getInstance().isMainThread();
    }

    @SafeVarargs
    @Nullable
    private final Map<String, Object> pairConvertToMap(@Nullable Pair<String, Object>... pairs) {
        if (pairs == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        for (Pair<String, Object> pair : pairs) {
            map.put(pair.first, pair.second);
        }
        return map;
    }


    public void onDestroy() {
        if (mLiveDataLoadTask != null) {
            mLiveDataLoadTask.onDestroy();
            mLiveDataLoadTask = null;
        }
    }
}
