package net.labymod.core.client.render.schematic.block.material;

import java.util.HashMap;
import java.util.Map;
import net.labymod.core.client.render.schematic.block.material.material.CraftingTableMaterial;
import net.labymod.core.client.render.schematic.block.material.material.CrossPlanesMaterial;
import net.labymod.core.client.render.schematic.block.material.material.EmptyMaterial;
import net.labymod.core.client.render.schematic.block.material.material.FenceMaterial;
import net.labymod.core.client.render.schematic.block.material.material.FurnaceMaterial;
import net.labymod.core.client.render.schematic.block.material.material.GlowstoneMaterial;
import net.labymod.core.client.render.schematic.block.material.material.GrassBlockMaterial;
import net.labymod.core.client.render.schematic.block.material.material.GrassMaterial;
import net.labymod.core.client.render.schematic.block.material.material.IceMaterial;
import net.labymod.core.client.render.schematic.block.material.material.LeverMaterial;
import net.labymod.core.client.render.schematic.block.material.material.LiquidMaterial;
import net.labymod.core.client.render.schematic.block.material.material.Material;
import net.labymod.core.client.render.schematic.block.material.material.PumpkinMaterial;
import net.labymod.core.client.render.schematic.block.material.material.RailMaterial;
import net.labymod.core.client.render.schematic.block.material.material.SlabMaterial;
import net.labymod.core.client.render.schematic.block.material.material.SnowBlockMaterial;
import net.labymod.core.client.render.schematic.block.material.material.SnowMaterial;
import net.labymod.core.client.render.schematic.block.material.material.SolidMaterial;
import net.labymod.core.client.render.schematic.block.material.material.TorchMaterial;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/block/material/MaterialRegistry.class */
public class MaterialRegistry {
    private static final Map<String, Material> REGISTRY = new HashMap();
    public static final Material AIR = new EmptyMaterial("air");
    public static final Material STONE = new SolidMaterial("stone");
    public static final Material GRASS_BLOCK = new GrassBlockMaterial();
    public static final Material OAK_PLANKS = new SolidMaterial("oak_planks");
    public static final Material COBBLESTONE = new SolidMaterial("cobblestone");
    public static final Material COAL_ORE = new SolidMaterial("coal_ore");
    public static final Material IRON_ORE = new SolidMaterial("iron_ore");
    public static final Material GOLD_ORE = new SolidMaterial("gold_ore");
    public static final Material DIAMOND_ORE = new SolidMaterial("diamond_ore");
    public static final Material CRAFTING_TABLE = new CraftingTableMaterial();
    public static final Material FURNACE = new FurnaceMaterial();
    public static final Material DIRT = new SolidMaterial("dirt");
    public static final Material GRAVEL = new SolidMaterial("gravel");
    public static final Material TORCH = new TorchMaterial("torch", TorchMaterial.Type.DEFAULT);
    public static final Material WALL_TORCH = new TorchMaterial("wall_torch", TorchMaterial.Type.DEFAULT);
    public static final Material SOUL_TORCH = new TorchMaterial("soul_torch", TorchMaterial.Type.SOUL);
    public static final Material SOUL_WALL_TORCH = new TorchMaterial("soul_wall_torch", TorchMaterial.Type.SOUL);
    public static final Material LAVA = new LiquidMaterial("lava");
    public static final Material COBWEB = new CrossPlanesMaterial("cobweb");
    public static final Material GRASS = new GrassMaterial();
    public static final Material OAK_FENCE = new FenceMaterial("oak_fence");
    public static final Material RAIL = new RailMaterial("rail");
    public static final Material OAK_SLAB = new SlabMaterial("oak_slab");
    public static final Material COBBLESTONE_SLAB = new SlabMaterial("cobblestone_slab");
    public static final Material DANDELION = new CrossPlanesMaterial("dandelion");
    public static final Material POPPY = new CrossPlanesMaterial("poppy");
    public static final Material GLOWSTONE = new GlowstoneMaterial();
    public static final Material SNOW = new SnowMaterial();
    public static final Material ICE = new IceMaterial("ice");
    public static final Material PUMPKIN = new PumpkinMaterial("pumpkin");
    public static final Material CARVED_PUMPKIN = new PumpkinMaterial("carved_pumpkin");
    public static final Material SNOW_BLOCK = new SnowBlockMaterial();
    public static final Material LEVER = new LeverMaterial();

    public static void register(Material material) {
        REGISTRY.put(material.getId(), material);
    }

    public static boolean has(String id) {
        return REGISTRY.containsKey(id);
    }

    @Nullable
    public static Material getById(String id) {
        return REGISTRY.get(id);
    }
}
