package com.cuongngo.cinemax.ui.onboard

import android.content.Intent
import androidx.viewpager2.widget.ViewPager2
import com.cuongngo.cinemax.AppPreferences.setShowOnBoard
import com.cuongngo.cinemax.R
import com.cuongngo.cinemax.base.activity.BaseActivity
import com.cuongngo.cinemax.databinding.ActivityOnboardBinding
import com.cuongngo.cinemax.ui.MainActivity

class OnBoardActivity : BaseActivity<ActivityOnboardBinding>() {

    override fun inflateLayout(): Int = R.layout.activity_onboard

    override fun setUp() {

        binding.vpOnBoard.apply {
            val listOnBoard = listOf(
                OnBoardingModel(R.drawable.image_onboard_step1),
                OnBoardingModel(R.drawable.image_onboard_step2),
                OnBoardingModel(R.drawable.image_onboard_step3)
            )
            adapter = OnBoardAdapter(listOnBoard)
        }

        binding.flSkip.setOnClickListener {
            setShowOnBoard(true)
            gotoMain()
        }

        binding.btnNext.setOnClickListener {
            val current = binding.vpOnBoard.currentItem
            val total = binding.vpOnBoard.adapter?.itemCount ?: 3
            if (current < total-1) {
                binding.vpOnBoard.currentItem = current + 1
                when (current) {
                    2 -> {
                        binding.tvOnboardSlogan.text = "Offers ad-free viewing of high quality"
                        binding.tvContent.text =
                            "Semper in cursus magna et eu varius nunc adipiscing. Elementum justo, laoreet id sem semper parturient."
                    }
                    3 -> {
                        binding.tvOnboardSlogan.text =
                            "Our service brings together your favorite series"
                        binding.tvContent.text =
                            "Semper in cursus magna et eu varius nunc adipiscing. Elementum justo, laoreet id sem semper parturient."
                    }
                }
            } else {
                setShowOnBoard(true)
                gotoMain()
            }
        }

        binding.dotsIndicator.attachTo(binding.vpOnBoard)

        binding.vpOnBoard.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        binding.tvOnboardSlogan.text =
                            "The biggest international and local film streaming"
                        binding.tvContent.text =
                            "Semper in cursus magna et eu varius nunc adipiscing. Elementum justo, laoreet id sem semper parturient."
                    }
                    1 -> {
                        binding.tvOnboardSlogan.text = "Offers ad-free viewing of high quality"
                        binding.tvContent.text =
                            "Semper in cursus magna et eu varius nunc adipiscing. Elementum justo, laoreet id sem semper parturient."
                    }
                    2 -> {
                        binding.tvOnboardSlogan.text =
                            "Our service brings together your favorite series"
                        binding.tvContent.text =
                            "Semper in cursus magna et eu varius nunc adipiscing. Elementum justo, laoreet id sem semper parturient."
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

        })
    }

    private fun gotoMain() {
        Intent(this, MainActivity::class.java).apply {
        }.also {
            finish()
            startActivity(it)
        }
    }

    override fun onBackPressed() {
        //do nothing
    }

    override fun setUpObserver() {

    }
}