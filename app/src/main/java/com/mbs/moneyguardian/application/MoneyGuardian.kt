package com.mbs.moneyguardian.application

import android.app.Application
import com.google.firebase.FirebaseApp

class MoneyGuardian: Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}