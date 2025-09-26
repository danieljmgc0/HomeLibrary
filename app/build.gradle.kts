import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
}

var secretsProperties = Properties()
secretsProperties.load(FileInputStream(rootProject.file("local.properties")))

android {
    namespace = "com.knighttech.homelibrary"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.knighttech.homelibrary"
        minSdk = 30
        targetSdk = 36
        versionCode = 3
        versionName = "1.5"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "BOOKS_APIKEY", secretsProperties.getProperty("BOOKS_APIKEY"))
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
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.room.common.jvm)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("androidx.compose.material3:material3:1.1.0-alpha02")

    implementation(libs.androidx.camera.core)
    implementation("androidx.camera:camera-camera2:1.2.3")
    implementation("androidx.camera:camera-lifecycle:1.2.3")
    implementation("androidx.camera:camera-view:1.2.3")
    implementation("com.google.mlkit:barcode-scanning:17.0.2")
    implementation("com.google.accompanist:accompanist-permissions:0.31.5-beta")

    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    implementation("com.google.code.gson:gson:2.10.1")

    implementation("androidx.room:room-runtime:2.8.0")
    //runtimeOnly("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.9.0")
    kapt("androidx.room:room-compiler:2.8.0")
    implementation("androidx.room:room-ktx:2.8.0")


    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
}


