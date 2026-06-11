package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.block.model.TextureSlots;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/model/UnbakedModel.class */
public interface UnbakedModel {
    public static final String PARTICLE_TEXTURE_REFERENCE = "particle";

    default Boolean ambientOcclusion() {
        return null;
    }

    default GuiLight guiLight() {
        return null;
    }

    default ItemTransforms transforms() {
        return null;
    }

    default TextureSlots.Data textureSlots() {
        return TextureSlots.Data.EMPTY;
    }

    default UnbakedGeometry geometry() {
        return null;
    }

    default Identifier parent() {
        return null;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/model/UnbakedModel$GuiLight.class */
    public enum GuiLight {
        FRONT("front"),
        SIDE("side");

        private final String name;

        GuiLight(String $$0) {
            this.name = $$0;
        }

        public static GuiLight getByName(String $$0) {
            for (GuiLight $$1 : values()) {
                if ($$1.name.equals($$0)) {
                    return $$1;
                }
            }
            throw new IllegalArgumentException("Invalid gui light: " + $$0);
        }

        public boolean lightLikeBlock() {
            return this == SIDE;
        }
    }
}
