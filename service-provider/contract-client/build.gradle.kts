dependencies {
    api(project(":service-provider:contract-dto"))
    implementation("io.ktor:ktor-client-okhttp:${Versions.ktor}")
    implementation("io.ktor:ktor-client-jackson:${Versions.ktor}")
}
