import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val mockkVersion: String = "1.13.2"

plugins {
    kotlin("jvm") version "1.7.20"
    kotlin("plugin.serialization") version "1.7.20"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.21")
    testImplementation(kotlin("test"))
    //MOCKK
    testImplementation("io.mockk:mockk:${mockkVersion}")
    // Exposed
    implementation("org.jetbrains.exposed:exposed-core:0.39.2")
    implementation("org.jetbrains.exposed:exposed-dao:0.39.2")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.39.2")
    // Base de Datos H2 Driver JDBC
    implementation("com.h2database:h2:2.1.214")
    // Para hacer el logging
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.0")
    implementation("ch.qos.logback:logback-classic:1.4.1")
    // Para manejar las fechas
    implementation("org.jetbrains.exposed:exposed-java-time:0.39.2")
    // Opcionales
    // Para manejar un pool de conexions mega rápido con HikariCP (no es obligatorio)
    implementation("com.zaxxer:HikariCP:5.0.1")
    //Terminal
    implementation("com.github.ajalt.mordant:mordant:2.0.0-beta8")
    //SHA-512
    implementation("com.google.guava:guava:31.1-jre")
    // Serializa Json
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}