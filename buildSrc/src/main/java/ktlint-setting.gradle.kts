import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    id("org.jlleitschuh.gradle.ktlint")
}

ktlint {
    android.set(true)
    outputToConsole.set(true)
    outputColorName.set("RED")
    reporters {
        reporter(ReporterType.PLAIN)
        reporter(ReporterType.CHECKSTYLE)
    }
    additionalEditorconfigFile.set(file(".editorconfig"))
}

