package net.labymod.api.client.waila.tool;

import java.util.List;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.service.Identifiable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/waila/tool/ToolTester.class */
public interface ToolTester extends Identifiable {
    ItemStack test(BlockState blockState);

    List<ItemStack> getTools();
}
