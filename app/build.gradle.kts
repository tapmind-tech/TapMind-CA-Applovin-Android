plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.tapmind"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.tapmindandroid"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"

//        applicationId = "com.edamame"
//        minSdk = 23
//        targetSdk = 35
//        versionCode = 1
//        versionName = "1.1.1"


//        applicationId = "com.impressiontest"
//        minSdk = 23
//        targetSdk = 35
//        versionCode = 1
//        versionName = "1.0.0"

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

        debug {
            isDebuggable = true
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
        buildConfig = true
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.activity)
//    implementation(project(":TapMindAdapter"))
//
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)

    androidTestImplementation(libs.androidx.espresso.core)

    implementation(project(":customadapter-ironsource"))
    implementation("com.google.android.gms:play-services-ads:23.6.0")
    implementation("com.facebook.android:audience-network-sdk:6.8.0")

    implementation("com.applovin:applovin-sdk:13.5.0")
    implementation("com.unity3d.ads-mediation:mediation-sdk:9.3.0")
//    implementation("com.unity3d.ads:unity-ads:4.12.2")
//    implementation("com.google.android.gms:play-services-appset:16.0.0")
//    implementation("com.google.android.gms:play-services-ads-identifier:18.1.0")

    implementation(project(":customadapter-admob"))
    implementation(project(":customadapter-applovin"))
//    implementation("com.github.tapmind-tech.TapMind-Custom-Adapter-Android:CustomAdapter-admob:1.0.1-alpha01")
//    implementation("com.github.tapmind-tech.TapMind-Custom-Adapter-Android:CustomAdapter-admob:1.0.9")
//    implementation("io.github.ravirising26:customadapter-admob:1.0.8")
}