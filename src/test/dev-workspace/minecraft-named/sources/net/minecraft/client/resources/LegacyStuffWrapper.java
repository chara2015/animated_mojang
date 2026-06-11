package net.minecraft.client.resources;

import com.mojang.blaze3d.platform.NativeImage;
import java.io.IOException;
import java.io.InputStream;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/LegacyStuffWrapper.class */
public class LegacyStuffWrapper {
    @Deprecated
    public static int[] getPixels(ResourceManager $$0, Identifier $$1) throws IOException {
        InputStream $$2 = $$0.open($$1);
        try {
            NativeImage $$3 = NativeImage.read($$2);
            try {
                int[] iArrMakePixelArray = $$3.makePixelArray();
                if ($$3 != null) {
                    $$3.close();
                }
                if ($$2 != null) {
                    $$2.close();
                }
                return iArrMakePixelArray;
            } finally {
            }
        } catch (Throwable th) {
            if ($$2 != null) {
                try {
                    $$2.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }
}
