import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    id("com.github.ben-manes.versions")
}

val versioningTasksGroupName = "Dependency tasks"

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

fun DependencyUpdatesTask.setOptionalParameters() {
    checkForGradleUpdate = true
    outputFormatter = "html"
    outputDir = "app/build/generated/reports/dependencyUpdates"
    reportfileName = "report"
}

tasks.create<DependencyUpdatesTask>("dependencyUpdatesCheck") {
    group = versioningTasksGroupName
    description = "Check Dependency Updates"

    resolutionStrategy {
        componentSelection {
            all {
                if (isNonStable(candidate.version) && !isNonStable(currentVersion)) {
                    reject("Release candidate")
                }
            }
        }
    }

    setOptionalParameters()
}
