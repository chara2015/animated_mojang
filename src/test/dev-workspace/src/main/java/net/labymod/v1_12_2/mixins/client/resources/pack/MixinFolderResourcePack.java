package net.labymod.v1_12_2.mixins.client.resources.pack;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.stream.Stream;
import net.labymod.api.util.StringUtil;
import net.labymod.v1_12_2.client.resources.pack.ResourceLister;
import org.spongepowered.asm.mixin.Mixin;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/resources/pack/MixinFolderResourcePack.class */
@Mixin({cek.class})
public abstract class MixinFolderResourcePack extends ced implements ResourceLister {
    public MixinFolderResourcePack(File lvt_1_1_) {
        super(lvt_1_1_);
    }

    @Override // net.labymod.v1_12_2.client.resources.pack.ResourceLister
    public void listResources(String namespace, String baseDirectory, Consumer<nf> locationConsumer) {
        Path root = this.a.toPath();
        Path assetsDirectory = root.resolve("assets").resolve(namespace);
        if (!Files.exists(assetsDirectory, new LinkOption[0])) {
            return;
        }
        try {
            Path startPath = assetsDirectory.resolve(baseDirectory);
            Stream<Path> files = Files.find(startPath, Integer.MAX_VALUE, (path, attributes) -> {
                return attributes.isRegularFile();
            }, new FileVisitOption[0]);
            try {
                files.forEach(resource -> {
                    String path2 = StringUtil.join(assetsDirectory.relativize(resource), "/");
                    locationConsumer.accept(new nf(namespace, path2));
                });
                if (files != null) {
                    files.close();
                }
            } finally {
            }
        } catch (IOException e) {
        }
    }
}
