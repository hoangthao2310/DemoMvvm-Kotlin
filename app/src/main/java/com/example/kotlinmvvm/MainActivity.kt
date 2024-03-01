package com.example.kotlinmvvm

import com.example.kotlinmvvm.base.BaseActivity
import com.example.kotlinmvvm.databinding.ActivityMainBinding
import com.example.kotlinmvvm.view.FoodTypeFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun initViews() {
        showFragment(this::class.java, FoodTypeFragment::class.java, 0, 0)
    }

    override fun getLayout(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
}