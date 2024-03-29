plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("maven-publish")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.araditc.anc"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation ("androidx.work:work-runtime-ktx:2.9.0")
    implementation(platform("com.google.firebase:firebase-bom:32.7.4"))
    implementation("com.google.firebase:firebase-messaging:23.4.1")
    implementation("androidx.security:security-crypto:1.1.0-alpha06")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.2.5")
    implementation ("androidx.room:room-runtime:2.6.1")
    annotationProcessor ("androidx.room:room-compiler:2.6.1")
    ksp ("androidx.room:room-compiler:2.6.1")
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.github.araditc"
            artifactId = "push-notification-sdk"
            version = "1.2.5"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}