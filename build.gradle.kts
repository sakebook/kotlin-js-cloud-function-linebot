plugins {
    kotlin("js") version "1.3.72"
    kotlin("plugin.serialization") version "1.3.72"
}

group = "com.github.sakebook"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-js"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:0.20.0") // Not official
    implementation(npm("@jetbrains/kotlin-extensions", "^1.0.1-pre.91"))
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
    val packaging by creating(Copy::class) {
        from("build/js/packages/${project.name}/kotlin/${project.name}.js", "build/js/packages/${project.name}/package.json")
        into("functions")
        rename { it.replace("${project.name}.js", "index.js") }

        doLast {
            val jsonFile = file("functions/package.json")
            val texts = jsonFile.readLines()
                .map { it.replace("kotlin/${project.name}.js", "index.js") }
            jsonFile.writeText(texts.joinToString("\n"))
        }
    }
    compileKotlinJs {
        doLast {
            // workaround for use kotlin-extensions
            val jsFile = File("build/js/packages/${project.name}/kotlin/${project.name}.js")
            val text = jsFile.readText()
            val rep = text.replace(" require('kotlin-extensions')", " require('@jetbrains/kotlin-extensions')")
            jsFile.writeText(rep)
        }
    }
}
