pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven { setUrl("https://jitpack.io") }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "kotgres-benchmarks"
include("ormlite")
include("common")
include("ktorm")
include("exposed")
include("spring")
include("hibernate")
include("jooq")
include("runner")
include("kotgres")
include("ebean")
