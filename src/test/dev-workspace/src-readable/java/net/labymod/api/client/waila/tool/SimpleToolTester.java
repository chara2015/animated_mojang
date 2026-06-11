package net.labymod.api.client.waila.tool;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.world.item.VanillaItem;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/waila/tool/SimpleToolTester.class */
public class SimpleToolTester implements ToolTester {
    private final List<ItemStack> tools;
    private final String name;

    private SimpleToolTester(String name, List<ItemStack> tools) {
        this.name = name;
        this.tools = new ArrayList(tools);
    }

    public static SimpleToolTester of(String name, ItemStack... tools) {
        return of(name, (List<ItemStack>) List.of((Object[]) tools));
    }

    public static SimpleToolTester of(String name, VanillaItem... items) {
        List<ItemStack> itemStacks = new ArrayList<>();
        for (VanillaItem item : items) {
            itemStacks.add(Laby.references().itemStackFactory().create(item.identifier()));
        }
        return of(name, itemStacks);
    }

    public static SimpleToolTester of(String name, List<ItemStack> tools) {
        return new SimpleToolTester(name, tools);
    }

    @Override // net.labymod.api.client.waila.tool.ToolTester
    public ItemStack test(BlockState state) {
        for (ItemStack tool : this.tools) {
            if (tool.isCorrectTool(state)) {
                return tool;
            }
        }
        return null;
    }

    @Override // net.labymod.api.client.waila.tool.ToolTester
    public List<ItemStack> getTools() {
        return this.tools;
    }

    @Override // net.labymod.api.service.Identifiable
    public String getId() {
        return this.name;
    }
}
