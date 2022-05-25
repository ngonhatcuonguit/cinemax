package com.cuongngo.cinemax.ui

import com.cuongngo.cinemax.R
import com.cuongngo.cinemax.base.activity.BaseActivity
import com.cuongngo.cinemax.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var homeFragment: HomeFragment
    private lateinit var navView: BottomNavigationView

    override fun inflateLayout(): Int = R.layout.activity_main

    override fun setUp() {

    }

    override fun setUpObserver() {

    }

}