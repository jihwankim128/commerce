import org.asciidoctor.gradle.jvm.AsciidoctorTask

plugins {
    java
    id("org.springframework.boot") version "3.5.7"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
}

group = "learn"
version = "0.0.1-SNAPSHOT"
description = "commerce"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

val asciidoctorExt: Configuration by configurations.creating {
    extendsFrom(configurations["testImplementation"])
}

repositories {
    mavenCentral()
}

dependencies {
    /*
    * Spring
    * */
    // boot
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    /*
    * Utils
    * */
    // LOMBOK
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    // RESTDOCS
    asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")

    /*
    * Tests
    * */
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
}

val snippetsDir = file("build/generated-snippets")

tasks.test {
    useJUnitPlatform()
    outputs.dir(snippetsDir)
}

tasks.named<AsciidoctorTask>("asciidoctor") {
    dependsOn(tasks.named("test"))
    inputs.dir(snippetsDir)
    configurations("asciidoctorExt")
}

tasks.bootJar {
    dependsOn(tasks.named("asciidoctor"))
}

tasks.register<Copy>("copyRestDocs") {
    dependsOn(tasks.named("asciidoctor"))

    val copyDocsDir = layout.buildDirectory.dir("resources/main/static/docs").get().asFile

    delete(copyDocsDir)
    from(tasks.named<AsciidoctorTask>("asciidoctor").get().outputDir)
    into(copyDocsDir)
}