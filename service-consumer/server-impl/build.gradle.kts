dependencies {
    implementation(project(":service-consumer:contract-dto"))
    implementation(project(":service-provider:contract-client"))

    fun ktor(suffix: String = "") = "io.ktor:ktor$suffix:${Versions.ktor}"
    implementation(ktor())
    implementation(ktor("-server-netty"))
    implementation(ktor("-jackson"))
    implementation("org.kodein.di:kodein-di-framework-ktor-server-jvm:${Versions.kodein}")
    implementation("org.kodein.di:kodein-di-generic-jvm:${Versions.kodein}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.jackson}")
    implementation("ch.qos.logback:logback-classic:${Versions.logback}")

    testImplementation(project(":service-provider:contract-mock"))
    testImplementation(project(":test-lib"))
    testImplementation(ktor("-server-test-host")) {
        exclude(group = "junit", module = "junit")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-test-junit")
    }
}
