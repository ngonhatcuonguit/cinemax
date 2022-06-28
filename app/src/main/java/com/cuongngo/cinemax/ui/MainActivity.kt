package com.cuongngo.cinemax.ui

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.fragment.app.FragmentManager
import com.cuongngo.cinemax.R
import com.cuongngo.cinemax.base.activity.BaseActivity
import com.cuongngo.cinemax.databinding.ActivityMainBinding
import com.cuongngo.cinemax.roomdb.AppDatabase
import com.cuongngo.cinemax.ui.home.HomeFragment
import com.cuongngo.cinemax.ui.profile.ProfileFragment
import com.cuongngo.cinemax.ui.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var homeFragment: HomeFragment
    private lateinit var navView: BottomNavigationView

    override fun inflateLayout(): Int = R.layout.activity_main

    private var currentFragment = HomeFragment::class.java.simpleName

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
                    val searchFragment = fragmentManager.findFragmentByTag(SearchFragment.TAG)
                    val profileFragment = fragmentManager.findFragmentByTag(ProfileFragment.TAG)
                    if (searchFragment != null) {
                        transaction.remove(searchFragment)
                    }
                    if (profileFragment != null) transaction.remove(profileFragment)
                    transaction.commit()
                    currentFragment = HomeFragment.TAG
                }
                R.id.navigation_search -> {
                    val transaction = fragmentManager.beginTransaction()
                    transaction.hide(homeFragment)
                    val searchFragment = fragmentManager.findFragmentByTag(SearchFragment.TAG)
                    if (searchFragment == null){
                        transaction.add(
                            R.id.container,
                            SearchFragment(),
                            SearchFragment.TAG
                        )
                        transaction.commit()
                        currentFragment = SearchFragment.TAG
                    }else {

                    }
                }
                R.id.navigation_profile -> {
                    val transaction = fragmentManager.beginTransaction()
                    transaction.hide(homeFragment)
                    val profileFragment = fragmentManager.findFragmentByTag(ProfileFragment.TAG)
                    val searchFragment = fragmentManager.findFragmentByTag(SearchFragment.TAG)
                    if (searchFragment != null) {
                        transaction.remove(searchFragment)
                    }
                    if (profileFragment == null){
                        transaction.add(
                            R.id.container,
                            ProfileFragment(),
                            ProfileFragment.TAG
                        )
                        transaction.commit()
                        currentFragment = ProfileFragment.TAG
                    }else {

                    }
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