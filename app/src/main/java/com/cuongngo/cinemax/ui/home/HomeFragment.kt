package com.cuongngo.cinemax.ui.home

import android.os.Handler
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.cuongngo.cinemax.R
import com.cuongngo.cinemax.base.fragment.BaseFragmentMVVM
import com.cuongngo.cinemax.base.viewmodel.kodeinViewModel
import com.cuongngo.cinemax.databinding.HomeFragmentBinding
import com.cuongngo.cinemax.ext.observeLiveDataChanged
import com.cuongngo.cinemax.services.network.onResultReceived
import com.cuongngo.cinemax.ui.home.top_slider.SliderAdapter
import kotlin.math.abs

class HomeFragment : BaseFragmentMVVM<HomeFragmentBinding, HomeViewModel>() {

    override val viewModel: HomeViewModel by kodeinViewModel()

    override fun inflateLayout() = R.layout.home_fragment

    private val sliderHandle = Handler()

    override fun setUp() {
        with(binding){
            vpTopViewpager.offscreenPageLimit = 3
            vpTopViewpager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

            val compositePageTransformer = CompositePageTransformer()
            compositePageTransformer.addTransformer(MarginPageTransformer(30))
            compositePageTransformer.addTransformer{ page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.85f + r * 0.25f
            }
            vpTopViewpager.setPageTransformer(compositePageTransformer)

            vpTopViewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    sliderHandle.removeCallbacks(sliderRunnable)
                    sliderHandle.postDelayed(sliderRunnable, 3000)
                }
            })
        }
    }

    private val sliderRunnable= Runnable{
        binding.vpTopViewpager.currentItem = binding.vpTopViewpager.currentItem + 1
    }

    override fun setUpObserver() {
        observeLiveDataChanged(viewModel.listMovie) {
            it.onResultReceived(
                onLoading = {
                    showProgressDialog()
                },
                onSuccess = {
                    hideProgressDialog()
                    binding.vpTopViewpager.adapter = SliderAdapter(
                        it.data?.results ?: return@onResultReceived,
                        binding.vpTopViewpager
                    )
                },
                onError = {
                    hideProgressDialog()
                }
            )
        }
    }

    companion object{
        val TAG = HomeFragment::class.java.simpleName
    }


}