plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.ironsource.adapters.custom.istapmindcustomadapter"
    compileSdk = 36

    defaultConfig {
        minSdk = 23

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

    sourceSets {
        named("main") {
            resources.srcDirs("src/main/resources")
        }
    }
}

dependencies {
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

//    add("api", "com.unity3d.ads-mediation:mediation-sdk:9.3.0")
    api("com.unity3d.ads-mediation:mediation-sdk:9.3.0")
//    api("com.unity3d.ads:unity-ads:4.12.2")
//    compileOnly("com.unity3d.ads:mediation-sdk-banner:9.3.0")
//    compileOnly("com.unity3d.mediation:mediation-sdk-interstitial:9.3.0")
//    compileOnly("com.unity3d.mediation:mediation-sdk-rewarded:9.3.0")
//    implementation(project(":customadapter-admob"))
    implementation(project(":TapMindSdk"))
    compileOnly("com.google.android.gms:play-services-ads:23.6.0")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])

                groupId = "com.github.TapMind"
                artifactId = "CustomAdapter-ironSource"
                version = "1.0.0"
            }
        }
    }
}