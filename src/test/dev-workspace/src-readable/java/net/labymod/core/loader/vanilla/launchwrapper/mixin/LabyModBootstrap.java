package net.labymod.core.loader.vanilla.launchwrapper.mixin;

import net.labymod.api.Constants;
import org.spongepowered.asm.service.mojang.MixinServiceLaunchWrapperBootstrap;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/mixin/LabyModBootstrap.class */
public class LabyModBootstrap extends MixinServiceLaunchWrapperBootstrap {
    public String getName() {
        return Constants.Branding.NAME;
    }

    public String getServiceClassName() {
        return "net.labymod.core.loader.mixin.LabyModBootstrap";
    }
}
