object Dependency {

    object Kotlin {
        const val SDK = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.5.21"
        const val COROUTINE_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1"
        const val COROUTINE_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.1"
        const val COROUTINE_PLAY_SERVICE = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.5.1"
        const val REFLECTION = "org.jetbrains.kotlin:kotlin-reflect:1.5.21"
    }

    object AndroidX {
        const val MATERIAL = "androidx.compose.material:material:1.0.0-rc02"
        const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:2.1.0-rc01"
        const val APP_COMPAT = "androidx.appcompat:appcompat:1.3.1"
        const val SWIPE_REFRESH_LAYOUT = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"
        const val MEDIA = "androidx.media:media:1.4.0"
    }

    object KTX {
        const val CORE = "androidx.core:core-ktx:1.7.0-alpha01"
        const val LIFECYCLE_VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1"
        const val LIFECYCLE_LIVEDATA = "androidx.lifecycle:lifecycle-livedata-ktx:2.3.1"
        const val ACTIVITY = "androidx.activity:activity-ktx:1.3.0-rc02"
        const val FRAGMENT = "androidx.fragment:fragment-ktx:1.3.6"
    }

    object Google {
        const val MATERIAL = "com.google.android.material:material:1.3.0"
        const val GSON = "com.google.code.gson:gson:2.8.7"
        const val OSS_LISENCE = "com.google.android.gms:play-services-oss-licenses:17.0.0"
    }

    object Firebase {
        const val BOM = "com.google.firebase:firebase-bom:28.3.0"
        const val AUTH = "com.google.firebase:firebase-auth-ktx"
        const val FIRESTORE = "com.google.firebase:firebase-firestore-ktx"
        const val GMS_PLAY_SERVICE_AUTH = "com.google.android.gms:play-services-auth:19.2.0"
        const val STORAGE = "com.google.firebase:firebase-storage-ktx"
        const val CRASHLYTICS = "com.google.firebase:firebase-crashlytics"
    }

    object Rx {
        const val RX_JAVA = "io.reactivex.rxjava2:rxjava:2.2.17"
        const val RX_ANDROID = "io.reactivex.rxjava2:rxandroid:2.2.1"
        const val RX_KOTLIN = "io.reactivex.rxjava2:rxkotlin:2.4.0"
        const val RX_BINDING = "com.jakewharton.rxbinding2:rxbinding-kotlin:2.2.0"

    }

    object Test {
        const val JUNIT = "junit:junit:4.13.2"
        const val ARCH_CORE = "androidx.arch.core:core-testing:2.1.0"
        const val ANDROIDX_TEST_CORE = "androidx.test:core:1.4.0-beta02"
        const val COROUTINE_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1"
        const val ANDROID_JUNIT_RUNNER = "AndroidJUnitRunner"
        const val MOCKK = "io.mockk:mockk:1.12.0"
    }

    object AndroidTest {
        const val TEST_RUNNER = "androidx.test:runner:1.4.0"
        const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:3.4.0"
    }

    object OkHttp {
        const val CORE = "com.squareup.okhttp3:okhttp:5.0.0-alpha.2"
        const val LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2"
    }

    object Retrofit {
        const val CORE = "com.squareup.retrofit2:retrofit:2.9.0"
        const val CONVERT_GSON = "com.squareup.retrofit2:converter-gson:2.9.0"
    }

    object Glide {
        const val CORE = "com.github.bumptech.glide:glide:4.12.0"
        const val APT = "com.github.bumptech.glide:compiler:4.12.0"
    }

    object Lottie {
        const val CORE = "com.airbnb.android:lottie:3.7.2"
    }

    object Hilt {
        const val CORE = "com.google.dagger:hilt-android:2.37"
        const val APT = "com.google.dagger:hilt-android-compiler:2.37"
    }

    // Etc
    const val SHIMMER = "com.facebook.shimmer:shimmer:0.5.0"
    const val BINDABLES = "com.github.skydoves:bindables:1.0.8"
    const val GOOGLE_BUTTON ="com.shobhitpuri.custombuttons:google-signin:1.0.0"
}
