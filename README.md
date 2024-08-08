# AlejoLibrary

Library to:

- Apply custom behaviors to entities.
- Override vanilla entities
- Spawn entities with presets
- Make custom item functionality

## Using AlejoLibrary
Maven
````xml
<dependency>
    <groupId>io.github.alejomc26</groupId>
    <artifactId>AlejoLibrary</artifactId>
    <version>1.0</version>
</dependency>
````

Gradle
````gradle
dependencies {
    implementation("io.github.alejomc26:AlejoLibrary:1.0")
}
````

Shading
````gradle
plugins {
    id("java")
    id("io.github.goooler.shadow") version "8.1.7"
}
````
````gradle
shadowJar {
    relocate 'io.github.alejolibrary', 'YOUR.PACKAGE.alejolibrary'
}
````
