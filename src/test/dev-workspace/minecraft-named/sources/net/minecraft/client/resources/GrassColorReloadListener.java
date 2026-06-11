package net.minecraft.client.resources;

import java.io.IOException;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.GrassColor;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/GrassColorReloadListener.class */
public class GrassColorReloadListener extends SimplePreparableReloadListener<int[]> {
    private static final Identifier LOCATION = Identifier.withDefaultNamespace("textures/colormap/grass.png");

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.minecraft.server.packs.resources.SimplePreparableReloadListener
    public int[] prepare(ResourceManager $$0, ProfilerFiller $$1) {
        try {
            return LegacyStuffWrapper.getPixels($$0, LOCATION);
        } catch (IOException $$2) {
            throw new IllegalStateException("Failed to load grass color texture", $$2);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.minecraft.server.packs.resources.SimplePreparableReloadListener
    public void apply(int[] $$0, ResourceManager $$1, ProfilerFiller $$2) {
        GrassColor.init($$0);
    }
}
