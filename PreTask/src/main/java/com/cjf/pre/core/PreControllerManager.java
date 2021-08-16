package com.cjf.pre.core;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;

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
public final class PreControllerManager {

    @NonNull
    private final Map<Object, PreController> taskMap = new ConcurrentHashMap<>();

    @NonNull
    public <T extends PreController> T put(@NonNull Object tag, @NonNull T viewModel) {
        taskMap.put(tag, viewModel);
        return viewModel;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public <T extends PreController> T getByTag(@NonNull Object tag) {
        if (!taskMap.containsKey(tag)) {
            return null;
        }
        return (T) taskMap.get(tag);
    }

    @NonNull
    public <T extends PreController> T getByTag(@NonNull Object tag, @NonNull Function<Object, T> function) {
        T controller = getByTag(tag);
        if (controller != null) {
            return controller;
        }
        return put(tag, function.apply(tag));
    }

    @NonNull
    public <T extends PreController> T put(@NonNull T viewModel) {
        put(viewModel.getClass().getSimpleName(), viewModel);
        return viewModel;
    }

    @Nullable
    public <T extends PreController> T get(@NonNull Class<T> clazz) {
        String simpleName = clazz.getSimpleName();
        return clazz.cast(getByTag(simpleName));
    }

    @NonNull
    public <T extends PreController> T get(@NonNull Class<T> clazz, @NonNull Function<Class<T>, T> function) {
        T controller = get(clazz);
        if (controller != null) {
            return controller;
        }
        return put(function.apply(clazz));
    }

    public boolean exists(@NonNull Object tag) {
        return taskMap.containsKey(tag);
    }

    public void clear(@NonNull Class<?> clazz) {
        String simpleName = clazz.getSimpleName();
        clear(simpleName);
    }

    public void clear(@Nullable Object... tags) {
        if (tags == null) {
            return;
        }
        for (Object tag : tags) {
            if (!taskMap.containsKey(tag)) {
                continue;
            }
            PreController viewModel = taskMap.get(tag);
            if (viewModel != null) {
                viewModel.onCleared();
                taskMap.remove(tag);
            }
        }
    }

    public void onDestroy() {
        if (taskMap == null) {
            return;
        }
        Set<Object> keySet = taskMap.keySet();
        if (keySet == null) {
            return;
        }
        for (Object key : keySet) {
            PreController liveDataLoadTask = taskMap.get(key);
            if (liveDataLoadTask != null) {
                liveDataLoadTask.onCleared();
            }
        }
        taskMap.clear();
    }
}
