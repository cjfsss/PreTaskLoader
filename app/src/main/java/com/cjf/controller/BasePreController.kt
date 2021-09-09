package com.cjf.controller

import com.cjf.pre.core.PreController
import com.cjf.pre.core.PreTaskImpl
import com.cjf.pre.core.PreTaskLoader
import com.cjf.pre.interfaces.PreRunnable

/**
 * <p>Title: BasePreController </p>
 * <p>Description: 预加载 </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2021/9/9 8:55
 * @version : 1.0
 */
abstract class BasePreController : PreController() {

    fun clear() {
        onCleared()
    }

    /**
     * 获取数据
     * @param tag Any 标识
     * @param preTaskImpl [@kotlin.ExtensionFunctionType] Function2<PreTaskImpl<Any>, [@kotlin.ParameterName] String?, Any>
     * @return PreTaskLoader<Any>
     */
    open fun getAny(
        tag: Any,
        preTaskImpl: (PreTaskImpl<Any>.(userId: String?) -> Any)
    ): PreTaskLoader<Any> {
        return get<Any>(tag) {
            return@get PreRunnable {
                val dataList = preTaskImpl.invoke(it, "currentUserId")
                it.postValue(dataList)
            }
        }
    }

    /**
     * 加载数据
     * @param tag Any 标识
     * @param preTaskImpl [@kotlin.ExtensionFunctionType] Function2<PreTaskImpl<Any>, [@kotlin.ParameterName] String?, Unit>
     * @return PreTaskLoader<Any>
     */
    open fun loadAny(
        tag: Any,
        preTaskImpl: (PreTaskImpl<Any>.(userId: String?) -> Unit)
    ): PreTaskLoader<Any> {
        return get<Any>(tag) {
            return@get PreRunnable {
                preTaskImpl.invoke(it, "currentUserId")
            }
        }
    }
}