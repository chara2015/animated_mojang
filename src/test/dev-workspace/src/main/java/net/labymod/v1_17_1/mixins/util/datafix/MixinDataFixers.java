package net.labymod.v1_17_1.mixins.util.datafix;

import com.mojang.datafixers.DataFixerBuilder;
import net.labymod.v1_17_1.client.util.datafix.FastDataFixerBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/util/datafix/MixinDataFixers.class */
@Mixin({ahr.class})
public abstract class MixinDataFixers {
    @Redirect(method = {"createFixerUpper"}, at = @At(remap = false, value = "NEW", target = "com/mojang/datafixers/DataFixerBuilder"))
    private static DataFixerBuilder labyMod$useFastDataFixBuilder(int dataVersion) {
        return new FastDataFixerBuilder();
    }
}
