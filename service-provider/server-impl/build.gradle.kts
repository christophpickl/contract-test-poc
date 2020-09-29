plugins {
    kotlin("jvm")
    id("com.github.ben-manes.versions")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":service-provider:contract-dto"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.jackson}")
}
