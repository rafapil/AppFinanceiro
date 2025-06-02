// necessário add essa configuracao
//buildscript {
//    dependencies {
//        classpath("com.dynatrace.tools.android:gradle-plugin:8.+")
//    }
//}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-parcelize")

    // adicionar estas configuracoes
//    id("com.android.application") version "8.1.0" apply false
//    id("com.android.library") version "8.1.0" apply false
}

android {
    namespace = "com.projetos.appfinanceiro"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.projetos.appfinanceiro"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.circularprogressbar)
    implementation(libs.glide)

    implementation("com.dynatrace.agent:agent-android:8.+")

}