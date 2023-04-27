package com.kurly.report.utils

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import coil.load

/**
 *
 * Create : Kwon IkYoung
 * Date : 2023/04/27
 */

@BindingAdapter("bindOriginalPrice", "bindDiscountedPrice", requireAll = false)
fun TextView.setDiscountPrice(originalPrice: Int, discountedPrice: Int? = null) {
    this.text = discountedPrice?.let {
        val persent = "${(((originalPrice - it) / originalPrice.toFloat()) * 100).toInt()}%"
        SpannableString("$persent ${originalPrice}원").apply {
            setSpan(ForegroundColorSpan(Color.parseColor("#FA622F")), (0), persent.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    } ?: run {
        "${originalPrice}원"
    }
}

@BindingAdapter("bindStrikePrice")
fun TextView.setStrikePrice(originalPrice: Int) {
    this.text = SpannableString("${originalPrice}원").apply {
        setSpan(StrikethroughSpan(), 0, this.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}

@BindingAdapter("bindLoadImgWithUrl")
fun ImageView.loadUrl(url: String?) {
    url ?: return
    load(url)
}

@BindingAdapter("changeSelected")
fun View.changeSelected(isSelected: Boolean) {
    this.isSelected = isSelected
}
