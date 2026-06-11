package net.labymod.v1_8_9.mixins.client.resources.pack;

import java.io.IOException;
import java.util.Enumeration;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.labymod.v1_8_9.client.resources.pack.ResourceLister;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/resources/pack/MixinFileResourcePack.class */
@Mixin({bnc.class})
public abstract class MixinFileResourcePack implements ResourceLister {
    @Shadow
    protected abstract ZipFile d() throws IOException;

    @Override // net.labymod.v1_8_9.client.resources.pack.ResourceLister
    public void listResources(String namespace, String baseDirectory, Consumer<jy> locationConsumer) {
        try {
            ZipFile file = d();
            String packPath = "assets/" + namespace + "/";
            String path = packPath + baseDirectory + "/";
            Enumeration<? extends ZipEntry> entries = file.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (!entry.isDirectory()) {
                    String name = entry.getName();
                    if (!name.endsWith(".mcmeta") && name.startsWith(path)) {
                        String locationPath = name.substring(packPath.length());
                        locationConsumer.accept(new jy(namespace, locationPath));
                    }
                }
            }
        } catch (IOException e) {
        }
    }
}
