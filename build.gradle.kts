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

subprojects {
    // apply(plugin = "org.jetbrains.kotlin.jvm")

//    dependencies {
//        "api"(kotlin("stdlib-jdk8"))
//        "api"(kotlin("reflect"))
//        "api"("io.github.microutils:kotlin-logging:${Versions.klogging}")
//    }

    tasks {
        withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions {
                jvmTarget = Versions.jvm
            }
        }
        withType<Test> {
            useTestNG {}
        }
    }
}
