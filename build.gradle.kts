import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    val kotlinVersion = "1.7.20"
    kotlin("jvm") version kotlinVersion
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
    implementation ("org.jetbrains.kotlin:kotlin-reflect:1.7.20")

    implementation("org.xpathqs:web-selenium:0.1.3")
    implementation("org.xpathqs:web:0.1.3")
    implementation("org.xpathqs:driver:0.1.3")
    implementation("org.xpathqs:core:0.1.3")
    implementation("org.xpathqs:log:0.1.3")
    implementation("org.xpathqs:prop:0.2.2")
    implementation("org.xpathqs:cache:0.1")

    implementation("org.xpathqs:gwt:0.2.2")

    implementation("org.seleniumhq.selenium:selenium-remote-driver:3.141.59")
    implementation("org.testcontainers:selenium:1.16.0")

    implementation("org.testng:testng:6.14.3")
    implementation("com.willowtreeapps.assertk:assertk-jvm:0.23.1")

    implementation("io.qameta.allure:allure-testng:2.14.0")

    implementation("org.slf4j:slf4j-log4j12:1.7.29")
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