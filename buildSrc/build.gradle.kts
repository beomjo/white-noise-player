plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

object PluginVersion {
    const val GRADLE = "4.2.1"
    const val KOTLIN = "1.5.10"
    const val GOOGLE_SERVICES = "4.3.5"
    const val CRASHLYTICS = "2.7.1"
    const val HILT = "2.37"
    const val KTLINT = "10.1.0"
    const val DETEKT = "1.17.1"
    const val SCABBARD = "0.4.0"

}

dependencies {
    implementation("com.android.tools.build:gradle:${PluginVersion.GRADLE}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginVersion.KOTLIN}")
    implementation("com.google.gms:google-services:${PluginVersion.GOOGLE_SERVICES}")
    implementation("com.google.firebase:firebase-crashlytics-gradle:${PluginVersion.CRASHLYTICS}")
    implementation("com.google.dagger:hilt-android-gradle-plugin:${PluginVersion.HILT}")
    implementation("org.jlleitschuh.gradle:ktlint-gradle:${PluginVersion.KTLINT}")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${PluginVersion.DETEKT}")
    implementation("gradle.plugin.dev.arunkumar:scabbard-gradle-plugin:${PluginVersion.SCABBARD}")
}
