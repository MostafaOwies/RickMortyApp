plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.com.google.dagger.hilt.android)
    id("org.jetbrains.kotlin.kapt")
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.firebase.crashlytics) // Apply the kapt plugin in Kotlin DSL

}

android {
    namespace = "com.aqua_waterfliter.rickmorty"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.aqua_waterfliter.rickmorty"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"https://rickandmortyapi.com/api/\"")

        }
        debug {
            buildConfigField("String", "BASE_URL", "\"https://rickandmortyapi.com/api/\"")
        }
    }
    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    implementation(libs.firebase.crashlytics.ktx)
    implementation(libs.androidx.material3)
    implementation(libs.google.firebase.auth)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Navigation
    implementation(libs.androidx.navigation.compose)

    // DI
    implementation(libs.com.google.dagger.hilt.android)
    implementation(libs.javapoet)
    kapt(libs.com.google.dagger.hilt.compiler)


    // hilt-navigation-compose
    implementation(libs.androidx.hilt.navigation.compose)

    // Network
    implementation(libs.com.squareup.retrofit2.retrofit)
    implementation(libs.com.squareup.retrofit2.converter.moshi)
    implementation(libs.com.squareup.okhttp3.okhttp)
    implementation(libs.com.squareup.okhttp3.logging.interceptor)
    implementation(libs.com.squareup.moshi.kotlin)
    kapt(libs.com.squareup.moshi.kotlin.codegen)

    // Timber logger
    implementation(libs.com.jakewharton.timber)

    // accompanist
    implementation(libs.com.google.accompanist.permissions)
    implementation(libs.com.google.accompanist.navigation.animation)

    //coroutines
    implementation(libs.org.jetbrains.kotlinx.coroutines.play.services)

    //coil
    implementation(libs.coil.compose)

    //swipe refresh
    implementation(libs.accompanist.swiperefresh)

    //paging
    implementation(libs.paging)
    implementation(libs.paging.compose)

}
kapt {
    correctErrorTypes = true
}
hilt {
    enableAggregatingTask = false
}

