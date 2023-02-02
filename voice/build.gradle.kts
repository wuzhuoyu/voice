
plugins {
    id("com.android.library")
    kotlin("android")
    `maven-publish`
}

android {
    namespace = "com.yuu.android.component.voice"
    compileSdk = 32

    defaultConfig {

        minSdk = 21
        targetSdk = 31

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    sourceSets {
        getByName("main") {
            manifest.srcFile("src/main/AndroidManifest.xml")
        }
    }

    buildTypes {
        getByName("release") {
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    packagingOptions {
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/NOTICE.txt")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation ("androidx.core:core-ktx:1.3.2")
    implementation ("androidx.appcompat:appcompat:1.3.0")
    androidTestImplementation ("androidx.test.ext:junit:1.1.2")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.3.0")
}

afterEvaluate {
    publishing{
        publications {
            create<MavenPublication>("release"){
                group = "com.yuu.android.component"
                artifactId = "voice"
                version = "0.0.2-alpha"

                afterEvaluate { artifact(tasks.getByName("bundleReleaseAar")) }
            }
        }
    }
}