package com.cuongngo.cinemax.ui.view_pager

import android.view.View
import androidx.core.view.ViewCompat
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.cuongngo.cinemax.App
import com.cuongngo.cinemax.R
import com.cuongngo.cinemax.utils.getScreenWidth
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.properties.Delegates

class ViewPagerHelper(
    private val viewPager2: ViewPager2,
    private val viewPagerAdapter: ViewPagerAdapter,
    private val defaultPos: Int,
    private val onPageChanged: (currentPosition: Int) -> Unit
) {
    companion object {
        private var jobHome: Job? by Delegates.observable(null) { _, old, _ ->
            old?.cancel()
        }
    }

    init {
        viewPager2.addOnAttachStateChangeListener(
            object : View.OnAttachStateChangeListener {
                override fun onViewAttachedToWindow(p0: View?) {
                    isViewPagerAttached = true
                }

                override fun onViewDetachedFromWindow(v: View?) {
                    isViewPagerAttached = false
                }
            }
        )
    }

    private val defaultPage = if (defaultPos == 0) viewPagerAdapter.itemCount / 3 else defaultPos
    private val offscreen = 1
    private var isIdling = true
    private var isViewPagerAttached = true
    private var activePos = -1

    private val MAX_ALPHA = 1.0f
    private val MIN_ALPHA = 0.5f
    private val MAX_SCALE = 1f
    private val SCALE_PERCENT = 0.9f
    private val MIN_SCALE = SCALE_PERCENT * MAX_SCALE


    private val pageChangeCallBack = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            val realPos = viewPagerAdapter.getRealPosition(position)
            if (activePos != realPos) {
                onPageChanged.invoke(realPos)
                activePos = realPos
            }
        }

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                isIdling = true
                val realItemCount = viewPagerAdapter.getRealItemCount()
                val position = viewPager2.currentItem
                val firstMainIndexes = viewPagerAdapter.itemCount / 3
                val lastMainIndexes = viewPagerAdapter.itemCount / 3 + realItemCount - 1

                if (position <= firstMainIndexes) {
                    //swipe left -> right
                    viewPager2.setCurrentItem(viewPager2.currentItem + realItemCount, false)
                } else if (position >= lastMainIndexes) {
                    //swipe right -> left
                    viewPager2.setCurrentItem(viewPager2.currentItem % realItemCount, false)
                }
            } else {
                isIdling = true
            }
        }
    }

    fun execute() {
        with(viewPager2) {
            setTransformer(this)
            adapter = viewPagerAdapter
            registerOnPageChangeCallback(pageChangeCallBack)
            if (defaultPage in 0 until viewPagerAdapter.itemCount) {
                viewPager2.setCurrentItem(defaultPage, false)
            }
        }
    }

    private fun setTransformer(viewPager2: ViewPager2) {
        with(viewPager2) {
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            clipToPadding = false
            clipChildren = false
            // retain 1 page on each size
            offscreenPageLimit = offscreen
            val screenWidth = resources.displayMetrics.widthPixels
            val nextItemTranslationX = 0.2319f *screenWidth
            setPageTransformer { page, position ->
                // position  -1: left, 0: center, 1: right
                val absPosition = abs(position)
                // alpha from MIN_ALPHA to MAX_ALPHA
                page.alpha = MAX_ALPHA - (MAX_ALPHA - MIN_ALPHA) * absPosition
                // scale from MIN_SCALE to MAX_SCALE
                val scale = MAX_SCALE - (MAX_SCALE - MIN_SCALE) * absPosition
                page.scaleY = scale
                page.scaleX = scale
                // translation X
                page.translationX = -position * nextItemTranslationX
            }
        }
    }

    fun autoScroll(lifecycleScope: LifecycleCoroutineScope, interval: Long): ViewPagerHelper {
        jobHome = exeJobIndefinitely(lifecycleScope, interval)
        return this
    }

    private fun exeJobIndefinitely(lifecycleScope: LifecycleCoroutineScope, interval: Long) =
        lifecycleScope.launchWhenResumed {
            scrollIndefinitely(interval)
        }

    private suspend fun scrollIndefinitely(interval: Long) {
        if (isIdling) {
            delay(interval)
            if (isViewPagerAttached) {
                val nextItem = viewPager2.currentItem + 1
                viewPager2.setCurrentItem(nextItem, true)
            }
            scrollIndefinitely(interval)
        }
    }

}