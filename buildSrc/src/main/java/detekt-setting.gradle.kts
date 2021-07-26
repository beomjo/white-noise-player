plugins {
    id("io.gitlab.arturbosch.detekt")
}

detekt {
    buildUponDefaultConfig = true
    allRules = false
    autoCorrect = true
    config = files(
        "${rootProject.projectDir}/detekt.yml"
    )

    reports {
        html.enabled = true
        txt.enabled = false
        sarif.enabled = false
        xml.enabled = true
    }
}

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.17.1")
}


