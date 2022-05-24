package com.cuongngo.cinemax.moviecustomview

import android.content.Context
import android.util.AttributeSet

class ImagePoster : carbon.widget.ImageView {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec + (widthMeasureSpec.toFloat() * 1 / 2.toFloat()).toInt())
    }
}