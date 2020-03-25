plugins {
    kotlin("js") version "1.3.70"
}

group = "com.github.sakebook"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-js"))
}

kotlin.target.nodejs { }
