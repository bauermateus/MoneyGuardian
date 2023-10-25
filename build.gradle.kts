buildscript {
    repositories {
        google()
    }
    dependencies {
        val navVersion = "2.7.4"
        val crashLyticsVersion = "2.9.9"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
        classpath ("com.google.firebase:firebase-crashlytics-gradle:$crashLyticsVersion")
    }
}
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
}