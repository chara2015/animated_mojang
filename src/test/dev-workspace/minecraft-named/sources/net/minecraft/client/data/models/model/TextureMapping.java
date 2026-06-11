package net.minecraft.client.data.models.model;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/data/models/model/TextureMapping.class */
public class TextureMapping {
    private final Map<TextureSlot, Identifier> slots = Maps.newHashMap();
    private final Set<TextureSlot> forcedSlots = Sets.newHashSet();

    public TextureMapping put(TextureSlot $$0, Identifier $$1) {
        this.slots.put($$0, $$1);
        return this;
    }

    public TextureMapping putForced(TextureSlot $$0, Identifier $$1) {
        this.slots.put($$0, $$1);
        this.forcedSlots.add($$0);
        return this;
    }

    public Stream<TextureSlot> getForced() {
        return this.forcedSlots.stream();
    }

    public TextureMapping copySlot(TextureSlot $$0, TextureSlot $$1) {
        this.slots.put($$1, this.slots.get($$0));
        return this;
    }

    public TextureMapping copyForced(TextureSlot $$0, TextureSlot $$1) {
        this.slots.put($$1, this.slots.get($$0));
        this.forcedSlots.add($$1);
        return this;
    }

    public Identifier get(TextureSlot $$0) {
        TextureSlot parent = $$0;
        while (true) {
            TextureSlot $$1 = parent;
            if ($$1 != null) {
                Identifier $$2 = this.slots.get($$1);
                if ($$2 != null) {
                    return $$2;
                }
                parent = $$1.getParent();
            } else {
                throw new IllegalStateException("Can't find texture for slot " + String.valueOf($$0));
            }
        }
    }

    public TextureMapping copyAndUpdate(TextureSlot $$0, Identifier $$1) {
        TextureMapping $$2 = new TextureMapping();
        $$2.slots.putAll(this.slots);
        $$2.forcedSlots.addAll(this.forcedSlots);
        $$2.put($$0, $$1);
        return $$2;
    }

    public static TextureMapping cube(Block $$0) {
        Identifier $$1 = getBlockTexture($$0);
        return cube($$1);
    }

    public static TextureMapping defaultTexture(Block $$0) {
        Identifier $$1 = getBlockTexture($$0);
        return defaultTexture($$1);
    }

    public static TextureMapping defaultTexture(Identifier $$0) {
        return new TextureMapping().put(TextureSlot.TEXTURE, $$0);
    }

    public static TextureMapping cube(Identifier $$0) {
        return new TextureMapping().put(TextureSlot.ALL, $$0);
    }

    public static TextureMapping cross(Block $$0) {
        return singleSlot(TextureSlot.CROSS, getBlockTexture($$0));
    }

    public static TextureMapping side(Block $$0) {
        return singleSlot(TextureSlot.SIDE, getBlockTexture($$0));
    }

    public static TextureMapping crossEmissive(Block $$0) {
        return new TextureMapping().put(TextureSlot.CROSS, getBlockTexture($$0)).put(TextureSlot.CROSS_EMISSIVE, getBlockTexture($$0, "_emissive"));
    }

    public static TextureMapping cross(Identifier $$0) {
        return singleSlot(TextureSlot.CROSS, $$0);
    }

    public static TextureMapping plant(Block $$0) {
        return singleSlot(TextureSlot.PLANT, getBlockTexture($$0));
    }

    public static TextureMapping plantEmissive(Block $$0) {
        return new TextureMapping().put(TextureSlot.PLANT, getBlockTexture($$0)).put(TextureSlot.CROSS_EMISSIVE, getBlockTexture($$0, "_emissive"));
    }

    public static TextureMapping plant(Identifier $$0) {
        return singleSlot(TextureSlot.PLANT, $$0);
    }

    public static TextureMapping rail(Block $$0) {
        return singleSlot(TextureSlot.RAIL, getBlockTexture($$0));
    }

    public static TextureMapping rail(Identifier $$0) {
        return singleSlot(TextureSlot.RAIL, $$0);
    }

