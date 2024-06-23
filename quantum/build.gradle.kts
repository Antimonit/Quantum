import com.vanniktech.maven.publish.SonatypeHost

plugins {
    kotlin("jvm") version "2.0.0"
    jacoco
    id("com.vanniktech.maven.publish") version "0.29.0"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.ejml:ejml-all:0.43.1")

    testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation(kotlin("reflect"))
}

tasks.test {
    useJUnitPlatform()
}

// JACOCO
tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
    }
    dependsOn(allprojects.map { it.tasks.named<Test>("test") })
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.S01)
}
