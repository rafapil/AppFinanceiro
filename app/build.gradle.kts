plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

    id("kotlin-parcelize")

    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0"
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
    ndkVersion = "27.2.12479018"
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

    implementation(libs.okhttp)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.lifecycle.runtime)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.dynatrace.agent)
    /*
    * config para opentelemetry
    * */
    implementation (platform("io.opentelemetry:opentelemetry-bom:1.25.0"))
    implementation ("io.opentelemetry:opentelemetry-context")
    implementation ("io.opentelemetry:opentelemetry-api")
    implementation ("io.opentelemetry:opentelemetry-exporter-otlp:1.24.0")
    implementation ("io.opentelemetry:opentelemetry-exporter-logging")
    implementation ("io.opentelemetry:opentelemetry-extension-kotlin")
    implementation ("io.opentelemetry:opentelemetry-sdk")
    implementation ("io.opentelemetry:opentelemetry-semconv")

//    implementation ("io.opentelemetry.instrumentation:opentelemetry-okhttp-3.0:1.28.0-alpha")

}