package net.labymod.core.client.world.rplace.art;

import net.labymod.api.Laby;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.util.ColorUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/world/rplace/art/ColoredBlock.class */
public class ColoredBlock {
    private final String identifier;
    private final int color;
    private final int invertedColor;
    private ItemStack itemStack;

    public ColoredBlock(String identifier, int color) {
        this.identifier = identifier;
        this.color = color;
        this.invertedColor = ColorUtil.invertColor(color);
        try {
            String[] segments = identifier.split(":");
            ItemStack itemStack = Laby.references().itemStackFactory().create(segments[0], segments[1]);
            if (!itemStack.isAir()) {
                this.itemStack = itemStack;
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public int getColor() {
        return this.color;
    }

    public int getInvertedColor() {
        return this.invertedColor;
    }

    public boolean isValid() {
        return this.itemStack != null;
    }
}
