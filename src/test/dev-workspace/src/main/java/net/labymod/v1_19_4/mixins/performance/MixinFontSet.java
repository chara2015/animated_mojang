package net.labymod.v1_19_4.mixins.performance;

import java.util.List;
import net.labymod.api.util.collection.table.CodepointTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/performance/MixinFontSet.class */
@Mixin({eqe.class})
public abstract class MixinFontSet {
    private final CodepointTable<eqh> labyMod$bakedGlyphTable = new CodepointTable<>(x$0 -> {
        return new eqh[x$0];
    }, x$02 -> {
        return new eqh[x$02];
    });
    private final CodepointTable<a> labyMod$glyphInfoTable = new CodepointTable<>(x$0 -> {
        return new a[x$0];
    }, x$02 -> {
        return new a[x$02];
    });

    @Shadow
    protected abstract eqh c(int i);

    @Shadow
    protected abstract a b(int i);

    @Inject(method = {"reload"}, at = {@At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/ints/Int2ObjectMap;clear()V", ordinal = 0)})
    private void labyMod$clearGlyphCache(List<efi> providers, CallbackInfo ci) {
        this.labyMod$bakedGlyphTable.clear();
        this.labyMod$glyphInfoTable.clear();
    }

    @Overwrite
    public eqh a(int index) {
        eqh bakedGlyph = this.labyMod$bakedGlyphTable.get(index);
        if (bakedGlyph == null) {
            bakedGlyph = c(index);
            this.labyMod$bakedGlyphTable.set(index, bakedGlyph);
        }
        return bakedGlyph;
    }

    @Overwrite
    public efh a(int index, boolean filter) {
        a info = this.labyMod$glyphInfoTable.get(index);
        if (info == null) {
            info = b(index);
            this.labyMod$glyphInfoTable.set(index, info);
        }
        return info.a(filter);
    }
}
