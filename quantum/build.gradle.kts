import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.0.0"
    jacoco
    id("com.vanniktech.maven.publish") version "0.29.0"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.ejml:ejml-all:0.43.1")

    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation(kotlin("reflect"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_11)
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

// JACOCO
tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
    }
    dependsOn(allprojects.map { it.tasks.named<Test>("test") })
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
}
