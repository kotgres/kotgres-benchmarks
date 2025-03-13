plugins {
    id("com.google.devtools.ksp") version "1.9.25-1.0.20"
    id("java")
    kotlin("jvm")
    kotlin("plugin.noarg") version "1.9.0"
}

group = "io.kotgres.benchmarks.kotgres"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { setUrl("https://jitpack.io") }
}

dependencies {

    implementation(project(":common"))

    implementation("com.github.kotgres:kotgres-dsl:v0.1.2")
    ksp("com.github.kotgres:kotgres:v0.1.3")
    implementation("com.github.kotgres:kotgres:v0.1.3")

    implementation("org.postgresql:postgresql:42.6.0")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(kotlin("stdlib-jdk8"))
}

kotlin {
    jvmToolchain(17)
}

noArg {
    annotation("io.kotgres.orm.annotations.Table")
}
