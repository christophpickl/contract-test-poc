plugins {
    kotlin("jvm")
    id("com.github.ben-manes.versions")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation(project(":service-consumer:contract-dto"))
    implementation(project(":service-provider:contract-client"))
    implementation(project(":service-provider:contract-dto"))
    fun ktor(suffix: String = "") = "io.ktor:ktor$suffix:${Versions.ktor}"
    implementation(ktor())
    implementation(ktor("-server-netty"))
    implementation(ktor("-jackson"))
    implementation("org.kodein.di:kodein-di-framework-ktor-server-jvm:${Versions.kodein}")
    implementation("org.kodein.di:kodein-di-generic-jvm:${Versions.kodein}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.jackson}")
    implementation("io.github.microutils:kotlin-logging:${Versions.klogging}")
    implementation("ch.qos.logback:logback-classic:${Versions.logback}")

    testImplementation(project(":service-provider:contract-mock"))
    testImplementation(project(":test-lib"))
    testImplementation("com.github.tomakehurst:wiremock-standalone:${Versions.wiremock}")
    testImplementation("org.testng:testng:${Versions.testng}")
    testImplementation(ktor("-server-test-host")) {
        exclude(group = "junit", module = "junit")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-test-junit")
    }
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:${Versions.assertk}")
}