    public static TextureMapping wool(Block $$0) {
        return singleSlot(TextureSlot.WOOL, getBlockTexture($$0));
    }

    public static TextureMapping flowerbed(Block $$0) {
        return new TextureMapping().put(TextureSlot.FLOWERBED, getBlockTexture($$0)).put(TextureSlot.STEM, getBlockTexture($$0, "_stem"));
    }

    public static TextureMapping wool(Identifier $$0) {
        return singleSlot(TextureSlot.WOOL, $$0);
    }

    public static TextureMapping stem(Block $$0) {
        return singleSlot(TextureSlot.STEM, getBlockTexture($$0));
    }

    public static TextureMapping attachedStem(Block $$0, Block $$1) {
        return new TextureMapping().put(TextureSlot.STEM, getBlockTexture($$0)).put(TextureSlot.UPPER_STEM, getBlockTexture($$1));
    }

    public static TextureMapping pattern(Block $$0) {
        return singleSlot(TextureSlot.PATTERN, getBlockTexture($$0));
    }

    public static TextureMapping fan(Block $$0) {
        return singleSlot(TextureSlot.FAN, getBlockTexture($$0));
    }

    public static TextureMapping crop(Identifier $$0) {
        return singleSlot(TextureSlot.CROP, $$0);
    }

    public static TextureMapping pane(Block $$0, Block $$1) {
        return new TextureMapping().put(TextureSlot.PANE, getBlockTexture($$0)).put(TextureSlot.EDGE, getBlockTexture($$1, "_top"));
    }

    public static TextureMapping singleSlot(TextureSlot $$0, Identifier $$1) {
        return new TextureMapping().put($$0, $$1);
    }

    public static TextureMapping column(Block $$0) {
        return new TextureMapping().put(TextureSlot.SIDE, getBlockTexture($$0, "_side")).put(TextureSlot.END, getBlockTexture($$0, "_top"));
    }

    public static TextureMapping cubeTop(Block $$0) {
        return new TextureMapping().put(TextureSlot.SIDE, getBlockTexture($$0, "_side")).put(TextureSlot.TOP, getBlockTexture($$0, "_top"));
    }

    public static TextureMapping pottedAzalea(Block $$0) {
        return new TextureMapping().put(TextureSlot.PLANT, getBlockTexture($$0, "_plant")).put(TextureSlot.SIDE, getBlockTexture($$0, "_side")).put(TextureSlot.TOP, getBlockTexture($$0, "_top"));
    }

    public static TextureMapping logColumn(Block $$0) {
        return new TextureMapping().put(TextureSlot.SIDE, getBlockTexture($$0)).put(TextureSlot.END, getBlockTexture($$0, "_top")).put(TextureSlot.PARTICLE, getBlockTexture($$0));
    }

    public static TextureMapping column(Identifier $$0, Identifier $$1) {
        return new TextureMapping().put(TextureSlot.SIDE, $$0).put(TextureSlot.END, $$1);
    }

    public static TextureMapping fence(Block $$0) {
        return new TextureMapping().put(TextureSlot.TEXTURE, getBlockTexture($$0)).put(TextureSlot.SIDE, getBlockTexture($$0, "_side")).put(TextureSlot.TOP, getBlockTexture($$0, "_top"));
    }

    public static TextureMapping customParticle(Block $$0) {
        return new TextureMapping().put(TextureSlot.TEXTURE, getBlockTexture($$0)).put(TextureSlot.PARTICLE, getBlockTexture($$0, "_particle"));
    }

    public static TextureMapping cubeBottomTop(Block $$0) {
        return new TextureMapping().put(TextureSlot.SIDE, getBlockTexture($$0, "_side")).put(TextureSlot.TOP, getBlockTexture($$0, "_top")).put(TextureSlot.BOTTOM, getBlockTexture($$0, "_bottom"));
    }

    public static TextureMapping cubeBottomTopWithWall(Block $$0) {
        Identifier $$1 = getBlockTexture($$0);
        return new TextureMapping().put(TextureSlot.WALL, $$1).put(TextureSlot.SIDE, $$1).put(TextureSlot.TOP, getBlockTexture($$0, "_top")).put(TextureSlot.BOTTOM, getBlockTexture($$0, "_bottom"));
    }

