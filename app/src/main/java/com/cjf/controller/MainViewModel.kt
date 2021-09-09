package com.cjf.controller

import android.app.Application
import com.cjf.pre.core.PreTaskLoader

/**
 * <p>Title: MainViewModel </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2021/9/9 19:39
 * @version : 1.0
 */
class MainViewModel(application: Application) : BaseViewModel(application) {

    fun getList(): PreTaskLoader<Any> {
        return loadAny("getList"){
            postValue("hello word")
        }
    }
}