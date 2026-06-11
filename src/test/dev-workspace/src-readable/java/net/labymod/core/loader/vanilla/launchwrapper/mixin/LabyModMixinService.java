package net.labymod.core.loader.vanilla.launchwrapper.mixin;

import java.io.IOException;
import java.io.InputStream;
import net.labymod.api.Constants;
import net.labymod.core.loader.ReflectLabyModLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.service.mojang.MixinServiceLaunchWrapper;
import org.spongepowered.asm.transformers.MixinClassReader;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/mixin/LabyModMixinService.class */
public class LabyModMixinService extends MixinServiceLaunchWrapper {
    public String getName() {
        return Constants.Branding.NAME;
    }

    public MixinEnvironment.Phase getInitialPhase() {
        return MixinEnvironment.Phase.PREINIT;
    }

    public boolean isValid() {
        return true;
    }

    public InputStream getResourceAsStream(String name) {
        return ReflectLabyModLoader.invokeGetMixinServiceResourceAsStream(name, super.getResourceAsStream(name));
    }

    public MixinEnvironment.CompatibilityLevel getMaxCompatibilityLevel() {
        return MixinEnvironment.CompatibilityLevel.JAVA_25;
    }

    public byte[] getClassBytes(String name, String transformedName) throws IOException {
        return ReflectLabyModLoader.invokeGetMixinServiceClassBytes(name, transformedName, super.getClassBytes(name, transformedName));
    }

    public void checkEnv(Object bootSource) {
    }

    public ClassNode getClassNode(String className) throws ClassNotFoundException, IOException {
        return getClassNode(className, true);
    }

    public ClassNode getClassNode(String className, boolean runTransformers) throws ClassNotFoundException, IOException {
        MixinClassReader mixinClassReader = new MixinClassReader(getClassBytes(className, runTransformers), className);
        ClassNode classNode = new ClassNode();
        mixinClassReader.accept(classNode, 0);
        return classNode;
    }
}
