
import com.github.jengelman.gradle.plugins.shadow.tasks.ConfigureShadowRelocation

plugins {
    java
	id("net.minecrell.plugin-yml.bukkit") version "0.5.1"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

repositories {
    mavenCentral()
    maven("https://jitpack.io/")
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
    implementation("com.github.MegavexNetwork.scoreboard-library:v1_18_R2:-SNAPSHOT")
    implementation("com.github.MegavexNetwork.scoreboard-library:implementation:-SNAPSHOT")
    implementation("me.lucko:commodore:1.13")
    compileOnly("org.spongepowered:configurate-hocon:4.1.2")
    compileOnly("com.github.4drian3d:MiniPlaceholders:1.0.0")
}

group = "me.dreamerzero.miniboard"
version = "1.0.0"
description = "ScoreBoard with MiniMessage support"

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

bukkit {
    main = "$group.MiniBoard"
    apiVersion = "1.18"
    authors = listOf("4drian3d")
    version = version
    libraries = listOf(
        "org.spongepowered:configurate-hocon:4.1.2"
    )
    softDepend = listOf(
        "MiniPlaceholders",
        "ProtocolSupport"
    )

    commands {
        register("miniboard") {}
    }
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.compilerArgs.add("--enable-preview")

        options.release.set(17)
    }
    /*build {
        dependsOn(shadowJar)
    }
    shadowJar {
        dependencies {
            exclude(dependency("com.mojang:brigadier"))
        }
        dependsOn(getByName("relocateShadowJar") as ConfigureShadowRelocation)
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        archiveFileName.set("MiniBoard.jar")
        configurations = listOf(project.configurations.shadow.get())
    }

    create<ConfigureShadowRelocation>("relocateShadowJar") {
        target = shadowJar.get()
        prefix = "me.dreamerzero.miniboard.libs"
    }*/
}
tasks.shadowJar {
    dependencies {
        exclude(dependency("com.mojang:brigadier"))
    }

    relocate("me.lucko.commodore", "me.dreamerzero.miniboard.libs.commodore")
    relocate("net.megavex.scoreboardlibrary", "me.dreamerzero.miniboard.libs.scoreboardlibrary")
}