    public static TextureMapping columnWithWall(Block $$0) {
        Identifier $$1 = getBlockTexture($$0);
        return new TextureMapping().put(TextureSlot.TEXTURE, $$1).put(TextureSlot.WALL, $$1).put(TextureSlot.SIDE, $$1).put(TextureSlot.END, getBlockTexture($$0, "_top"));
    }

    public static TextureMapping door(Identifier $$0, Identifier $$1) {
        return new TextureMapping().put(TextureSlot.TOP, $$0).put(TextureSlot.BOTTOM, $$1);
    }

    public static TextureMapping door(Block $$0) {
        return new TextureMapping().put(TextureSlot.TOP, getBlockTexture($$0, "_top")).put(TextureSlot.BOTTOM, getBlockTexture($$0, "_bottom"));
    }

    public static TextureMapping particle(Block $$0) {
        return new TextureMapping().put(TextureSlot.PARTICLE, getBlockTexture($$0));
    }

    public static TextureMapping particle(Identifier $$0) {
        return new TextureMapping().put(TextureSlot.PARTICLE, $$0);
    }

    public static TextureMapping fire0(Block $$0) {
        return new TextureMapping().put(TextureSlot.FIRE, getBlockTexture($$0, "_0"));
    }

    public static TextureMapping fire1(Block $$0) {
        return new TextureMapping().put(TextureSlot.FIRE, getBlockTexture($$0, "_1"));
    }

    public static TextureMapping lantern(Block $$0) {
        return new TextureMapping().put(TextureSlot.LANTERN, getBlockTexture($$0));
    }

    public static TextureMapping torch(Block $$0) {
        return new TextureMapping().put(TextureSlot.TORCH, getBlockTexture($$0));
    }

    public static TextureMapping torch(Identifier $$0) {
        return new TextureMapping().put(TextureSlot.TORCH, $$0);
    }

    public static TextureMapping trialSpawner(Block $$0, String $$1, String $$2) {
        return new TextureMapping().put(TextureSlot.SIDE, getBlockTexture($$0, $$1)).put(TextureSlot.TOP, getBlockTexture($$0, $$2)).put(TextureSlot.BOTTOM, getBlockTexture($$0, "_bottom"));
    }

    public static TextureMapping vault(Block $$0, String $$1, String $$2, String $$3, String $$4) {
        return new TextureMapping().put(TextureSlot.FRONT, getBlockTexture($$0, $$1)).put(TextureSlot.SIDE, getBlockTexture($$0, $$2)).put(TextureSlot.TOP, getBlockTexture($$0, $$3)).put(TextureSlot.BOTTOM, getBlockTexture($$0, $$4));
    }

    public static TextureMapping particleFromItem(Item $$0) {
        return new TextureMapping().put(TextureSlot.PARTICLE, getItemTexture($$0));
    }

    public static TextureMapping commandBlock(Block $$0) {
        return new TextureMapping().put(TextureSlot.SIDE, getBlockTexture($$0, "_side")).put(TextureSlot.FRONT, getBlockTexture($$0, "_front")).put(TextureSlot.BACK, getBlockTexture($$0, "_back"));
    }

    public static TextureMapping orientableCube(Block $$0) {
        return new TextureMapping().put(TextureSlot.SIDE, getBlockTexture($$0, "_side")).put(TextureSlot.FRONT, getBlockTexture($$0, "_front")).put(TextureSlot.TOP, getBlockTexture($$0, "_top")).put(TextureSlot.BOTTOM, getBlockTexture($$0, "_bottom"));
    }

    public static TextureMapping orientableCubeOnlyTop(Block $$0) {
        return new TextureMapping().put(TextureSlot.SIDE, getBlockTexture($$0, "_side")).put(TextureSlot.FRONT, getBlockTexture($$0, "_front")).put(TextureSlot.TOP, getBlockTexture($$0, "_top"));
    }

