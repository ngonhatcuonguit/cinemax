package com.cuongngo.cinemax.utils

import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.cuongngo.cinemax.R

fun Drawable.setColorFilter2(@ColorInt color: Int){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        this.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
    } else {
        this.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    }
}

object MyDrawableCompat{
    fun createProgressDrawable(context: Context): Drawable {
        val circularProgressDrawable =  CircularProgressDrawable(context)
        circularProgressDrawable.setColorFilter2(ContextCompat.getColor(context, R.color.blue_accent))
        circularProgressDrawable.centerRadius = context.resources.getDimension(R.dimen._10dp)
        circularProgressDrawable.start()
        return circularProgressDrawable
    }
    fun createMatchParentProgressDrawable(context: Context): Drawable {
        val circularProgressDrawable =  CircularProgressDrawable(context)
        circularProgressDrawable.setColorFilter2(ContextCompat.getColor(context, R.color.blue_accent))
        circularProgressDrawable.centerRadius = 0F
        circularProgressDrawable.start()
        return circularProgressDrawable
    }
}
