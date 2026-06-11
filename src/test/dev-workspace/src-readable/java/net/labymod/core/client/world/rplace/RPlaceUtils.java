package net.labymod.core.client.world.rplace;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.world.block.Block;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.util.KeyValue;
import net.labymod.core.client.world.rplace.art.ColoredBlock;
import net.labymod.core.client.world.rplace.art.PixelArt;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/world/rplace/RPlaceUtils.class */
public class RPlaceUtils {
    private static final RPlaceRegistry REGISTRY = LabyMod.references().rPlaceRegistry();

    public static void selectSlot(ItemStack itemStack) {
        ClientPlayer clientPlayer = Laby.labyAPI().minecraft().getClientPlayer();
        if (clientPlayer == null) {
            return;
        }
        int freeSlot = -1;
        Inventory inventory = clientPlayer.inventory();
        for (int i = 0; i <= 8; i++) {
            ItemStack itemInSlot = inventory.itemStackAt(i);
            if (equalsItem(itemInSlot, itemStack)) {
                inventory.setSelectedIndex(i);
                return;
            } else {
                if (itemInSlot.isAir()) {
                    freeSlot = i;
                }
            }
        }
        if (freeSlot != -1) {
            inventory.setSelectedIndex(freeSlot);
        }
    }

    public static void pickItem(ItemStack itemStack) {
        ClientPlayer clientPlayer = Laby.labyAPI().minecraft().getClientPlayer();
        if (clientPlayer == null) {
            return;
        }
        Inventory inventory = clientPlayer.inventory();
        inventory.setCreativeModeItemStack(inventory.getSelectedIndex(), itemStack.copy());
    }

    public static PixelArt getPixelArtAt(int x, int y, int z) {
        ClientWorld world = Laby.labyAPI().minecraft().clientWorld();
        BlockState blockState = world.getBlockState(x, y, z);
        if (blockState == null) {
            return null;
        }
        ClientPlayer clientPlayer = Laby.labyAPI().minecraft().getClientPlayer();
        if (clientPlayer == null) {
            return null;
        }
        for (KeyValue<PixelArt> entry : REGISTRY.getElements()) {
            PixelArt art = entry.getValue();
            int minX = art.getMinX();
            int minZ = art.getMinZ();
            int maxX = art.getMaxX();
            int maxZ = art.getMaxZ();
            int artY = art.getY();
            if (artY == y && x >= minX && x <= maxX && z >= minZ && z <= maxZ) {
                return art;
            }
        }
        return null;
    }

    public static ItemStack getRequiredItemStack(int x, int y, int z) {
        PixelArt art = getPixelArtAt(x, y, z);
        if (art == null) {
            return null;
        }
        return getRequiredItemStack(art, x, z);
    }

    public static ItemStack getRequiredItemStack(PixelArt art, int x, int z) {
        ClientPlayer clientPlayer;
        ColoredBlock requiredBlock = art.getBlockAt(x - art.getMinX(), z - art.getMinZ());
        if (requiredBlock == null || (clientPlayer = Laby.labyAPI().minecraft().getClientPlayer()) == null) {
            return null;
        }
        Inventory inventory = clientPlayer.inventory();
        int selectedIndex = inventory.getSelectedIndex();
        ItemStack selectedItem = inventory.itemStackAt(selectedIndex);
        ItemStack requiredItem = requiredBlock.getItemStack();
        if (selectedItem.equals(requiredItem)) {
            return null;
        }
        return requiredItem;
    }

    public static boolean equalsItem(ItemStack item1, ItemStack item2) {
        if (item1 == null || item2 == null) {
            return false;
        }
        return item1.getIdentifier().getPath().equals(item2.getIdentifier().getPath());
    }

    public static boolean equalsItem(Block block, ItemStack item) {
        if (block == null || item == null) {
            return false;
        }
        return block.id().getPath().equals(item.getIdentifier().getPath());
    }
}
