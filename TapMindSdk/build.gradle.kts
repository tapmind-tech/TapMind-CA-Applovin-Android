plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.gradleup.nmcp")
    id("maven-publish")
    id("signing")
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
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("com.google.android.material:material:1.13.0")

    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")

//    compileOnly("com.applovin:applovin-sdk:13.5.0")

    api("com.google.android.gms:play-services-ads:25.0.0")
//    api("com.facebook.android:audience-network-sdk:6.16.0")
//    api("com.unity3d.ads-mediation:mediation-sdk:9.3.0")

    api("com.squareup.retrofit2:retrofit:2.11.0")
    api("com.squareup.retrofit2:converter-gson:2.11.0")

    api("com.google.code.gson:gson:2.13.1")

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
//
//afterEvaluate {
//    publishing {
//        publications {
//            create<MavenPublication>("release") {
//                from(components["release"])
//
//                groupId = "io.github.ravirising26"
//                artifactId = "tapmindsdk"
//                version = "1.0.13"
//
//                pom {
//                    name.set("TapmindSdk")
//                    description.set("TapMind Ads mediation SDK")
//                    url.set("https://github.com/TapMind/TapmindSdk")
//
//                    licenses {
//                        license {
//                            name.set("Apache-2.0")
//                            url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
//                        }
//                    }
//
//                    developers {
//                        developer {
//                            id.set("tapmind")
//                            name.set("Ravi")
//                            email.set("ravi.rising26@gmail.com")
//                        }
//                    }
//
//                    scm {
//                        connection.set("scm:git:github.com/TapMind/TapmindSdk.git")
//                        developerConnection.set("scm:git:ssh://github.com/TapMind/TapmindSdk.git")
//                        url.set("https://github.com/TapMind/TapmindSdk")
//                    }
//                }
//            }
//        }
//    }
//
//    nmcp {
//        publish("release") {
//            username = project.findProperty("ossrhUsername") as String?
//            password = project.findProperty("ossrhPassword") as String?
//            publicationType = "AUTOMATIC"
//        }
//    }
//}
//
//signing {
//    useGpgCmd()
//    sign(publishing.publications)
//}