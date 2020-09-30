plugins {
    kotlin("jvm")
    id("com.github.ben-manes.versions")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.testng:testng:${Versions.testng}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.jackson}")
    implementation("com.github.tomakehurst:wiremock-standalone:${Versions.wiremock}")
    implementation("io.github.microutils:kotlin-logging:${Versions.klogging}")
}
