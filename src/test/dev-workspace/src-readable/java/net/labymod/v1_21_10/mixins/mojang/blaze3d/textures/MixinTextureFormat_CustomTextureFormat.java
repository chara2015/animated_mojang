package net.labymod.v1_21_10.mixins.mojang.blaze3d.textures;

import com.mojang.blaze3d.textures.TextureFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.labymod.v1_21_10.client.gfx.texture.CustomTextureFormat;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/mojang/blaze3d/textures/MixinTextureFormat_CustomTextureFormat.class */
@Mixin({TextureFormat.class})
public class MixinTextureFormat_CustomTextureFormat {

    @Mutable
    @Shadow
    @Final
    private static TextureFormat[] $VALUES;

    @Invoker("<init>")
    public static TextureFormat labyMod$newFormat(String internal, int ordinal, int pixelSize) {
        return null;
    }

    @Inject(method = {"<clinit>"}, at = {@At("TAIL")})
    private static void labyMod$addTextureFormat(CallbackInfo ci) {
        List<TextureFormat> usages = new ArrayList<>(Arrays.asList($VALUES));
        TextureFormat lastFormat = (TextureFormat) usages.getLast();
        TextureFormat newFormat = labyMod$newFormat("DEPTH24_STENCIL8", lastFormat.ordinal() + 1, 4);
        CustomTextureFormat.DEPTH24_STENCIL8 = newFormat;
        usages.add(newFormat);
        $VALUES = (TextureFormat[]) usages.toArray(new TextureFormat[0]);
    }
}
