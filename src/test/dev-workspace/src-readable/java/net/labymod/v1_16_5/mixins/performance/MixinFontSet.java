package net.labymod.v1_16_5.mixins.performance;

import java.util.List;
import net.labymod.api.util.collection.table.CodepointTable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/performance/MixinFontSet.class */
@Mixin({dmw.class})
public abstract class MixinFontSet {
    private final CodepointTable<dmz> labyMod$bakedGlyphTable = new CodepointTable<>(x$0 -> {
        return new dmz[x$0];
    }, x$02 -> {
        return new dmz[x$02];
    });
    private final CodepointTable<dea> labyMod$glyphInfoTable = new CodepointTable<>(x$0 -> {
        return new dea[x$0];
    }, x$02 -> {
        return new dea[x$02];
    });

    @Shadow
    @Final
    private static dea b;

    @Shadow
    @Final
    private static dna a;

    @Shadow
    protected abstract dec c(int i);

    @Shadow
    protected abstract dmz a(dec decVar);

    @Inject(method = {"reload"}, at = {@At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/ints/Int2ObjectMap;clear()V", ordinal = 0)})
    private void labyMod$clearGlyphCache(List<deb> providers, CallbackInfo ci) {
        this.labyMod$bakedGlyphTable.clear();
        this.labyMod$glyphInfoTable.clear();
    }

    @Overwrite
    public dmz b(int index) {
        dmz bakedGlyph = this.labyMod$bakedGlyphTable.get(index);
        if (bakedGlyph == null) {
            bakedGlyph = computeBakedGlyph(index);
            this.labyMod$bakedGlyphTable.set(index, bakedGlyph);
        }
        return bakedGlyph;
    }

    @Overwrite
    public dea a(int index) {
        dea info = this.labyMod$glyphInfoTable.get(index);
        if (info == null) {
            info = computeGlyphInfo(index);
            this.labyMod$glyphInfoTable.set(index, info);
        }
        return info;
    }

    private dea computeGlyphInfo(int codepoint) {
        return codepoint == 32 ? b : c(codepoint);
    }

    private dmz computeBakedGlyph(int codepoint) {
        return codepoint == 32 ? a : a(c(codepoint));
    }
}
