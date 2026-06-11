package net.labymod.core.thirdparty.optifine;

import java.net.URL;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.loader.platform.PlatformClassloader;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.models.Implements;
import net.labymod.api.modloader.ModLoader;
import net.labymod.api.modloader.ModLoaderId;
import net.labymod.api.modloader.ModLoaderRegistry;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.api.thirdparty.optifine.OptiFineConfig;
import net.labymod.core.addon.DefaultAddonService;
import net.labymod.core.thirdparty.optifine.listener.OptiFineListener;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/thirdparty/optifine/DefaultOptiFine.class */
@Singleton
@Implements(OptiFine.class)
public class DefaultOptiFine implements OptiFine {
    static final String INTERFACE = "net/labymod/api/thirdparty/optifine/OptiFineConfig";
    private static final String PACKAGE = "net/labymod/core/generated/thirdparty/optifine/";
    static final String IMPLEMENTATION_CLASS_NAME = "net/labymod/core/generated/thirdparty/optifine/DefaultOptiFineConfig";
    private final OptiFineConfigClassGenerator configClassGenerator;
    private OptiFineConfig optiFineConfig;
    private boolean foundOptiFine;
    private boolean onlyOnce;

    public DefaultOptiFine() {
        setOptiFineConfig(new NOPOptiFineConfig());
        this.configClassGenerator = new OptiFineConfigClassGenerator(this::setOptiFineConfig);
    }

    public void setOptiFineConfig(OptiFineConfig config) {
        this.optiFineConfig = config;
    }

    @Override // net.labymod.api.thirdparty.optifine.OptiFine
    public boolean isOptiFinePresent() {
        if (this.onlyOnce) {
            return this.foundOptiFine;
        }
        this.onlyOnce = true;
        if (BUNDLED_OPTIFINE) {
            PlatformClassloader platformClassloader = PlatformEnvironment.getPlatformClassloader();
            ClassLoader classloader = platformClassloader.getPlatformClassloader();
            URL resource = classloader.getResource(OptiFine.BETTER_GRASS_CLASS_NAME.replace(".", "/").concat(".class"));
            boolean exists = resource != null;
            setFoundOptiFine(exists);
            return exists;
        }
        setFoundOptiFine(DefaultAddonService.getInstance().getAddon(OptiFine.NAMESPACE).isPresent() || isOptiFabricPresent());
        return this.foundOptiFine;
    }

    @Override // net.labymod.api.thirdparty.optifine.OptiFine
    public boolean isOptiFabricPresent() {
        ModLoader fabricLoader = ModLoaderRegistry.instance().getById(ModLoaderId.FABRIC);
        return fabricLoader != null && fabricLoader.isModLoaded(OptiFine.FABRIC_MOD_ID);
    }

    @Override // net.labymod.api.thirdparty.optifine.OptiFine
    public OptiFineConfig optiFineConfig() {
        return this.optiFineConfig;
    }

    private void setFoundOptiFine(boolean foundOptiFine) {
        this.foundOptiFine = foundOptiFine;
        if (foundOptiFine) {
            this.configClassGenerator.generateClass(IMPLEMENTATION_CLASS_NAME.replace('/', '.'), null);
            Laby.references().eventBus().registerListener(new OptiFineListener());
        }
    }
}
