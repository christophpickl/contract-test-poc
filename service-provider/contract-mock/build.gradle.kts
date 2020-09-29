plugins {
    kotlin("jvm")
    id("com.github.ben-manes.versions")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":service-provider:contract-dto"))
    implementation(project(":test-lib"))
}
