import java.util.Properties

pluginManagement {
	repositories {
		maven {
			name = "Fabric"
			url = uri("https://maven.fabricmc.net/")
		}
		maven("https://maven.kikugie.dev/releases")
		maven("https://maven.kikugie.dev/snapshots")
		mavenCentral()
		gradlePluginPortal()
	}
}

plugins {
	id("dev.kikugie.stonecutter") version "0.7.10"
}

stonecutter {
	create(rootProject) {
		versions("1.20", "1.20.5", "1.21", "1.21.1", "1.21.2", "1.21.5", "1.21.6", "1.21.11", "26.1", "26.1.1", "26.1.2")
		vcsVersion = "26.1.2"
	}
}

val versionPlaceholder = "[VERSION" + "ED]"
gradle.beforeProject {
	val propertiesFile = rootDir.resolve("versions/$name/gradle.properties")
	if (propertiesFile.isFile) {
		Properties().apply {
			propertiesFile.inputStream().use { load(it) }
		}.forEach { key, value ->
			if (key is String && value is String && value != versionPlaceholder) {
				extensions.extraProperties[key] = value
			}
		}
	}
}

rootProject.name = "animated_mojang"
