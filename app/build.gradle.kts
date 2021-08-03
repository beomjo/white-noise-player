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
    id("com.google.android.gms.oss-licenses-plugin")
}

val version = Project.Version.value

android {
    compileSdk = Project.Config.ANDROID_COMPILE
    buildToolsVersion = Project.Config.BUILD_TOOL

    defaultConfig {
        applicationId = "com.beomjo.whitenoise"
        minSdk = Project.Config.ANDROID_MIN
        targetSdk = Project.Config.ANDROID_TARGET
        vectorDrawables.useSupportLibrary = true
        versionCode = version.code
        versionName = version.name

        testInstrumentationRunner = Dependency.Test.ANDROID_JUNIT_RUNNER
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android-optimize.txt"
                ),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(project(":android-compilation"))

    implementation(Dependency.Kotlin.SDK)
    implementation(Dependency.Kotlin.COROUTINE_CORE)
    implementation(Dependency.Kotlin.COROUTINE_ANDROID)
    implementation(Dependency.Kotlin.COROUTINE_PLAY_SERVICE)

    implementation(Dependency.AndroidX.MATERIAL)
    implementation(Dependency.AndroidX.CONSTRAINT_LAYOUT)
    implementation(Dependency.AndroidX.APP_COMPAT)
    implementation(Dependency.AndroidX.SWIPE_REFRESH_LAYOUT)
    implementation(Dependency.AndroidX.MEDIA)

    implementation(Dependency.KTX.CORE)
    implementation(Dependency.KTX.LIFECYCLE_LIVEDATA)
    implementation(Dependency.KTX.LIFECYCLE_VIEWMODEL)
    implementation(Dependency.KTX.ACTIVITY)
    implementation(Dependency.KTX.FRAGMENT)

    implementation(platform(Dependency.Firebase.BOM))
    implementation(Dependency.Firebase.AUTH)
    implementation(Dependency.Firebase.FIRESTORE)
    implementation(Dependency.Firebase.GMS_PLAY_SERVICE_AUTH)
    implementation(Dependency.Firebase.STORAGE)
    implementation(Dependency.Firebase.CRASHLYTICS)

    implementation(Dependency.Google.MATERIAL)
    implementation(Dependency.Google.GSON)
    implementation(Dependency.Google.OSS_LISENCE)

    implementation(Dependency.Glide.CORE)
    kapt(Dependency.Glide.APT)

    implementation(Dependency.Hilt.CORE)
    kapt(Dependency.Hilt.APT)

    implementation(Dependency.Lottie.CORE)

    implementation(Dependency.BINDABLES)
    implementation(Dependency.SHIMMER)
    implementation(Dependency.GOOGLE_BUTTON)

    testImplementation(Dependency.Test.JUNIT)
    testImplementation(Dependency.Test.ARCH_CORE)
    testImplementation(Dependency.Test.ANDROIDX_TEST_CORE)
    testImplementation(Dependency.Test.COROUTINE_TEST)
    testImplementation(Dependency.Test.MOCKK)
    testImplementation(Dependency.Kotlin.REFLECTION)
    androidTestImplementation(Dependency.AndroidTest.TEST_RUNNER)
    androidTestImplementation(Dependency.AndroidTest.ESPRESSO_CORE)
}
