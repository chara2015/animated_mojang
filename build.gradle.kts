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

val metadataSource = rootProject.file("src/main/java/animated_mojang/ModMetadata.java")
fun metadataConstant(name: String): String {
	val pattern = Regex("""public\s+static\s+final\s+String\s+$name\s*=\s*"([^"]+)";""")
	return pattern.find(metadataSource.readText())?.groupValues?.get(1)
		?: throw GradleException("Missing ModMetadata.$name")
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
val isIdentifierEra = isMinecraft26Plus || minecraftVersion == "1.21.11"
val modId = metadataConstant("MOD_ID")
val modName = metadataConstant("MOD_NAME")
val modVersion = metadataConstant("MOD_VERSION")
val modDescription = metadataConstant("MOD_DESCRIPTION")
val modAuthor = metadataConstant("MOD_AUTHOR")
val modLicense = metadataConstant("MOD_LICENSE")
val modHomepage = metadataConstant("MOD_HOMEPAGE")
val modSources = metadataConstant("MOD_SOURCES")
val modIssues = metadataConstant("MOD_ISSUES")
val jarName = metadataConstant("JAR_NAME")
val mavenGroup = metadataConstant("MAVEN_GROUP")

apply(plugin = if (isMinecraft26Plus) "net.fabricmc.fabric-loom" else "fabric-loom")

version = modVersion
group = mavenGroup

base {
	archivesName.set("$jarName-${project.name}")
}

repositories {
	maven("https://maven.terraformersmc.com/releases")
}

extensions.configure<net.fabricmc.loom.api.LoomGradleExtensionAPI>("loom") {
	splitEnvironmentSourceSets()

	mods {
		create(modId) {
			sourceSet(sourceSets.main.get())
			sourceSet(sourceSets.getByName("client"))
		}
	}
}

if (!isMinecraft26Plus) {
	sourceSets.named("client") {
		java.setSrcDirs(listOf(
			rootProject.file("src/shared_client/java"),
			rootProject.file("src/platform/legacy/client/java")
		))
		resources.setSrcDirs(listOf(rootProject.file("src/platform/legacy/client/resources")))
		if (minecraftVersion != "1.20") {
			java.exclude("animated_mojang/client/mixin/LegacyScreen120Mixin.java")
		}
	}
	sourceSets.main {
		resources.exclude("animated_mojang.client.mixins.json")
	}
}

sourceSets.named("client") {
	java.srcDir(rootProject.file("src/shared_client/java"))
	java.srcDir(rootProject.file("src/platform/versions/${minecraftVersion.replace('.', '_')}/client/java"))
}

if (!isIdentifierEra) {
	sourceSets.main {
		java.exclude("animated_mojang/sound/**")
	}
}

sourceSets.test {
	java.setSrcDirs(emptyList<String>())
	resources.setSrcDirs(emptyList<String>())
}

dependencies {
	add("minecraft", "com.mojang:minecraft:$minecraftVersion")
	if (!isMinecraft26Plus) {
		add("mappings", project.extensions.getByType<net.fabricmc.loom.api.LoomGradleExtensionAPI>().officialMojangMappings())
		add("modImplementation", "net.fabricmc:fabric-loader:${versionedProperty("loader_version")}")
		add("modImplementation", "net.fabricmc.fabric-api:fabric-api:${versionedProperty("fabric_version")}")
		add("modCompileOnly", "com.terraformersmc:modmenu:${versionedProperty("modmenu_version")}")
	} else {
		add("implementation", "net.fabricmc:fabric-loader:${versionedProperty("loader_version")}")
		add("implementation", "net.fabricmc.fabric-api:fabric-api:${versionedProperty("fabric_version")}")
		add("compileOnly", "com.terraformersmc:modmenu:${versionedProperty("modmenu_version")}")
	}
}

tasks.withType<ProcessResources>().configureEach {
	val version = version
	val javaCompatibilityLevel = if (isMinecraft26Plus) "JAVA_25" else "JAVA_21"
	inputs.property("version", version)
	inputs.property("javaCompatibilityLevel", javaCompatibilityLevel)
	inputs.property("legacyScreen120Mixin", minecraftVersion == "1.20")
	inputs.property("versionGuiAccessorMixin", minecraftVersion == "1.21.11")
	inputs.property("versionGameRendererMixin", minecraftVersion == "1.21.11")
	inputs.property("versionScreenEffectsMixin", minecraftVersion == "1.21.11")
	inputs.property("versionLevelLoadingScreenMixin", minecraftVersion == "1.21.11")
	inputs.property("modId", modId)
	inputs.property("modName", modName)
	inputs.property("modDescription", modDescription)
	inputs.property("modAuthor", modAuthor)
	inputs.property("modLicense", modLicense)
	inputs.property("modHomepage", modHomepage)
	inputs.property("modSources", modSources)
	inputs.property("modIssues", modIssues)

	filesMatching("fabric.mod.json") {
		expand(
			"version" to version,
			"mod_id" to modId,
			"mod_name" to modName,
			"mod_description" to modDescription,
			"mod_author" to modAuthor,
			"mod_license" to modLicense,
			"mod_homepage" to modHomepage,
			"mod_sources" to modSources,
			"mod_issues" to modIssues,
			"supported_minecraft_version" to versionedProperty("supported_minecraft_version"),
			"java_version" to if (isMinecraft26Plus) "25" else "21"
		)
	}

	filesMatching("animated_mojang*.mixins.json") {
		expand(
			"java_compatibility_level" to javaCompatibilityLevel,
			"legacy_screen_120_mixin" to if (minecraftVersion == "1.20") "\"LegacyScreen120Mixin\"," else "",
			"version_gui_accessor_mixin" to if (minecraftVersion == "1.21.11") "\"GuiGraphicsAccessor\"," else "",
			"version_game_renderer_mixin" to if (minecraftVersion == "1.21.11") "\"VersionedGameRendererMixin\"," else "",
			"version_screen_effects_mixin" to if (minecraftVersion == "1.21.11") "\"VersionedScreenEffectsMixin\"," else "",
			"version_selection_list_mixin" to if (minecraftVersion == "1.21.11") "\"VersionedSelectionListMixin\"," else "",
			"version_level_loading_screen_mixin" to if (minecraftVersion == "1.21.11") "\"VersionedLevelLoadingScreenMixin\"," else ""
		)
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
