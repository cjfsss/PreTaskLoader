package com.cjf.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.cjf.pre.core.PreTaskImpl
import com.cjf.pre.core.PreTaskLoader

/**
 * <p>Title: BaseViewModel </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/8/8 22:15
 * @version : 1.0
 */
open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    protected open val loader by lazy { LoadController() }

    protected open class LoadController : BasePreController() {

    }

    override fun onCleared() {
        loader.clear()
        super.onCleared()
    }
    /**
     * 获取数据
     * @param tag Any 标识
     * @param preTaskImpl [@kotlin.ExtensionFunctionType] Function2<PreTaskImpl<Any>, [@kotlin.ParameterName] String?, Any>
     * @return PreTaskLoader<Any>
     */
    protected open fun getAny(
        tag: Any,
        preTaskImpl: (PreTaskImpl<Any>.(userId: String?) -> Any)
    ): PreTaskLoader<Any> {
        return loader.getAny(tag, preTaskImpl)
    }
    /**
     * 加载数据
     * @param tag Any 标识
     * @param preTaskImpl [@kotlin.ExtensionFunctionType] Function2<PreTaskImpl<Any>, [@kotlin.ParameterName] String?, Unit>
     * @return PreTaskLoader<Any>
     */
    protected open fun loadAny(
        tag: Any,
        preTaskImpl: (PreTaskImpl<Any>.(userId: String?) -> Unit)
    ): PreTaskLoader<Any> {
        return loader.loadAny(tag, preTaskImpl)
    }

}