import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.20"
    jacoco
    id("com.vanniktech.maven.publish")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.ejml:ejml-all:0.38")

    testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation(kotlin("reflect"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf(
            "-XXLanguage:+InlineClasses",
            "-Xopt-in=kotlin.ExperimentalStdlibApi",
            "-Xopt-in=kotlin.RequiresOptIn"
        )
    }
}

// JACOCO
tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
    }
    dependsOn(allprojects.map { it.tasks.named<Test>("test") })
}

mavenPublish {
    sonatypeHost = com.vanniktech.maven.publish.SonatypeHost.S01
}
