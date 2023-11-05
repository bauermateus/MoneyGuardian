package com.mbs.moneyguardian.utils.extensions

fun String.isValidEmail(): Boolean {
    val regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$".toRegex()
    return regex.matches(this)
}