package com.mbs.moneyguardian.utils.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(text: String) {
    Toast.makeText(this.requireContext(), text, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(stringResource: Int) {
    Toast.makeText(this.requireContext(), stringResource, Toast.LENGTH_SHORT).show()
}