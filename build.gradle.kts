plugins {
    java
	id("net.minecrell.plugin-yml.bukkit") version "0.5.1"
}

repositories {
	maven("https://jitpack.io/")
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
	compileOnly("com.github.MegavexNetwork.scoreboard-library:v1_18_R2:-SNAPSHOT")
}

group = "me.dreamerzero.miniboard"
version = "1.0.0"
description = "ScoreBoard with MiniMessage support"

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

bukkit {
    main = "me.dreamerzero.miniboard.MiniBoard"
    apiVersion = "1.18"
    authors = listOf("4drian3d")
    version = version
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()

        options.release.set(17)
    }
}
