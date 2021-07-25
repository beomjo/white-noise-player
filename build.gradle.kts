subprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://plugins.gradle.org/m2/")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
