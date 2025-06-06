plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "2.0.21"
}

android {
    namespace = "com.example.moodify"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.moodify"
        minSdk = 28
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


    dependencies {

        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.activity.compose)
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.ui)
        implementation(libs.androidx.ui.graphics)
        implementation(libs.androidx.ui.tooling.preview)
        implementation(libs.androidx.material3)
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.androidx.ui.test.junit4)
        debugImplementation(libs.androidx.ui.tooling)
        debugImplementation(libs.androidx.ui.test.manifest)
        val nav_version = "2.9.0"

        // Jetpack Compose integration
        implementation("androidx.navigation:navigation-compose:$nav_version")

        // Views/Fragments integration
        implementation("androidx.navigation:navigation-fragment:$nav_version")
        implementation("androidx.navigation:navigation-ui:$nav_version")

        // Feature module support for Fragments
        implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")

        // Testing Navigation
        androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")
        // CameraX core library
        implementation ("androidx.camera:camera-core:1.3.0")
        implementation ("androidx.camera:camera-camera2:1.3.0")

        // CameraX Lifecycle support
        implementation ("androidx.camera:camera-lifecycle:1.3.0")

        // CameraX View (includes PreviewView)
        implementation ("androidx.camera:camera-view:1.3.0")

        // CameraX Extensions (optional)
        implementation ("androidx.camera:camera-extensions:1.3.0")
        implementation ("org.tensorflow:tensorflow-lite:2.13.0")
    }

    }