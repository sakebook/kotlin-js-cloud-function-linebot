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
    implementation(npm("firebase-admin", "^8.10.0"))
    implementation(npm("firebase-functions", "^3.5.0"))
}

kotlin {
    sourceSets["main"].kotlin.srcDir("src/main/external")
    target {
        nodejs {}
        useCommonJs()
    }

}
