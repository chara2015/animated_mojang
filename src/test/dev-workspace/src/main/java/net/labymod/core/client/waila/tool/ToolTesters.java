package net.labymod.core.client.waila.tool;

import net.labymod.api.client.waila.tool.SimpleToolTester;
import net.labymod.api.client.waila.tool.ToolTester;
import net.labymod.api.client.world.item.VanillaItems;
import net.labymod.core.main.animation.old.animations.SwordOldAnimation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/waila/tool/ToolTesters.class */
public interface ToolTesters {
    public static final ToolTester AXE = SimpleToolTester.of("axe", VanillaItems.WOODEN_AXE, VanillaItems.STONE_AXE, VanillaItems.IRON_AXE, VanillaItems.GOLDEN_AXE, VanillaItems.DIAMOND_AXE, VanillaItems.NETHERITE_AXE);
    public static final ToolTester HOE = SimpleToolTester.of("hoe", VanillaItems.WOODEN_HOE, VanillaItems.STONE_HOE, VanillaItems.IRON_HOE, VanillaItems.GOLDEN_HOE, VanillaItems.DIAMOND_HOE, VanillaItems.NETHERITE_HOE);
    public static final ToolTester PICKAXE = SimpleToolTester.of("pickaxe", VanillaItems.WOODEN_PICKAXE, VanillaItems.STONE_PICKAXE, VanillaItems.IRON_PICKAXE, VanillaItems.GOLDEN_PICKAXE, VanillaItems.DIAMOND_PICKAXE, VanillaItems.NETHERITE_PICKAXE);
    public static final ToolTester SHOVEL = SimpleToolTester.of("shovel", VanillaItems.WOODEN_SHOVEL, VanillaItems.STONE_SHOVEL, VanillaItems.IRON_SHOVEL, VanillaItems.GOLDEN_SHOVEL, VanillaItems.DIAMOND_SHOVEL, VanillaItems.NETHERITE_SHOVEL);
    public static final ToolTester SWORD = SimpleToolTester.of(SwordOldAnimation.NAME, VanillaItems.WOODEN_SWORD, VanillaItems.STONE_SWORD, VanillaItems.IRON_SWORD, VanillaItems.GOLDEN_SWORD, VanillaItems.DIAMOND_SWORD, VanillaItems.NETHERITE_SWORD);
}
