plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":service-provider:contract-dto"))
    implementation("io.ktor:ktor-client-okhttp:${Versions.ktor}")
    implementation("io.ktor:ktor-client-jackson:${Versions.ktor}")
    implementation("io.github.microutils:kotlin-logging:${Versions.klogging}")
}
