plugins {
    kotlin("jvm") version "1.9.25"
}

group = "io.kotgres.benchmarks"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { setUrl("https://jitpack.io") }
}

dependencies {
    testImplementation(kotlin("test"))
}



kotlin {
    jvmToolchain(17)
}
