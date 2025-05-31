plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    kotlin("plugin.jpa") version "1.9.25"
    id("org.jetbrains.kotlin.android") version "1.9.25" apply false
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
}

dependencies {
    implementation("com.squareup.okio:okio-jvm:3.12.0")
    implementation("org.springframework.boot:spring-boot-starter-web:3.4.5")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.4.5")
    implementation("org.springframework.boot:spring-boot-starter-security:3.4.5")
    implementation("org.springframework.boot:spring-boot-starter-actuator:3.4.5")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc:3.4.5")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.4.5")
    implementation("org.springframework.boot:spring-boot-starter-mail:3.4.5")
    implementation("org.springframework.boot:spring-boot-starter-data-redis:3.4.5")
    implementation("io.netty:netty-common:4.2.1.Final")
    implementation("io.netty:netty-handler:4.2.1.Final")
    implementation("io.hypersistence:hypersistence-utils-hibernate-63:3.9.10")
    implementation("org.projectlombok:lombok:1.18.38")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.25")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.25")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
    implementation("com.github.f4b6a3:tsid-creator:5.2.6")
    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    implementation("org.bouncycastle:bcprov-jdk18on:1.80")
    implementation("org.hibernate.orm:hibernate-core:6.6.13.Final")
    implementation("net.nurigo:sdk:4.3.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.19.0")
    implementation("com.github.f4b6a3:tsid-creator:5.2.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")
    runtimeOnly("com.mysql:mysql-connector-j:9.3.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.4.5")
}



allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
