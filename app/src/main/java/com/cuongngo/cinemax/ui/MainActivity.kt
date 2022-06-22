package com.cuongngo.cinemax.ui

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.fragment.app.FragmentManager
import com.cuongngo.cinemax.R
import com.cuongngo.cinemax.base.activity.BaseActivity
import com.cuongngo.cinemax.databinding.ActivityMainBinding
import com.cuongngo.cinemax.ui.home.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var homeFragment: HomeFragment
    private lateinit var navView: BottomNavigationView

    override fun inflateLayout(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableLightStatusBar()
    }

    override fun setUp() {
        navView = findViewById(R.id.nav_bottom)
        val fragmentManager: FragmentManager = supportFragmentManager
        homeFragment = HomeFragment()
        fragmentManager.beginTransaction()
            .replace(R.id.frame_container, homeFragment, HomeFragment.TAG).commit()

        navView.setOnNavigationItemSelectedListener {
            if (navView.selectedItemId == it.itemId) {
                if (it.itemId == R.id.navigation_home) {
                    val transaction = fragmentManager.beginTransaction()
                    transaction.commit()
                }
                return@setOnNavigationItemSelectedListener false
            }
            when (it.itemId) {
                R.id.navigation_home -> {
                    val transaction = fragmentManager.beginTransaction()
                    transaction.show(homeFragment)
//                    val homeFragment = fragmentManager.findFragmentByTag(HomeFragment.TAG)
//                    if (homeFragment != null) {
//                        transaction.remove(homeFragment)
//                    }
                    transaction.commit()
                }


            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun setUpObserver() {

    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val view: View? = currentFocus
        val ret = super.dispatchTouchEvent(event)
        if (view is EditText) {
            currentFocus?.let {
                val w: View = it
                val scrcoords = IntArray(2)
                w.getLocationOnScreen(scrcoords)
                val x: Float = event.rawX + w.left - scrcoords[0]
                val y: Float = event.rawY + w.top - scrcoords[1]
                if (event.action == MotionEvent.ACTION_UP
                    && (x < w.left || x >= w.right || y < w.top || y > w.bottom)
                ) {
                    hideKeyboard()

                }
            }
        }
        return ret
    }

}