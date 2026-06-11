package net.labymod.api.client.resources.pack;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.BiConsumer;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/pack/ResourcePack.class */
public interface ResourcePack {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/pack/ResourcePack$Factory.class */
    @Referenceable
    public interface Factory {
        ResourcePack classpath(String str);

        ResourcePack classpath(String str, LoadedAddon loadedAddon);

        ResourcePack folder(String str, Path path);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/pack/ResourcePack$ResourceOutput.class */
    @FunctionalInterface
    public interface ResourceOutput extends BiConsumer<ResourceLocation, InputStreamSupplier> {
    }

    String getName();

    InputStream getResource(PackType packType, ResourceLocation resourceLocation) throws IOException;

    boolean hasResource(PackType packType, ResourceLocation resourceLocation);

    void listResources(PackType packType, String str, String str2, ResourceOutput resourceOutput);

    Set<String> getNamespaces(PackType packType);

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/pack/ResourcePack$InputStreamSupplier.class */
    @FunctionalInterface
    public interface InputStreamSupplier {
        InputStream get() throws IOException;

        default void silentClose() {
            try {
                InputStream inputStream = get();
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
            }
        }
    }
}
