
<p align="center"><strong>PreTaskLoader</strong></p>


* #####  使用方法

第一步
```kotlin
abstract class BasePreController : PreController() {
    fun clear() {
        onCleared()
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
```
第二步
```kotlin
open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    protected open val loader by lazy { LoadController() }
    protected open class LoadController : BasePreController() {
    }
    override fun onCleared() {
        loader.clear()
        super.onCleared()
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
```
第三步
```kotlin
class MainViewModel(application: Application) : BaseViewModel(application) {

    fun getList(): PreTaskLoader<Any> {
        return loadAny("getList"){
            postValue("hello word")
        }
    }
}
```
第四步
```kotlin
private val mModel by viewModels<MainViewModel>()
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // 数据监听
    mModel.getList().observe(this){

    }
    // 加载新数据
    mModel.getList().autoRefresh()
}
```

在项目根目录的 build.gradle 添加仓库

```groovy
allprojects {
    repositories {
        // ...
        maven { url 'https://jitpack.io' }
    }
}
```

在 module 的 build.gradle 添加依赖

```groovy
    api 'com.github.cjfsss:PreTaskLoader:0.0.1'
    api 'androidx.appcompat:appcompat:1.3.0'
```

<br>


## License

```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
