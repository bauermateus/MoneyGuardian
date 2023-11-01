package com.mbs.moneyguardian.utils.animations

import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation

internal fun View.animatedRotation(degrees: Float, animationDuration: Long = 1000, reverse: Boolean = false) {
    return this.startAnimation(RotateAnimation(
        0f,
        degrees,
        Animation.RELATIVE_TO_SELF,
        0.5f,
        Animation.RELATIVE_TO_SELF,
        0.5f
    ).apply {
        duration = animationDuration
        interpolator = LinearInterpolator()
    })
}