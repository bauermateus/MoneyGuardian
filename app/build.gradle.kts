plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.nav.args)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.google.services)
    alias(libs.plugins.crashlytics)
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.mbs.moneyguardian"
    compileSdk = rootProject.extra.get("compileSdkVersion") as Int

    defaultConfig {
        applicationId = "com.mbs.moneyguardian"
        minSdk = rootProject.extra.get("minSdkVersion") as Int
        targetSdk = rootProject.extra.get("compileSdkVersion") as Int
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //Ads
    implementation(libs.play.services.ads)

    //Firebase
    implementation (platform("com.google.firebase:firebase-bom:32.4.0"))
    implementation(libs.bundles.firebase.tools)

    //Services Auth
    implementation(libs.services.auth)

    //Navigation
    implementation(libs.bundles.navigation)

    //Lifecycle Extensions
    implementation (libs.bundles.lifecycle.extensions)

    //Glide
    implementation (libs.glide)

    //Koin
    implementation (libs.bundles.koin)

    //Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    //DataStore
    implementation(libs.preferences.datastore)

    //Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
}

kapt {
    correctErrorTypes = true
}
