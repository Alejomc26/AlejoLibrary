import com.vanniktech.maven.publish.SonatypeHost
import io.papermc.hangarpublishplugin.model.Platforms

plugins {
    id("io.papermc.hangar-publish-plugin") version "0.1.0"
    id("com.vanniktech.maven.publish") version "0.28.0"
    id("java")
}

description = "Library for minecraft plugin developers (Paper api only)"
group = "io.github.alejomc26"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = false)
    signAllPublications()
}

hangarPublish {
    publications.register("plugin") {
        version.set(project.version as String)
        channel.set("Snapshot")
        id.set("hangar-project")
        apiKey.set(System.getProperty("HANGAR_API_TOKEN"))
        platforms {
            register(Platforms.PAPER) {
                jar.set(tasks.jar.flatMap { it.archiveFile })
                val versions: List<String> = (property("paperVersion") as String)
                    .split(",")
                    .map { it.trim() }
                platformVersions.set(versions)
            }
        }
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.6-R0.1-SNAPSHOT")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}