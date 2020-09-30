import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("com.github.ben-manes.versions")
}


dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.jackson}")
    implementation("com.github.tomakehurst:wiremock-standalone:${Versions.wiremock}")
    implementation("io.github.microutils:kotlin-logging:${Versions.klogging}")

    testImplementation("org.testng:testng:${Versions.testng}")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:${Versions.assertk}")
}


tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = Versions.jvm
        }
    }
    withType<Test> {
        useTestNG {}
    }
}
