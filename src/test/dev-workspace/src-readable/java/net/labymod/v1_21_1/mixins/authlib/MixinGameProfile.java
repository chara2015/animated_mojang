package net.labymod.v1_21_1.mixins.authlib;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.PropertyMap;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.mojang.Property;
import net.labymod.v1_21_1.mojang.WrappedPropertyMap;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/mixins/authlib/MixinGameProfile.class */
@Mixin({GameProfile.class})
public abstract class MixinGameProfile implements net.labymod.api.mojang.GameProfile {

    @Mutable
    @Shadow(remap = false)
    @Final
    private PropertyMap properties = new PropertyMap();
    private transient Metadata labyMod$metadata;

    @Shadow(remap = false)
    public abstract UUID getId();

    @Shadow(remap = false)
    public abstract String getName();

    @Inject(method = {"<init>*"}, at = {@At("TAIL")}, remap = false)
    private void labyMod$wrapProperties(CallbackInfo ci) {
        this.properties = new WrappedPropertyMap();
    }

    @Override // net.labymod.api.mojang.GameProfile
    public UUID getUniqueId() {
        return getId();
    }

    @Override // net.labymod.api.mojang.GameProfile
    public String getUsername() {
        return getName();
    }

    @Override // net.labymod.api.mojang.GameProfile
    @NotNull
    public Map<String, Collection<Property>> getProperties() {
        return ((WrappedPropertyMap) this.properties).getProperties();
    }

    @Override // net.labymod.api.metadata.MetadataExtension
    public Metadata metadata() {
        if (this.labyMod$metadata == null) {
            this.labyMod$metadata = Metadata.create();
        }
        return this.labyMod$metadata;
    }

    @Override // net.labymod.api.metadata.MetadataExtension
    public void metadata(Metadata metadata) {
        this.labyMod$metadata = metadata;
    }
}
