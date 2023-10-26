plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}


android {
    namespace = "com.mbs.moneyguardian.core.data"
    compileSdk = rootProject.extra.get("compileSdkVersion") as Int
}

dependencies {

}