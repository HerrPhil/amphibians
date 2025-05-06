plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // plug-in for Kotlin Serialization - use most recent version
    alias(libs.plugins.kotlin.serialization)

}

android {
    namespace = "com.example.amphibians"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.amphibians"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Getting data from the internet
    // Retrofit
    implementation(libs.retrofit)
    // Retrofit with Scalar Converter
    // 11th re-factoring: remove; replaced by Kotlinx serialization converter, below
//    implementation(libs.converter.scalars)
    // Retrofit with Kotlin serialization
    implementation(libs.kotlinx.serialization.json)
    // Jack Wharton Retrofit with Kotlinx serialization converter
    implementation(libs.kotlinx.serialization.converter)
    // Dependency of Jack Wharton Retrofit library
    implementation(libs.okhttp3)
    // Coil to download, buffer, decode and cache the images; show as composable
    implementation(libs.coil)

    testImplementation(libs.junit)
    // 24th re-factoring: test implementation for coroutine test (for fake network calls)
    testImplementation(libs.kotlinx.coroutines.test)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}