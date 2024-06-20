import org.jreleaser.model.Active

plugins {
    id("java")
    id("org.jreleaser") version "1.12.0"
}

description = "Library for minecraft plugin developers (Paper api only)"
group = "io.github.alejomc26"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.6-R0.1-SNAPSHOT")
}

jreleaser {
    signing {
        active.set(Active.ALWAYS)
        armored = true
    }
    deploy {
        maven {
            mavenCentral {
                create("mavenCentral") {
                    active.set(Active.ALWAYS)
                    url.set("https://central.sonatype.com/api/v1/publisher")
                    stagingRepository("target/staging-deploy")

                }
            }
        }
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}