    public static TextureMapping orientableCubeSameEnds(Block $$0) {
        return new TextureMapping().put(TextureSlot.SIDE, getBlockTexture($$0, "_side")).put(TextureSlot.FRONT, getBlockTexture($$0, "_front")).put(TextureSlot.END, getBlockTexture($$0, "_end"));
    }

    public static TextureMapping top(Block $$0) {
        return new TextureMapping().put(TextureSlot.TOP, getBlockTexture($$0, "_top"));
    }

    public static TextureMapping craftingTable(Block $$0, Block $$1) {
        return new TextureMapping().put(TextureSlot.PARTICLE, getBlockTexture($$0, "_front")).put(TextureSlot.DOWN, getBlockTexture($$1)).put(TextureSlot.UP, getBlockTexture($$0, "_top")).put(TextureSlot.NORTH, getBlockTexture($$0, "_front")).put(TextureSlot.EAST, getBlockTexture($$0, "_side")).put(TextureSlot.SOUTH, getBlockTexture($$0, "_side")).put(TextureSlot.WEST, getBlockTexture($$0, "_front"));
    }

    public static TextureMapping fletchingTable(Block $$0, Block $$1) {
        return new TextureMapping().put(TextureSlot.PARTICLE, getBlockTexture($$0, "_front")).put(TextureSlot.DOWN, getBlockTexture($$1)).put(TextureSlot.UP, getBlockTexture($$0, "_top")).put(TextureSlot.NORTH, getBlockTexture($$0, "_front")).put(TextureSlot.SOUTH, getBlockTexture($$0, "_front")).put(TextureSlot.EAST, getBlockTexture($$0, "_side")).put(TextureSlot.WEST, getBlockTexture($$0, "_side"));
    }

    public static TextureMapping snifferEgg(String $$0) {
        return new TextureMapping().put(TextureSlot.PARTICLE, getBlockTexture(Blocks.SNIFFER_EGG, $$0 + "_north")).put(TextureSlot.BOTTOM, getBlockTexture(Blocks.SNIFFER_EGG, $$0 + "_bottom")).put(TextureSlot.TOP, getBlockTexture(Blocks.SNIFFER_EGG, $$0 + "_top")).put(TextureSlot.NORTH, getBlockTexture(Blocks.SNIFFER_EGG, $$0 + "_north")).put(TextureSlot.SOUTH, getBlockTexture(Blocks.SNIFFER_EGG, $$0 + "_south")).put(TextureSlot.EAST, getBlockTexture(Blocks.SNIFFER_EGG, $$0 + "_east")).put(TextureSlot.WEST, getBlockTexture(Blocks.SNIFFER_EGG, $$0 + "_west"));
    }

    public static TextureMapping driedGhast(String $$0) {
        return new TextureMapping().put(TextureSlot.PARTICLE, getBlockTexture(Blocks.DRIED_GHAST, $$0 + "_north")).put(TextureSlot.BOTTOM, getBlockTexture(Blocks.DRIED_GHAST, $$0 + "_bottom")).put(TextureSlot.TOP, getBlockTexture(Blocks.DRIED_GHAST, $$0 + "_top")).put(TextureSlot.NORTH, getBlockTexture(Blocks.DRIED_GHAST, $$0 + "_north")).put(TextureSlot.SOUTH, getBlockTexture(Blocks.DRIED_GHAST, $$0 + "_south")).put(TextureSlot.EAST, getBlockTexture(Blocks.DRIED_GHAST, $$0 + "_east")).put(TextureSlot.WEST, getBlockTexture(Blocks.DRIED_GHAST, $$0 + "_west")).put(TextureSlot.TENTACLES, getBlockTexture(Blocks.DRIED_GHAST, $$0 + "_tentacles"));
    }

    public static TextureMapping campfire(Block $$0) {
        return new TextureMapping().put(TextureSlot.LIT_LOG, getBlockTexture($$0, "_log_lit")).put(TextureSlot.FIRE, getBlockTexture($$0, "_fire"));
    }

