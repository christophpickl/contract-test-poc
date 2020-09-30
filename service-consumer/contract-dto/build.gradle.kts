plugins {
    kotlin("jvm")
    id("com.github.ben-manes.versions")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

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
