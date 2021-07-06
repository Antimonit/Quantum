buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.vanniktech:gradle-maven-publish-plugin:0.16.0")
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
}
