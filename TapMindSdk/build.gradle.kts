plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.tapminds"
    compileSdk = 36

    defaultConfig {
        minSdk = 23
        version = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("com.google.android.material:material:1.13.0")

    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")

//    compileOnly("com.applovin:applovin-sdk:13.5.0")

    compileOnly("com.google.android.gms:play-services-ads:23.6.0")
    compileOnly("com.facebook.android:audience-network-sdk:6.16.0")
    compileOnly("com.unity3d.ads-mediation:mediation-sdk:9.3.0")

    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    implementation("com.google.code.gson:gson:2.13.1")

//    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1")

//    compileOnly("com.applovin.mediation:google-adapter:24.7.0.0")
//    compileOnly("com.applovin.mediation:facebook-adapter:6.21.0.0")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])

                groupId = "com.github.TapMind"
                artifactId = "TapmindSdk"
                version = "1.0.0"
            }
        }
    }
}