    public static TextureMapping candleCake(Block $$0, boolean $$1) {
        return new TextureMapping().put(TextureSlot.PARTICLE, getBlockTexture(Blocks.CAKE, "_side")).put(TextureSlot.BOTTOM, getBlockTexture(Blocks.CAKE, "_bottom")).put(TextureSlot.TOP, getBlockTexture(Blocks.CAKE, "_top")).put(TextureSlot.SIDE, getBlockTexture(Blocks.CAKE, "_side")).put(TextureSlot.CANDLE, getBlockTexture($$0, $$1 ? "_lit" : ""));
    }

    public static TextureMapping cauldron(Identifier $$0) {
        return new TextureMapping().put(TextureSlot.PARTICLE, getBlockTexture(Blocks.CAULDRON, "_side")).put(TextureSlot.SIDE, getBlockTexture(Blocks.CAULDRON, "_side")).put(TextureSlot.TOP, getBlockTexture(Blocks.CAULDRON, "_top")).put(TextureSlot.BOTTOM, getBlockTexture(Blocks.CAULDRON, "_bottom")).put(TextureSlot.INSIDE, getBlockTexture(Blocks.CAULDRON, "_inner")).put(TextureSlot.CONTENT, $$0);
    }

    public static TextureMapping sculkShrieker(boolean $$0) {
        String $$1 = $$0 ? "_can_summon" : "";
        return new TextureMapping().put(TextureSlot.PARTICLE, getBlockTexture(Blocks.SCULK_SHRIEKER, "_bottom")).put(TextureSlot.SIDE, getBlockTexture(Blocks.SCULK_SHRIEKER, "_side")).put(TextureSlot.TOP, getBlockTexture(Blocks.SCULK_SHRIEKER, "_top")).put(TextureSlot.INNER_TOP, getBlockTexture(Blocks.SCULK_SHRIEKER, $$1 + "_inner_top")).put(TextureSlot.BOTTOM, getBlockTexture(Blocks.SCULK_SHRIEKER, "_bottom"));
    }

    public static TextureMapping bars(Block $$0) {
        return new TextureMapping().put(TextureSlot.BARS, getBlockTexture($$0)).put(TextureSlot.EDGE, getBlockTexture($$0));
    }

    public static TextureMapping layer0(Item $$0) {
        return new TextureMapping().put(TextureSlot.LAYER0, getItemTexture($$0));
    }

    public static TextureMapping layer0(Block $$0) {
        return new TextureMapping().put(TextureSlot.LAYER0, getBlockTexture($$0));
    }

    public static TextureMapping layer0(Identifier $$0) {
        return new TextureMapping().put(TextureSlot.LAYER0, $$0);
    }

    public static TextureMapping layered(Identifier $$0, Identifier $$1) {
        return new TextureMapping().put(TextureSlot.LAYER0, $$0).put(TextureSlot.LAYER1, $$1);
    }

    public static TextureMapping layered(Identifier $$0, Identifier $$1, Identifier $$2) {
        return new TextureMapping().put(TextureSlot.LAYER0, $$0).put(TextureSlot.LAYER1, $$1).put(TextureSlot.LAYER2, $$2);
    }

    public static Identifier getBlockTexture(Block $$0) {
        Identifier $$1 = BuiltInRegistries.BLOCK.getKey($$0);
        return $$1.withPrefix("block/");
    }

    public static Identifier getBlockTexture(Block $$0, String $$1) {
        Identifier $$2 = BuiltInRegistries.BLOCK.getKey($$0);
        return $$2.withPath($$12 -> {
            return "block/" + $$12 + $$1;
        });
    }

    public static Identifier getItemTexture(Item $$0) {
        Identifier $$1 = BuiltInRegistries.ITEM.getKey($$0);
        return $$1.withPrefix("item/");
    }

    public static Identifier getItemTexture(Item $$0, String $$1) {
        Identifier $$2 = BuiltInRegistries.ITEM.getKey($$0);
        return $$2.withPath($$12 -> {
            return "item/" + $$12 + $$1;
        });
    }
}
