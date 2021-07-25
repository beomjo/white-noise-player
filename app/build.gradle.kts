plugins {
    android
    `kotlin-android`
    `kotlin-kapt`
    id("com.google.gms.google-services")
    `kotlin-parcelize`
    id("com.google.firebase.crashlytics")
    id("scabbard.gradle")
    id("dagger.hilt.android.plugin")
    `detekt-setting`
    `ktlint-setting`
}

val version = Project.version

android {
    compileSdkVersion(Version.ANDROID_COMPILE)
    buildToolsVersion = Version.BUILD_TOOL

    defaultConfig {
        applicationId = "com.beomjo.whitenoise"
        minSdkVersion(Version.ANDROID_MIN)
        targetSdkVersion(Version.ANDROID_TARGET)
        vectorDrawables.useSupportLibrary = true
        versionCode = version.code
        versionName = version.name

        testInstrumentationRunner = Dependency.ANDROID_JUNIT_RUNNER
    }

    buildTypes {
        getByName("release") {
            minifyEnabled(true)
            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android-optimize.txt"
                ),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(project(":android-compilation"))

    implementation(Dependency.KOTLIN)
    implementation(Dependency.COROUTINE_CORE)
    implementation(Dependency.COROUTINE_ANDROID)

    implementation(Dependency.KTX_CORE)
    implementation(Dependency.MATERIAL)
    implementation(Dependency.CONSTRAINT_LAYOUT)
    implementation(Dependency.APP_COMPAT)
    implementation(Dependency.LIFECYCLE_LIVEDATA_KTX)
    implementation(Dependency.LIFECYCLE_VIEWMODEL_KTX)
    implementation(Dependency.ACTIVITY_KTX)
    implementation(Dependency.FRAGMENT_KTX)
    implementation(Dependency.FRAGMENT_KTX)
    implementation(Dependency.SWIPE_REFRESH_LAYOUT)
    implementation(Dependency.GOOGLE_MATERIAL)
    implementation(Dependency.GSON)
    implementation(Dependency.LOTTIE)

    implementation(Dependency.GLIDE)
    kapt(Dependency.GLIDE_APT)

    implementation(platform(Dependency.FIREBASE_BOM))
    implementation(Dependency.FIREBASE_AUTH_KTX)
    implementation(Dependency.FIREBASE_FIRESTORE_KTX)
    implementation(Dependency.GMS_PLAY_SERVICE_AUTH)
    implementation(Dependency.FIREBASE_STORAGE_KTX)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.4.3")
    implementation(Dependency.FIREBASE_CRASHLYTICS)

    implementation(Dependency.HILT)
    kapt(Dependency.HILT_APT)

    implementation(Dependency.BINDABLES)
    implementation(Dependency.SHIMMER)
    implementation(Dependency.MEDIA)
    implementation(Dependency.OSS_LISENCE)

    testImplementation(Dependency.JUNIT)
    testImplementation(Dependency.ARCH_CORE)
    testImplementation(Dependency.ANDROIDX_TEST_CORE)
    testImplementation(Dependency.COROUTINE_TEST)
    testImplementation(Dependency.MOCKK)
    testImplementation(Dependency.KOTLIN_REFLECTION)
    androidTestImplementation(Dependency.TEST_RUNNER)
    androidTestImplementation(Dependency.ESPRESSO_CORE)
}
