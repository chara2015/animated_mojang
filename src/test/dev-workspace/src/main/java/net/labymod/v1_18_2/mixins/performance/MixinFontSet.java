package net.labymod.v1_18_2.mixins.performance;

import java.util.List;
import net.labymod.api.util.collection.table.CodepointTable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/performance/MixinFontSet.class */
@Mixin({ebr.class})
public abstract class MixinFontSet {
    private final CodepointTable<ebu> labyMod$bakedGlyphTable = new CodepointTable<>(x$0 -> {
        return new ebu[x$0];
    }, x$02 -> {
        return new ebu[x$02];
    });
    private final CodepointTable<drq> labyMod$glyphInfoTable = new CodepointTable<>(x$0 -> {
        return new drq[x$0];
    }, x$02 -> {
        return new drq[x$02];
    });

    @Shadow
    @Final
    private static drq b;

    @Shadow
    @Final
    private static ebv a;

    @Shadow
    protected abstract drs d(int i);

    @Shadow
    protected abstract ebu a(drs drsVar);

    @Inject(method = {"reload"}, at = {@At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/ints/Int2ObjectMap;clear()V", ordinal = 0)})
    private void labyMod$clearGlyphCache(List<drr> providers, CallbackInfo ci) {
        this.labyMod$bakedGlyphTable.clear();
        this.labyMod$glyphInfoTable.clear();
    }

    @Overwrite
    public ebu b(int index) {
        ebu bakedGlyph = this.labyMod$bakedGlyphTable.get(index);
        if (bakedGlyph == null) {
            bakedGlyph = computeBakedGlyph(index);
            this.labyMod$bakedGlyphTable.set(index, bakedGlyph);
        }
        return bakedGlyph;
    }

    @Overwrite
    public drq a(int index) {
        drq info = this.labyMod$glyphInfoTable.get(index);
        if (info == null) {
            info = computeGlyphInfo(index);
            this.labyMod$glyphInfoTable.set(index, info);
        }
        return info;
    }

    private drq computeGlyphInfo(int codepoint) {
        return codepoint == 32 ? b : d(codepoint);
    }

    private ebu computeBakedGlyph(int codepoint) {
        return codepoint == 32 ? a : a(d(codepoint));
    }
}
