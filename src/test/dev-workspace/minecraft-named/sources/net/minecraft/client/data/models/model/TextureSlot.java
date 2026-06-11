package net.minecraft.client.data.models.model;

import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.resources.model.UnbakedModel;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/data/models/model/TextureSlot.class */
public final class TextureSlot {
    public static final TextureSlot ALL = create("all");
    public static final TextureSlot TEXTURE = create("texture", ALL);
    public static final TextureSlot PARTICLE = create(UnbakedModel.PARTICLE_TEXTURE_REFERENCE, TEXTURE);
    public static final TextureSlot END = create("end", ALL);
    public static final TextureSlot BOTTOM = create(PartNames.BOTTOM, END);
    public static final TextureSlot TOP = create("top", END);
    public static final TextureSlot FRONT = create("front", ALL);
    public static final TextureSlot BACK = create("back", ALL);
    public static final TextureSlot SIDE = create("side", ALL);
    public static final TextureSlot NORTH = create("north", SIDE);
    public static final TextureSlot SOUTH = create("south", SIDE);
    public static final TextureSlot EAST = create("east", SIDE);
    public static final TextureSlot WEST = create("west", SIDE);
    public static final TextureSlot UP = create("up");
    public static final TextureSlot DOWN = create("down");
    public static final TextureSlot CROSS = create("cross");
    public static final TextureSlot CROSS_EMISSIVE = create("cross_emissive");
    public static final TextureSlot PLANT = create("plant");
    public static final TextureSlot WALL = create("wall", ALL);
    public static final TextureSlot RAIL = create("rail");
    public static final TextureSlot WOOL = create("wool");
    public static final TextureSlot PATTERN = create("pattern");
    public static final TextureSlot PANE = create("pane");
    public static final TextureSlot EDGE = create("edge");
    public static final TextureSlot FAN = create("fan");
    public static final TextureSlot STEM = create("stem");
    public static final TextureSlot UPPER_STEM = create("upperstem");
    public static final TextureSlot CROP = create("crop");
    public static final TextureSlot DIRT = create("dirt");
    public static final TextureSlot FIRE = create("fire");
    public static final TextureSlot LANTERN = create("lantern");
    public static final TextureSlot PLATFORM = create("platform");
    public static final TextureSlot UNSTICKY = create("unsticky");
    public static final TextureSlot TORCH = create("torch");
    public static final TextureSlot LAYER0 = create("layer0");
    public static final TextureSlot LAYER1 = create("layer1");
    public static final TextureSlot LAYER2 = create("layer2");
    public static final TextureSlot LIT_LOG = create("lit_log");
    public static final TextureSlot CANDLE = create("candle");
    public static final TextureSlot INSIDE = create("inside");
    public static final TextureSlot CONTENT = create("content");
    public static final TextureSlot INNER_TOP = create("inner_top");
    public static final TextureSlot FLOWERBED = create("flowerbed");
    public static final TextureSlot TENTACLES = create("tentacles");
    public static final TextureSlot BARS = create("bars");
    private final String id;
    private final TextureSlot parent;

    private static TextureSlot create(String $$0) {
        return new TextureSlot($$0, null);
    }

    private static TextureSlot create(String $$0, TextureSlot $$1) {
        return new TextureSlot($$0, $$1);
    }

    private TextureSlot(String $$0, TextureSlot $$1) {
        this.id = $$0;
        this.parent = $$1;
    }

    public String getId() {
        return this.id;
    }

    public TextureSlot getParent() {
        return this.parent;
    }

    public String toString() {
        return "#" + this.id;
    }
}
