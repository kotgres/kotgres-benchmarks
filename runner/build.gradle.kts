plugins {
    id("java")
    kotlin("jvm")
}

group = "io.kotgres.benchmarks.runner"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { setUrl("https://jitpack.io") }
}

dependencies {
    // this implementation?
    implementation(project(":common"))
    implementation(project(":exposed"))
    implementation(project(":ormlite"))
    implementation(project(":ktorm"))
    implementation(project(":kotgres"))
    implementation(project(":hibernate"))
    implementation(project(":ebean"))
    implementation(project(":jooq"))

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(kotlin("stdlib-jdk8"))
}


kotlin {
    jvmToolchain(17)
}