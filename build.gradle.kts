import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    kotlin("jvm") version Versions.kotlin apply false
    id("com.github.ben-manes.versions") version Versions.Plugins.versions apply false
}

allprojects {
    repositories {
        mavenCentral()
        jcenter()
        mavenLocal()
    }

    tasks {
        withType<DependencyUpdatesTask> {
            val rejectPatterns = listOf("alpha", "beta", "eap", "rc").map { qualifier ->
                Regex("(?i).*[.-]$qualifier[.\\d-]*")
            }
            resolutionStrategy {
                componentSelection {
                    all {
                        if (rejectPatterns.any { it.matches(candidate.version) }) {
                            reject("Release candidate")
                        }
                    }
                }
            }
            checkForGradleUpdate = true
            outputFormatter = "json"
            outputDir = "build/reports"
            reportfileName = "dependencyUpdates"
        }
    }
}
