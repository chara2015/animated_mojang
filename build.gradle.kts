import java.util.Properties

plugins {
	id("java")
	id("net.fabricmc.fabric-loom") version "1.16-SNAPSHOT" apply false
	id("dev.kikugie.stonecutter")
	`maven-publish`
}

val localVersionProperties = Properties().apply {
	val propertiesFile = project.layout.projectDirectory.file("gradle.properties").asFile
	if (propertiesFile.isFile) propertiesFile.inputStream().use { load(it) }
}

fun versionedProperty(name: String): String {
	val local = localVersionProperties.getProperty(name)
	if (!local.isNullOrBlank()) return local
	return findProperty(name)?.toString()
		?: throw GradleException("Missing versioned property '$name' for ${project.path}")
}

val isExplicitlyRequested = gradle.startParameter.taskNames.any { taskName ->
	taskName == project.path || taskName.startsWith("${project.path}:")
}

if (stonecutter.current.isActive || isExplicitlyRequested) {
val minecraftVersion = versionedProperty("minecraft_version")
val isMinecraft26Plus = minecraftVersion.substringBefore('.').toIntOrNull()?.let { it >= 26 } == true

apply(plugin = if (isMinecraft26Plus) "net.fabricmc.fabric-loom" else "fabric-loom")

version = rootProject.providers.gradleProperty("mod_version").get()
group = rootProject.providers.gradleProperty("maven_group").get()

repositories {
	// Add repositories to retrieve artifacts from in here.
	// You should only use this when depending on other mods because
	// Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
	// See https://docs.gradle.org/current/userguide/declaring_repositories.html
	// for more information about repositories.
}

extensions.configure<net.fabricmc.loom.api.LoomGradleExtensionAPI>("loom") {
	splitEnvironmentSourceSets()

	mods {
		create("animated_mojang") {
			sourceSet(sourceSets.main.get())
			sourceSet(sourceSets.getByName("client"))
		}
	}
}

dependencies {
	add("minecraft", "com.mojang:minecraft:$minecraftVersion")
	if (!isMinecraft26Plus) {
		add("mappings", project.extensions.getByType<net.fabricmc.loom.api.LoomGradleExtensionAPI>().officialMojangMappings())
		add("modImplementation", "net.fabricmc:fabric-loader:${versionedProperty("loader_version")}")
		add("modImplementation", "net.fabricmc.fabric-api:fabric-api:${versionedProperty("fabric_version")}")
	} else {
		add("implementation", "net.fabricmc:fabric-loader:${versionedProperty("loader_version")}")
		add("implementation", "net.fabricmc.fabric-api:fabric-api:${versionedProperty("fabric_version")}")
	}
}

tasks.processResources {
	val version = version
	val javaCompatibilityLevel = if (isMinecraft26Plus) "JAVA_25" else "JAVA_21"
	inputs.property("version", version)
	inputs.property("javaCompatibilityLevel", javaCompatibilityLevel)

	filesMatching("fabric.mod.json") {
		expand(
			"version" to version,
			"supported_minecraft_version" to versionedProperty("supported_minecraft_version"),
			"java_version" to if (isMinecraft26Plus) "25" else "21"
		)
	}

	filesMatching("animated_mojang*.mixins.json") {
		expand("java_compatibility_level" to javaCompatibilityLevel)
	}
}

tasks.withType<JavaCompile>().configureEach {
	options.release = if (isMinecraft26Plus) 25 else 21
}

tasks.withType<Jar>().configureEach {
	duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

java {
	// Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
	// if it is present.
	// If you remove this line, sources will not be generated.
	withSourcesJar()

	sourceCompatibility = if (isMinecraft26Plus) JavaVersion.VERSION_25 else JavaVersion.VERSION_21
	targetCompatibility = if (isMinecraft26Plus) JavaVersion.VERSION_25 else JavaVersion.VERSION_21
}

tasks.jar {
	val projectName = project.name
	inputs.property("projectName", projectName)

	from("LICENSE") {
		rename { "${it}_$projectName" }
	}
}

// configure the maven publication
publishing {
	publications {
		register<MavenPublication>("mavenJava") {
			from(components["java"])
		}
	}

	// See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
	repositories {
		// Add repositories to publish to here.
		// Notice: This block does NOT have the same function as the block in the top level.
		// The repositories here will be used for publishing your artifact, not for
		// retrieving dependencies.
	}
}
} else {
	tasks.configureEach {
		if (!name.startsWith("stonecutter")) {
			enabled = false
		}
		if (name in setOf("build", "assemble", "check", "clean")) {
			setDependsOn(emptyList<Any>())
		}
	}
}
