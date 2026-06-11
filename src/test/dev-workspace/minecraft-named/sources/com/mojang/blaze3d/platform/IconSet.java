package com.mojang.blaze3d.platform;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.resources.IoSupplier;
import org.apache.commons.lang3.ArrayUtils;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/platform/IconSet.class */
public enum IconSet {
    RELEASE("icons"),
    SNAPSHOT("icons", "snapshot");

    private final String[] path;

    IconSet(String... $$0) {
        this.path = $$0;
    }

    public List<IoSupplier<InputStream>> getStandardIcons(PackResources $$0) throws IOException {
        return List.of(getFile($$0, "icon_16x16.png"), getFile($$0, "icon_32x32.png"), getFile($$0, "icon_48x48.png"), getFile($$0, "icon_128x128.png"), getFile($$0, "icon_256x256.png"));
    }

    public IoSupplier<InputStream> getMacIcon(PackResources $$0) throws IOException {
        return getFile($$0, "minecraft.icns");
    }

    private IoSupplier<InputStream> getFile(PackResources $$0, String $$1) throws IOException {
        String[] $$2 = (String[]) ArrayUtils.add(this.path, $$1);
        IoSupplier<InputStream> $$3 = $$0.getRootResource($$2);
        if ($$3 == null) {
            throw new FileNotFoundException(String.join("/", $$2));
        }
        return $$3;
    }
}
