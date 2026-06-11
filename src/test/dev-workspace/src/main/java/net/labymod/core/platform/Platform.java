package net.labymod.core.platform;

import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gui.screen.ScreenService;
import net.labymod.api.client.os.OperatingSystemAccessorFactory;
import net.labymod.api.client.resources.pack.ResourcePackRepository;
import net.labymod.core.client.os.DefaultOperatingSystemAccessorFactory;
import net.labymod.core.main.LabyMod;
import net.labymod.core.mapper.DefaultEnumMapperRegistry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/platform/Platform.class */
public abstract class Platform {
    protected LabyMod labyMod;
    protected Minecraft minecraft;
    protected ResourcePackRepository resourcePackRepository;
    protected PlatformScreenHandler<?> platformScreenHandler;

    protected abstract void onInitialization();

    public abstract void onPostStartup();

    public void initialize(LabyMod labyMod) {
        this.labyMod = labyMod;
        setOperatingSystemAccessorFactory(new DefaultOperatingSystemAccessorFactory());
        DefaultEnumMapperRegistry enumMapperRegistry = (DefaultEnumMapperRegistry) Laby.references().enumMapperRegistry();
        enumMapperRegistry.initialize();
        onInitialization();
        if (this.platformScreenHandler != null) {
            this.platformScreenHandler.onInitialize();
        }
    }

    public void setMinecraft(Minecraft minecraft) {
        this.minecraft = minecraft;
        this.labyMod.setMinecraft(minecraft);
    }

    public void setOperatingSystemAccessorFactory(OperatingSystemAccessorFactory osAccessorFactory) {
        this.labyMod.setOperatingSystemAccessorFactory(osAccessorFactory);
    }

    public void setPlatformScreenHandler(PlatformScreenHandler<?> platformScreenHandler) {
        this.platformScreenHandler = platformScreenHandler;
        ScreenService screenService = platformScreenHandler.getScreenService();
        Objects.requireNonNull(platformScreenHandler);
        screenService.setInventoryCondition(platformScreenHandler::isInventoryScreen);
        this.labyMod.setScreenService(screenService);
    }
}
