package com.mbs.moneyguardian.utils.animations

import android.view.View

internal fun View.animatedRotation(
    degrees: Float,
    animationDuration: Long = 300
) {
    return this.animate()
        .rotation(degrees)
        .setDuration(animationDuration)
        .start()
}