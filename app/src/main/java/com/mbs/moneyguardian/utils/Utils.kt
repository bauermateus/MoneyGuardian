package com.mbs.moneyguardian.utils

import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup.LayoutParams
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import com.mbs.moneyguardian.R


private var dialog: Dialog? = null

/** Creates a loading progress bar. */
fun startLoad(context: Context) {
    val pb = ProgressBar(context)
    pb.isIndeterminate = true
    pb.indeterminateTintList = ColorStateList
        .valueOf(ContextCompat.getColor(context, R.color.primary))
    dialog = Dialog(context)
    dialog?.let {
        it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        it.addContentView(pb, LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT))
        it.show()
    }
}

/** Dismisses the loading progress bar. */
fun stopLoad() {
    if (dialog != null && dialog!!.isShowing) {
        dialog!!.dismiss()
        dialog = null
    }
}
