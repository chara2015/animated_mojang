package net.minecraft.client.renderer.texture.atlas;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.metadata.MetadataSectionType;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceMetadata;
import net.minecraft.util.Mth;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/texture/atlas/SpriteResourceLoader.class */
@FunctionalInterface
public interface SpriteResourceLoader {
    public static final Logger LOGGER = LogUtils.getLogger();

    SpriteContents loadSprite(Identifier identifier, Resource resource);

    static SpriteResourceLoader create(Set<MetadataSectionType<?>> $$0) {
        return ($$1, $$2) -> {
            FrameSize $$16;
            try {
                ResourceMetadata $$3 = $$2.metadata();
                Optional<AnimationMetadataSection> $$8 = $$3.getSection(AnimationMetadataSection.TYPE);
                Optional<TextureMetadataSection> $$9 = $$3.getSection(TextureMetadataSection.TYPE);
                List<MetadataSectionType.WithValue<?>> $$10 = $$3.getTypedSections($$0);
                try {
                    InputStream $$11 = $$2.open();
                    try {
                        NativeImage $$15 = NativeImage.read($$11);
                        if ($$11 != null) {
                            $$11.close();
                        }
                        if ($$8.isPresent()) {
                            $$16 = $$8.get().calculateFrameSize($$15.getWidth(), $$15.getHeight());
                            if (!Mth.isMultipleOf($$15.getWidth(), $$16.width()) || !Mth.isMultipleOf($$15.getHeight(), $$16.height())) {
                                LOGGER.error("Image {} size {},{} is not multiple of frame size {},{}", new Object[]{$$1, Integer.valueOf($$15.getWidth()), Integer.valueOf($$15.getHeight()), Integer.valueOf($$16.width()), Integer.valueOf($$16.height())});
                                $$15.close();
                                return null;
                            }
                        } else {
                            $$16 = new FrameSize($$15.getWidth(), $$15.getHeight());
                        }
                        return new SpriteContents($$1, $$16, $$15, $$8, $$10, $$9);
                    } finally {
                    }
                } catch (IOException $$14) {
                    LOGGER.error("Using missing texture, unable to load {}", $$1, $$14);
                    return null;
                }
            } catch (Exception $$7) {
                LOGGER.error("Unable to parse metadata from {}", $$1, $$7);
                return null;
            }
        };
    }
}
