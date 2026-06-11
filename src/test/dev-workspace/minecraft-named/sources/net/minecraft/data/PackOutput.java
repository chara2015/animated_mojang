package net.minecraft.data;

import java.nio.file.Path;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/data/PackOutput.class */
public class PackOutput {
    private final Path outputFolder;

    public PackOutput(Path $$0) {
        this.outputFolder = $$0;
    }

    public Path getOutputFolder() {
        return this.outputFolder;
    }

    public Path getOutputFolder(Target $$0) {
        return getOutputFolder().resolve($$0.directory);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/data/PackOutput$Target.class */
    public enum Target {
        DATA_PACK("data"),
        RESOURCE_PACK("assets"),
        REPORTS("reports");

        final String directory;

        Target(String $$0) {
            this.directory = $$0;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/data/PackOutput$PathProvider.class */
    public static class PathProvider {
        private final Path root;
        private final String kind;

        PathProvider(PackOutput $$0, Target $$1, String $$2) {
            this.root = $$0.getOutputFolder($$1);
            this.kind = $$2;
        }

        public Path file(Identifier $$0, String $$1) {
            return this.root.resolve($$0.getNamespace()).resolve(this.kind).resolve($$0.getPath() + "." + $$1);
        }

        public Path json(Identifier $$0) {
            return this.root.resolve($$0.getNamespace()).resolve(this.kind).resolve($$0.getPath() + ".json");
        }

        public Path json(ResourceKey<?> $$0) {
            return this.root.resolve($$0.identifier().getNamespace()).resolve(this.kind).resolve($$0.identifier().getPath() + ".json");
        }
    }

    public PathProvider createPathProvider(Target $$0, String $$1) {
        return new PathProvider(this, $$0, $$1);
    }

    public PathProvider createRegistryElementsPathProvider(ResourceKey<? extends Registry<?>> $$0) {
        return createPathProvider(Target.DATA_PACK, Registries.elementsDirPath($$0));
    }

    public PathProvider createRegistryTagsPathProvider(ResourceKey<? extends Registry<?>> $$0) {
        return createPathProvider(Target.DATA_PACK, Registries.tagsDirPath($$0));
    }
}
