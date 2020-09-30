plugins {
    kotlin("jvm")
    id("com.github.ben-manes.versions")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
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
