import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlinAndroidKsp)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.compose.compiler)
    id("org.jetbrains.kotlin.kapt")
}

// Read keystore credentials from environment (for CI/CD)
// isNullOrBlank guards against GitHub Actions passing empty strings for unset secrets
val myKeystorePassword: String? = System.getenv("KEYSTORE_PASSWORD")
val myKeyAlias: String? = System.getenv("KEY_ALIAS")
val myKeyPassword: String? = System.getenv("KEY_PASSWORD") ?: myKeystorePassword
val myKeystoreFile = rootProject.file("bolivia_empresas.jks")
val canSign = myKeystoreFile.exists() && !myKeystorePassword.isNullOrBlank() && !myKeyAlias.isNullOrBlank()

android {
    namespace = "com.locototeam.seprecbolivia"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.locototeam.seprecbolivia"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "2026.06.11"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        if (canSign) {
            create("release") {
                storeFile = myKeystoreFile
                storePassword = myKeystorePassword
                keyAlias = myKeyAlias
                keyPassword = myKeyPassword
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = if (canSign) signingConfigs.getByName("release") else signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {

    implementation(project(":core"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    // fragment
    implementation(libs.fragment.ktx)
    // viewmodel compose
    implementation(libs.lifecycle.viewmodel.compose)
    // compose lifecycle
    implementation(libs.lifecycle.runtime.compose)
    // retrofit
    implementation(libs.retrofit2)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.okhttp3)
    implementation(libs.okhttp3.logging.interceptor)
    // dagger hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    // navigation compose
    implementation(libs.navigation.compose)
    implementation(libs.hilt.navigation.compose)
    // work manager
    implementation(libs.work.manager)
    // chucker
    debugImplementation("com.github.chuckerteam.chucker:library:4.3.1")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:4.3.1")
    // map compose
    implementation("com.google.android.gms:play-services-location:21.3.0")
    implementation("com.google.maps.android:maps-compose:8.3.0")
    implementation("com.google.android.gms:play-services-maps:20.0.0")
    // window size class
    implementation("androidx.compose.material3:material3-window-size-class:1.4.0")
}
