plugins {
    kotlin("jvm") version "2.1.20"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    // log4shell
    implementation("org.apache.logging.log4j:log4j-core:2.14.0")

    // Ktor für Webserver
    val ktorVersion = "2.3.7"
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-html-builder:$ktorVersion")
}

// Wir brauchen ein Lockfile für Trivy
// ./gradlew dependencies --write-locks
dependencyLocking {
    lockAllConfigurations()
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("org.example.MainKt")
}
