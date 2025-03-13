plugins {
    id("idea")
    id("java")
    id("io.ebean") version "13.6.5"
    kotlin("jvm")
    kotlin("kapt")
}


group = "io.kotgres.benchmarks.ebean"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation(project(":common"))
    implementation("io.ebean:ebean:13.6.5")

    // query bean generation
    kapt("io.ebean:kotlin-querybean-generator:13.6.5")

    implementation("io.ebean:ebean-test:13.6.5")
    implementation(kotlin("stdlib-jdk8"))
}


ebean {
    debugLevel = 1
}
kotlin {
    jvmToolchain(17)
}