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
    compileSdkVersion(Project.Config.ANDROID_COMPILE)
    buildToolsVersion = Project.Config.BUILD_TOOL

    defaultConfig {
        applicationId = "com.beomjo.whitenoise"
        minSdkVersion(Project.Config.ANDROID_MIN)
        targetSdkVersion(Project.Config.ANDROID_TARGET)
        vectorDrawables.useSupportLibrary = true
        versionCode = version.code
        versionName = version.name

        testInstrumentationRunner = Dependencies.Test.ANDROID_JUNIT_RUNNER
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

    implementation(Dependencies.Kotlin.SDK)
    implementation(Dependencies.Kotlin.COROUTINE_CORE)
    implementation(Dependencies.Kotlin.COROUTINE_ANDROID)
    implementation(Dependencies.Kotlin.COROUTINE_PLAY_SERVICE)

    implementation(Dependencies.AndroidX.MATERIAL)
    implementation(Dependencies.AndroidX.CONSTRAINT_LAYOUT)
    implementation(Dependencies.AndroidX.APP_COMPAT)
    implementation(Dependencies.AndroidX.SWIPE_REFRESH_LAYOUT)
    implementation(Dependencies.AndroidX.MEDIA)

    implementation(Dependencies.KTX.CORE)
    implementation(Dependencies.KTX.LIFECYCLE_LIVEDATA)
    implementation(Dependencies.KTX.LIFECYCLE_VIEWMODEL)
    implementation(Dependencies.KTX.ACTIVITY)
    implementation(Dependencies.KTX.FRAGMENT)
    implementation(Dependencies.KTX.FRAGMENT)

    implementation(platform(Dependencies.Firebase.BOM))
    implementation(Dependencies.Firebase.AUTH)
    implementation(Dependencies.Firebase.FIRESTORE)
    implementation(Dependencies.Firebase.GMS_PLAY_SERVICE_AUTH)
    implementation(Dependencies.Firebase.STORAGE)
    implementation(Dependencies.Firebase.CRASHLYTICS)

    implementation(Dependencies.Google.MATERIAL)
    implementation(Dependencies.Google.GSON)
    implementation(Dependencies.Google.OSS_LISENCE)

    implementation(Dependencies.Glide.CORE)
    kapt(Dependencies.Glide.APT)

    implementation(Dependencies.Hilt.CORE)
    kapt(Dependencies.Hilt.APT)

    implementation(Dependencies.Lottie.CORE)

    implementation(Dependencies.BINDABLES)
    implementation(Dependencies.SHIMMER)

    testImplementation(Dependencies.Test.JUNIT)
    testImplementation(Dependencies.Test.ARCH_CORE)
    testImplementation(Dependencies.Test.ANDROIDX_TEST_CORE)
    testImplementation(Dependencies.Test.COROUTINE_TEST)
    testImplementation(Dependencies.Test.MOCKK)
    testImplementation(Dependencies.Kotlin.REFLECTION)
    androidTestImplementation(Dependencies.AndroidTest.TEST_RUNNER)
    androidTestImplementation(Dependencies.AndroidTest.ESPRESSO_CORE)
}
