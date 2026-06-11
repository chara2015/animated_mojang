package net.labymod.v1_21_8.mixins.performance;

import java.util.Set;
import net.labymod.api.util.collection.table.CodepointTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/performance/MixinFontSet.class */
@Mixin({gao.class})
public abstract class MixinFontSet {
    private final CodepointTable<gas> labyMod$bakedGlyphTable = new CodepointTable<>(x$0 -> {
        return new gas[x$0];
    }, x$02 -> {
        return new gas[x$02];
    });
    private final CodepointTable<a> labyMod$glyphInfoTable = new CodepointTable<>(x$0 -> {
        return new a[x$0];
    }, x$02 -> {
        return new a[x$02];
    });

    @Shadow
    protected abstract gas c(int i);

    @Shadow
    protected abstract a b(int i);

    @Inject(method = {"reload(Ljava/util/Set;)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/font/FontSet;selectProviders(Ljava/util/List;Ljava/util/Set;)Ljava/util/List;", shift = At.Shift.BEFORE)})
    private void labyMod$clearGlyphCache(Set<gan> options, CallbackInfo ci) {
        this.labyMod$bakedGlyphTable.clear();
        this.labyMod$glyphInfoTable.clear();
    }

    @Overwrite
    public gas a(int index) {
        gas bakedGlyph = this.labyMod$bakedGlyphTable.get(index);
        if (bakedGlyph == null) {
            bakedGlyph = c(index);
            this.labyMod$bakedGlyphTable.set(index, bakedGlyph);
        }
        return bakedGlyph;
    }

    @Overwrite
    public flp a(int index, boolean filter) {
        a info = this.labyMod$glyphInfoTable.get(index);
        if (info == null) {
            info = b(index);
            this.labyMod$glyphInfoTable.set(index, info);
        }
        return info.a(filter);
    }
}
