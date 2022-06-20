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

    private var pageMarginRight = App.getResources().getDimension(R.dimen.banner_spacing)
    private var pageMarginLeft = App.getResources().getDimension(R.dimen.banner_margin_offset)
    private val offscreen = 3
    private val remainRightSpace =
        getScreenWidth().toFloat() - App.getResources().getDimension(R.dimen.banner_width)
    private val defaultPage = if (defaultPos == 0) viewPagerAdapter.itemCount / 3 else defaultPos

    private var isIdling = true
    private var isViewPagerAttached = true
    private var activePos = -1

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
            offscreenPageLimit = offscreen //3
            registerOnPageChangeCallback(pageChangeCallBack)
            if (defaultPage in 0 until viewPagerAdapter.itemCount) {
                viewPager2.setCurrentItem(defaultPage, false)
            }
        }
    }

    private fun setTransformer(viewPager2: ViewPager2) {
        val nextPagePreShow = remainRightSpace - pageMarginRight
        with(viewPager2) {
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            setPageTransformer { page, position ->
                val myOffset: Float = calculatePageMarginOffset(
                    position = position,
                    marginLeft = pageMarginLeft,
                    marginRight = nextPagePreShow,
                )
                if (orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
                    if (ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                        page.translationX = -myOffset
                    } else {
                        page.translationX = myOffset
                    }
                } else {
                    page.translationY = myOffset
                }
            }
        }
    }

    private fun calculatePageMarginOffset(
        position: Float,
        marginLeft: Float,
        marginRight: Float
    ): Float {
        return marginLeft + position * -marginRight
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