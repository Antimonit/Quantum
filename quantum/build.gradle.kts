plugins {
    kotlin("jvm") version "2.0.0"
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
