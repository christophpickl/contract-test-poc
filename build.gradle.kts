import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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
            rejectAlphaVersions()
            checkForGradleUpdate = false
        }
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "com.github.ben-manes.versions")

    dependencies {
        "implementation"(kotlin("stdlib-jdk8"))
        "implementation"(kotlin("reflect"))
        "implementation"("io.github.microutils:kotlin-logging:${Versions.klogging}")
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions.jvmTarget = Versions.jvm
        }
        withType<Test> {
            useTestNG {}
        }
    }
}

fun DependencyUpdatesTask.rejectAlphaVersions() {
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
}
