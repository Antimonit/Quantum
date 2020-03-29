import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.jfrog.bintray.gradle.BintrayExtension

plugins {
    kotlin("jvm") version "1.3.71"
    id("com.jfrog.bintray") version "1.8.4"
    `maven-publish`
    jacoco
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
tasks.register<JacocoReport>("codeCoverageReport") {

    executionData(fileTree(project.rootDir.absolutePath).include("**/build/jacoco/*.exec"))

    subprojects.forEach {
        sourceSets.addLater(it.sourceSets.main)
    }

    reports {
        xml.isEnabled = true
        xml.destination = File("${buildDir}/reports/jacoco/report.xml")
        html.isEnabled = false
        csv.isEnabled = false
    }

    dependsOn(allprojects.map { it.tasks.named<Test>("test") })
}

// PUBLISHING
java {
    withJavadocJar()
    withSourcesJar()
}

val libraryVersion = "1.0.0-RC3"

publishing {
    publications {
        create<MavenPublication>("quantum") {
            groupId = "me.khol"
            artifactId = "quantum"
            version = libraryVersion

            from(components["java"])

            pom {
                name.set("quantum")
                description.set("Kotlin framework for writing quantum algorithms using QASM-like syntax")
                url.set("https://github.com/Antimonit/Quantum")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        id.set("antimonit")
                        name.set("David Khol")
                        email.set("david@khol.me")
                    }
                }

                scm {
                    connection.set("https://github.com/Antimonit/Quantum.git")
                    developerConnection.set("https://github.com/Antimonit/Quantum.git")
                    url.set("https://github.com/Antimonit/Quantum")
                }
            }
        }
    }
}

bintray {
    user = System.getProperty("bintray.user")
    key = System.getProperty("bintray.key")

    setPublications("quantum")

    pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
        repo = "quantum"
        name = "quantum"
        desc = "Kotlin framework for writing quantum algorithms using QASM-like syntax"

        websiteUrl = "https://github.com/Antimonit/Quantum"
        issueTrackerUrl = "https://github.com/Antimonit/Quantum/issues"
        vcsUrl = "https://github.com/Antimonit/Quantum.git"

        publish = true
        publicDownloadNumbers = true

        setLicenses("Apache-2.0")

        version(delegateClosureOf<BintrayExtension.VersionConfig> {
            name = libraryVersion
            desc = "Kotlin framework for writing quantum algorithms using QASM-like syntax"

            gpg(delegateClosureOf<BintrayExtension.GpgConfig> {
                sign = true
            })
        })
    })
}
