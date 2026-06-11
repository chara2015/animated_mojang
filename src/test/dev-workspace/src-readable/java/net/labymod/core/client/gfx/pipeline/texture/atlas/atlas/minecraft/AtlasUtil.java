package net.labymod.core.client.gfx.pipeline.texture.atlas.atlas.minecraft;

import net.labymod.api.Namespaces;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.SpriteScaling;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.StretchScaling;
import net.labymod.api.client.resources.ResourceLocation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/texture/atlas/atlas/minecraft/AtlasUtil.class */
public final class AtlasUtil {
    private static final String SERVER_LIST = "server_list";
    private static final String ICON = "icon";
    private static final String HUD = "hud";

    public static void buildIcons(SpriteFactory factory) {
        buildHudIcons(factory);
        buildPingIcons(factory);
        buildExperience(factory);
    }

    private static void buildExperience(SpriteFactory factory) {
        factory.register(createMinecraft("hud/experience_bar_background"), 0, 64, 183, 5);
        factory.register(createMinecraft("hud/experience_bar_progress"), 0, 69, 183, 5);
    }

    private static void buildHudIcons(SpriteFactory factory) {
        factory.register(createMinecraft("hud/food_empty"), 16, 27, 9, 9);
        factory.register(createMinecraft("hud/food_half"), 61, 27, 9, 9);
        factory.register(createMinecraft("hud/food_full"), 52, 27, 9, 9);
        factory.register(createMinecraft("hud/heart/container"), 16, 0, 9, 9);
        factory.register(createMinecraft("hud/heart/full"), 52, 0, 9, 9);
        factory.register(createMinecraft("hud/heart/full_blinking"), 43, 0, 9, 9);
        factory.register(createMinecraft("hud/heart/half"), 61, 0, 9, 9);
        factory.register(createMinecraft("hud/heart/absorbing_full"), 160, 0, 9, 9);
        factory.register(createMinecraft("hud/heart/absorbing_half"), 169, 0, 9, 9);
        factory.register(createMinecraft("hud/armor_full"), 43, 9, 9, 9);
    }

    private static void buildPingIcons(SpriteFactory factory) {
        factory.register(createMinecraft("server_list/incompatible"), 0, 216, 10, 8);
        factory.register(createMinecraft("server_list/unreachable"), 0, 216, 10, 8);
        factory.register(createMinecraft("icon/ping_unknown"), 0, 216, 10, 8);
        int i = 0;
        int type = 0;
        while (type < 2) {
            for (int index = 5; index > 0; index--) {
                factory.register(createMinecraft((type == 0 ? SERVER_LIST : ICON) + "/ping_" + index), 0, 177 + (i * 8), 10, 8);
                i++;
            }
            i = 0;
            type++;
        }
        for (int index2 = 5; index2 > 0; index2--) {
            factory.register(createMinecraft("server_list/pinging_" + index2), 10, 177 + (i * 8), 10, 8);
            i++;
        }
    }

    private static ResourceLocation createMinecraft(String path) {
        return ResourceLocation.create(Namespaces.MINECRAFT, path);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/texture/atlas/atlas/minecraft/AtlasUtil$SpriteFactory.class */
    public interface SpriteFactory {
        void register(ResourceLocation resourceLocation, int i, int i2, int i3, int i4, SpriteScaling.Factory factory);

        default void register(ResourceLocation id, int x, int y, int width, int height) {
            register(id, x, y, width, height, (w, h) -> {
                return new StretchScaling();
            });
        }
    }
}
