plugins {
    id("java")
    id("io.freefair.aspectj.post-compile-weaving") version "9.0.0"
}

group = "org.hynaf"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.aspectj:aspectjrt:1.9.21")
    implementation("org.aspectj:aspectweaver:1.9.21")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}