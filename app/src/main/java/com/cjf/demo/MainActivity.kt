package com.cjf.demo

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.cjf.controller.MainViewModel

/**
 * <p>Title: MainActivity </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2021/9/9 19:40
 * @version : 1.0
 */
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val mModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 数据监听
        mModel.getList().observe(this){

        }
        // 加载新数据
        mModel.getList().autoRefresh()
    }
}