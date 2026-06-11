package net.minecraft.client.resources.model;

import com.mojang.math.Quadrant;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.block.model.SimpleUnbakedGeometry;
import net.minecraft.client.renderer.block.model.TextureSlots;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import org.joml.Vector3f;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/model/MissingBlockModel.class */
public class MissingBlockModel {
    private static final String TEXTURE_SLOT = "missingno";
    public static final Identifier LOCATION = Identifier.withDefaultNamespace("builtin/missing");

    public static UnbakedModel missingModel() {
        BlockElementFace.UVs $$0 = new BlockElementFace.UVs(0.0f, 0.0f, 16.0f, 16.0f);
        Map<Direction, BlockElementFace> $$1 = Util.makeEnumMap(Direction.class, $$12 -> {
            return new BlockElementFace($$12, -1, TEXTURE_SLOT, $$0, Quadrant.R0);
        });
        BlockElement $$2 = new BlockElement(new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(16.0f, 16.0f, 16.0f), $$1);
        return new BlockModel(new SimpleUnbakedGeometry(List.of($$2)), null, null, ItemTransforms.NO_TRANSFORMS, new TextureSlots.Data.Builder().addReference(UnbakedModel.PARTICLE_TEXTURE_REFERENCE, TEXTURE_SLOT).addTexture(TEXTURE_SLOT, new Material(TextureAtlas.LOCATION_BLOCKS, MissingTextureAtlasSprite.getLocation())).build(), null);
    }
}
