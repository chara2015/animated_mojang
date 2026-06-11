package net.labymod.api.volt.plugin;

import java.util.List;
import java.util.Set;
import net.labymod.api.mixin.dynamic.DynamicMixinConfigPlugin;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/volt/plugin/VoltMixinConfigPlugin.class */
public class VoltMixinConfigPlugin implements IMixinConfigPlugin {
    private final DynamicMixinConfigPlugin dynamicMixinConfigPlugin = new DynamicMixinConfigPlugin();

    public void onLoad(String mixinPackage) {
        this.dynamicMixinConfigPlugin.onLoad(Thread.currentThread().getContextClassLoader(), mixinPackage, System.getProperty("net.labymod.running-version"));
    }

    public String getRefMapperConfig() {
        return null;
    }

    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return this.dynamicMixinConfigPlugin.shouldApply(this::isValid, targetClassName, mixinClassName);
    }

    protected boolean isValid(String name) {
        return true;
    }

    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    public List<String> getMixins() {
        return List.of();
    }

    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
