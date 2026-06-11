package net.labymod.core.client.waila.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.waila.tool.ToolTester;
import net.labymod.api.client.waila.tool.ToolTesterRegistry;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.models.Implements;
import net.labymod.core.util.collection.TimestampedCache;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/waila/tool/DefaultToolTesterRegistry.class */
@Singleton
@Implements(ToolTesterRegistry.class)
public class DefaultToolTesterRegistry implements ToolTesterRegistry {
    private static final long MAX_LIFETIME = 300000;
    private static final long CHECK_DURATION = 60000;
    private final TimestampedCache<BlockState, List<ItemStack>> resultCache = new TimestampedCache<>(1024, MAX_LIFETIME, 60000);
    private final Map<ResourceLocation, ToolTester> toolTesters = new HashMap();

    public DefaultToolTesterRegistry() {
        register(ToolTesters.AXE);
        register(ToolTesters.HOE);
        register(ToolTesters.PICKAXE);
        register(ToolTesters.SHOVEL);
        register(ToolTesters.SWORD);
    }

    @Override // net.labymod.api.client.waila.tool.ToolTesterRegistry
    public void register(ToolTester toolTester) {
        String namespace = Laby.labyAPI().getNamespace(toolTester);
        this.toolTesters.put(ResourceLocation.create(namespace, toolTester.getId()), toolTester);
    }

    public List<ItemStack> test(BlockState state) {
        return this.resultCache.putIfAbsent(state, key -> {
            return getTools(state);
        });
    }

    private List<ItemStack> getTools(BlockState state) {
        List<ItemStack> tools = new ArrayList<>();
        for (ToolTester toolTester : this.toolTesters.values()) {
            ItemStack itemStack = toolTester.test(state);
            if (itemStack != null) {
                tools.add(itemStack);
            }
        }
        return tools;
    }
}
