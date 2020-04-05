plugins {
    kotlin("js") version "1.3.71"
    kotlin("plugin.serialization") version "1.3.71"
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
    implementation(npm("@firebase/app", "^0.6.0"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:0.14.0") // workaround: 0.20.0 is not found oon npm
    implementation(npm("axios", "^0.19.2"))
}

kotlin {
    sourceSets["main"].kotlin.srcDir("src/main/external")
    target {
        nodejs {}
        useCommonJs()
    }
}

tasks {
    val jsRename by creating(Copy::class) {
        val path = "build/js/packages/${project.name}/kotlin/${project.name}.js"
        if (File(path).exists()) {
            from(path)
            into("functions")
            rename { it.replace(project.name, "index") }
        }
    }

    val depRewrite by creating(Copy::class) {
        val path = "build/js/packages/${project.name}/package.json"
        if (File(path).exists()) {
            from(path)
            into("functions")
            val file = File(path)
            val texts = file.readLines()
                .filterNot { it.contains("kotlin-source-map-loader") }
                .map { it.replace("kotlin/${project.name}", "index") }
            file.writeText(texts.joinToString("\n"))
        }
    }

    val cloudFunctions by registering {
        dependsOn(jsRename, depRewrite)
    }
}
