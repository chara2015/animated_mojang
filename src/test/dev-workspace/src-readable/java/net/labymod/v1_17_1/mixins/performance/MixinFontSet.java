package net.labymod.v1_17_1.mixins.performance;

import java.util.List;
import net.labymod.api.util.collection.table.CodepointTable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/performance/MixinFontSet.class */
@Mixin({dym.class})
public abstract class MixinFontSet {
    private final CodepointTable<dyp> labyMod$bakedGlyphTable = new CodepointTable<>(x$0 -> {
        return new dyp[x$0];
    }, x$02 -> {
        return new dyp[x$02];
    });
    private final CodepointTable<dop> labyMod$glyphInfoTable = new CodepointTable<>(x$0 -> {
        return new dop[x$0];
    }, x$02 -> {
        return new dop[x$02];
    });

    @Shadow
    @Final
    private static dop b;

    @Shadow
    @Final
    private static dyq a;

    @Shadow
    protected abstract dor c(int i);

    @Shadow
    protected abstract dyp a(dor dorVar);

    @Inject(method = {"reload"}, at = {@At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/ints/Int2ObjectMap;clear()V", ordinal = 0)})
    private void labyMod$clearGlyphCache(List<doq> providers, CallbackInfo ci) {
        this.labyMod$bakedGlyphTable.clear();
        this.labyMod$glyphInfoTable.clear();
    }

    @Overwrite
    public dyp b(int index) {
        dyp bakedGlyph = this.labyMod$bakedGlyphTable.get(index);
        if (bakedGlyph == null) {
            bakedGlyph = computeBakedGlyph(index);
            this.labyMod$bakedGlyphTable.set(index, bakedGlyph);
        }
        return bakedGlyph;
    }

    @Overwrite
    public dop a(int index) {
        dop info = this.labyMod$glyphInfoTable.get(index);
        if (info == null) {
            info = computeGlyphInfo(index);
            this.labyMod$glyphInfoTable.set(index, info);
        }
        return info;
    }

    private dop computeGlyphInfo(int codepoint) {
        return codepoint == 32 ? b : c(codepoint);
    }

    private dyp computeBakedGlyph(int codepoint) {
        return codepoint == 32 ? a : a(c(codepoint));
    }
}
