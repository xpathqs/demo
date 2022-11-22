import org.gradle.api.tasks.testing.logging.TestLogEvent

val kotlinVersion = "1.6.0"

plugins {
    kotlin("jvm") version "1.6.0"
    id("io.qameta.allure") version "2.8.1"
    java
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation ("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

    implementation("org.xpathqs:framework-testng:0.0.2")
    implementation("io.qameta.allure:allure-testng:2.14.0")
}

tasks.named<Test>("test") {
    defaultCharacterEncoding = "UTF-8"
    environment(mapOf("TESTCONTAINERS_RYUK_DISABLED" to "true"))
    defaultCharacterEncoding = "UTF-8"


    useTestNG(closureOf<TestNGOptions> {
       suites("src/test/resources/suites/pages/all.xml")
    })

    testLogging {
        showStandardStreams = true
        events = setOf(
            TestLogEvent.FAILED,
            TestLogEvent.STANDARD_OUT,
            TestLogEvent.STANDARD_ERROR
        )
    }
}

allure {
    version = "2.14.0"
    autoconfigure = true
    configuration = "testImplementation"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}