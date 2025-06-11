plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

    id("kotlin-parcelize")
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

    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")


    // incluir essa dependencia para o oneAgent funcionar
    implementation("com.dynatrace.agent:agent-android:8.315.1.1005")

    // Dependencias para openTelemetry
//    implementation("io.opentelemetry:opentelemetry-api-logs:1.26.0-alpha")
//    implementation("io.opentelemetry:opentelemetry-sdk-logs:1.26.0-alpha")
////    implementation("io.opentelemetry:opentelemetry-sdk-logs:1.51.0")
//    implementation("io.opentelemetry:opentelemetry-exporter-otlp-logs:1.26.0-alpha")

    //    // If you specifically need gRPC and its dependencies:
//    implementation ("io.grpc:grpc-okhttp:1.59.0") // Or latest, check compatibility
//    implementation ("io.grpc:grpc-protobuf-lite:1.59.0")
//    implementation ("io.grpc:grpc-stub:1.59.0")




}