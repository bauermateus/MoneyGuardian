

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.nav.args)  apply false
    alias(libs.plugins.crashlytics)  apply false
    id("com.google.dagger.hilt.android") version "2.48.1" apply false
}

extra.apply{
    set("minSdkVersion", 24)
    set("compileSdkVersion", 34)
}