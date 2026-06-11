package net.minecraft.client.renderer.texture.atlas;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.StrictJsonParser;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/texture/atlas/SpriteSourceList.class */
public class SpriteSourceList {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final FileToIdConverter ATLAS_INFO_CONVERTER = new FileToIdConverter("atlases", ".json");
    private final List<SpriteSource> sources;

    private SpriteSourceList(List<SpriteSource> $$0) {
        this.sources = $$0;
    }

    public List<SpriteSource.Loader> list(ResourceManager $$0) {
        final Map<Identifier, SpriteSource.DiscardableLoader> $$1 = new HashMap<>();
        SpriteSource.Output $$2 = new SpriteSource.Output(this) { // from class: net.minecraft.client.renderer.texture.atlas.SpriteSourceList.1
            @Override // net.minecraft.client.renderer.texture.atlas.SpriteSource.Output
            public void add(Identifier $$02, SpriteSource.DiscardableLoader $$12) {
                SpriteSource.DiscardableLoader $$22 = (SpriteSource.DiscardableLoader) $$1.put($$02, $$12);
                if ($$22 != null) {
                    $$22.discard();
                }
            }

            @Override // net.minecraft.client.renderer.texture.atlas.SpriteSource.Output
            public void removeAll(Predicate<Identifier> $$02) {
                Iterator<Map.Entry<Identifier, SpriteSource.DiscardableLoader>> $$12 = $$1.entrySet().iterator();
                while ($$12.hasNext()) {
                    Map.Entry<Identifier, SpriteSource.DiscardableLoader> $$22 = $$12.next();
                    if ($$02.test($$22.getKey())) {
                        $$22.getValue().discard();
                        $$12.remove();
                    }
                }
            }
        };
        this.sources.forEach($$22 -> {
            $$22.run($$0, $$2);
        });
        ImmutableList.Builder<SpriteSource.Loader> $$3 = ImmutableList.builder();
        $$3.add($$02 -> {
            return MissingTextureAtlasSprite.create();
        });
        $$3.addAll($$1.values());
        return $$3.build();
    }

    public static SpriteSourceList load(ResourceManager $$0, Identifier $$1) {
        Identifier $$2 = ATLAS_INFO_CONVERTER.idToFile($$1);
        List<SpriteSource> $$3 = new ArrayList<>();
        for (Resource $$4 : $$0.getResourceStack($$2)) {
            try {
                BufferedReader $$5 = $$4.openAsReader();
                try {
                    Dynamic<JsonElement> $$6 = new Dynamic<>(JsonOps.INSTANCE, StrictJsonParser.parse($$5));
                    $$3.addAll((Collection) SpriteSources.FILE_CODEC.parse($$6).getOrThrow());
                    if ($$5 != null) {
                        $$5.close();
                    }
                } catch (Throwable th) {
                    if ($$5 != null) {
                        try {
                            $$5.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (Exception $$7) {
                LOGGER.error("Failed to parse atlas definition {} in pack {}", new Object[]{$$2, $$4.sourcePackId(), $$7});
            }
        }
        return new SpriteSourceList($$3);
    }
}
