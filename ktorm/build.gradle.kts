plugins {
    id("java")
    kotlin("jvm")
}

group = "io.kotgres.benchmarks.ktorm"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common"))
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("org.ktorm:ktorm-core:3.6.0")
    implementation("org.ktorm:ktorm-support-postgresql:3.6.0")
    implementation(kotlin("stdlib-jdk8"))
}



kotlin {
    jvmToolchain(17)
}