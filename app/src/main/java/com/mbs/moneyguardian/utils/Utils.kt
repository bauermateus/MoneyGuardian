package com.mbs.moneyguardian.utils

import android.content.res.ColorStateList
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.isVisible
import com.mbs.moneyguardian.R

fun startLoad(root: ViewGroup) {
    val pb = ProgressBar(root.context)
    pb.isIndeterminate = true
    pb.indeterminateTintList = ColorStateList
        .valueOf(ContextCompat.getColor(root.context, R.color.primary))
    pb.isVisible = true
    val layoutParams = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    )
    pb.tag = "pb"
    pb.id = View.generateViewId()
    if (root is ConstraintLayout) {
        pb.layoutParams = layoutParams
        root.children.forEach {
            it.alpha = 0.4f
        }
        root.addView(pb)
        val constraintSet = ConstraintSet()
        constraintSet.clone(root)
        constraintSet.connect(pb.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
        constraintSet.connect(
            pb.id,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START
        )
        constraintSet.connect(pb.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
        constraintSet.connect(
            pb.id,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM
        )
        constraintSet.applyTo(root)
    } else {
        layoutParams.gravity = Gravity.CENTER
        pb.layoutParams = layoutParams
        root.children.forEach {
            it.alpha = 0.4f
        }
        root.addView(pb)
    }
}

fun stopLoad(root: ViewGroup) {
    val pb: ProgressBar? = root.findViewWithTag("pb")
    if (pb != null) {
        root.removeView(pb)
        root.children.forEach {
            it.alpha = 1f
        }
    }
}
