package net.minecraft.server.packs.linkfs;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.nio.file.Path;
import java.util.Map;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/linkfs/PathContents.class */
interface PathContents {
    public static final PathContents MISSING = new PathContents() { // from class: net.minecraft.server.packs.linkfs.PathContents.1
        public String toString() {
            return "empty";
        }
    };
    public static final PathContents RELATIVE = new PathContents() { // from class: net.minecraft.server.packs.linkfs.PathContents.2
        public String toString() {
            return "relative";
        }
    };

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/linkfs/PathContents$FileContents.class */
    public static final class FileContents extends Record implements PathContents {
        private final Path contents;

        public FileContents(Path $$0) {
            this.contents = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, FileContents.class), FileContents.class, "contents", "FIELD:Lnet/minecraft/server/packs/linkfs/PathContents$FileContents;->contents:Ljava/nio/file/Path;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, FileContents.class), FileContents.class, "contents", "FIELD:Lnet/minecraft/server/packs/linkfs/PathContents$FileContents;->contents:Ljava/nio/file/Path;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, FileContents.class, Object.class), FileContents.class, "contents", "FIELD:Lnet/minecraft/server/packs/linkfs/PathContents$FileContents;->contents:Ljava/nio/file/Path;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Path contents() {
            return this.contents;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/linkfs/PathContents$DirectoryContents.class */
    public static final class DirectoryContents extends Record implements PathContents {
        private final Map<String, LinkFSPath> children;

        public DirectoryContents(Map<String, LinkFSPath> $$0) {
            this.children = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, DirectoryContents.class), DirectoryContents.class, "children", "FIELD:Lnet/minecraft/server/packs/linkfs/PathContents$DirectoryContents;->children:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, DirectoryContents.class), DirectoryContents.class, "children", "FIELD:Lnet/minecraft/server/packs/linkfs/PathContents$DirectoryContents;->children:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, DirectoryContents.class, Object.class), DirectoryContents.class, "children", "FIELD:Lnet/minecraft/server/packs/linkfs/PathContents$DirectoryContents;->children:Ljava/util/Map;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Map<String, LinkFSPath> children() {
            return this.children;
        }
    }
}
