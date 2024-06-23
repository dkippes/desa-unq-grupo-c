import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    kotlin("plugin.jpa") version "1.9.22"
    id("org.sonarqube") version "4.3.1.3277"
    id("jacoco")
}

val springdocVersion = "2.2.0"
val mockitoVersion = "3.+"
val junitVersion = "4.13.2"
val archunitVersion = "1.0.1"
val jwtVersion = "0.9.1"
val jaxbVersion = "2.1"
val cache = "2.6.0"
val cacheApi = "1.1.1"
val cacheEh = "3.8.1"
val jaxbApi = "2.3.1"
val jaxbCore = "2.3.0.1"
val jaxbImpl = "2.3.2"

sonar {
    properties {
        property("sonar.projectKey", "dkippes_desa-unq-grupo-c")
        property("sonar.organization", "dkippes")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.coverage.jacoco.xmlReportPaths", "$buildDir\\reports\\jacoco\\test\\jacocoTestReport.xml")
        property("sonar.coverage.exclusions", "**/configuration/**,ar/edu/unq/desapp/grupoc/backenddesappapi/webservice/handlers/**")
    }
}

group = "ar.edu.unq.desapp.grupoc"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${springdocVersion}")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt:${jwtVersion}")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("javax.xml.bind:jaxb-api:${jaxbVersion}")
    implementation("org.springframework.boot:spring-boot-starter-cache:${cache}")
    implementation("javax.cache:cache-api:${cacheApi}")
    implementation("org.ehcache:ehcache:${cacheEh}")
    implementation("javax.xml.bind:jaxb-api:${jaxbApi}")
    implementation("com.sun.xml.bind:jaxb-core:${jaxbCore}")
    implementation("com.sun.xml.bind:jaxb-impl:${jaxbImpl}")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito:mockito-core:${mockitoVersion}")
    testImplementation("junit:junit:${junitVersion}")
    testImplementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    testImplementation("com.tngtech.archunit:archunit-junit4:${archunitVersion}")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.h2database:h2")

}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(false)
    }
}

jacoco {
    toolVersion = "0.8.8"
}

