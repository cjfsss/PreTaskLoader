package com.cjf.pre.interfaces;

import com.cjf.pre.core.PreTaskLoader;

/**
 * <p>Title: PreTaskLoaderListener </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/7/2 15:31
 */
public interface PreTaskLoaderListener {

    void initTask(PreTaskLoader<?> taskLoader);
}
