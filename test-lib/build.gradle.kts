import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("com.github.ben-manes.versions")
}


dependencies {
    //implementation("io.ktor:ktor-client-cio:${Versions.ktor}")
    //implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.jackson}")
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.github.microutils:kotlin-logging:${Versions.klogging}")

    testImplementation("org.testng:testng:${Versions.testng}")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:${Versions.assertk}")
    testImplementation("io.mockk:mockk:${Versions.mockk}")
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
