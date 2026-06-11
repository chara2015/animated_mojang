package net.labymod.core.client.gfx.pipeline.texture.atlas.atlas;

import net.labymod.api.Textures;
import net.labymod.core.client.gfx.pipeline.texture.atlas.AbstractTextureAtlas;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/texture/atlas/atlas/DefaultBlockTextureAtlas.class */
public class DefaultBlockTextureAtlas extends AbstractTextureAtlas {
    public DefaultBlockTextureAtlas() {
        super(Textures.Splash.BLOCKS, 256, 256);
        register16(createLabyMod("block/grass_block_top"), 0, 0);
        register16(createLabyMod("block/stone"), 1, 0);
        register16(createLabyMod("block/dirt"), 2, 0);
        register16(createLabyMod("block/grass_block_side"), 3, 0);
        register16(createLabyMod("block/oak_planks"), 4, 0);
        register16(createLabyMod("block/cobblestone"), 0, 1);
        register16(createLabyMod("block/gravel"), 3, 1);
        register16(createLabyMod("block/gold_ore"), 0, 2);
        register16(createLabyMod("block/iron_ore"), 1, 2);
        register16(createLabyMod("block/coal_ore"), 2, 2);
        register16(createLabyMod("block/diamond_ore"), 2, 3);
        register16(createLabyMod("block/redstone_ore"), 3, 3);
        register16(createLabyMod("block/glowstone"), 9, 6);
        register16(createLabyMod("block/furnace_top"), 0, 1);
        register16(createLabyMod("block/furnace_bottom"), 1, 1);
        register16(createLabyMod("block/furnace_front"), 12, 2);
        register16(createLabyMod("block/furnace_side"), 13, 2);
        register16(createLabyMod("block/furnace_front_lit"), 13, 3);
        register16(createLabyMod("block/pumpkin_top"), 6, 6);
        register16(createLabyMod("block/pumpkin"), 6, 7);
        register16(createLabyMod("block/carved_pumpkin"), 7, 7);
        register16(createLabyMod("block/carved_pumpkin_lit"), 8, 7);
        register16(createLabyMod("block/crafting_table_top"), 11, 2);
        register16(createLabyMod("block/crafting_table_bottom"), 4, 0);
        register16(createLabyMod("block/crafting_table_side"), 11, 3);
        register16(createLabyMod("block/cobweb"), 11, 0);
        register16(createLabyMod("block/poppy"), 12, 0);
        register16(createLabyMod("block/dandelion"), 13, 0);
        register16(createLabyMod("block/torch"), 0, 5);
        register16(createLabyMod("block/soul_torch"), 3, 8);
        register16(createLabyMod("block/lever"), 0, 6);
        register16(createLabyMod("block/rail_corner"), 0, 7);
        register16(createLabyMod("block/rail"), 0, 8);
        register16(createLabyMod("block/snow"), 2, 4);
        register16(createLabyMod("block/ice"), 3, 4);
        register16(createLabyMod("block/grass_block_snow"), 4, 4);
    }
}
