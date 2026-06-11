package net.labymod.api.client.gfx.pipeline.renderer.text.glyph;

import it.unimi.dsi.fastutil.floats.Float2ObjectMap;
import it.unimi.dsi.fastutil.floats.Float2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Random;
import java.util.function.IntFunction;
import java.util.function.ObjIntConsumer;
import net.labymod.api.util.collection.table.CodepointTable;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/glyph/AbstractGlyphStorage.class */
public abstract class AbstractGlyphStorage implements GlyphStorage {
    private static final Random RANDOM = new Random();
    private final CodepointTable<BakedGlyph> glyphs;
    private final Float2ObjectMap<IntList> glyphsByWidth = new Float2ObjectOpenHashMap();
    private EffectGlyph effectGlyph;

    public AbstractGlyphStorage(IntFunction<BakedGlyph[]> blockConstructor, IntFunction<BakedGlyph[][]> blockMapConstructor) {
        this.glyphs = new CodepointTable<>(blockConstructor, blockMapConstructor);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphStorage
    @Nullable
    public BakedGlyph getGlyph(int codepoint) {
        return this.glyphs.get(codepoint);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphStorage
    public BakedGlyph getRandomGlyph(Random random, float advance) {
        IntList codepoints = (IntList) this.glyphsByWidth.get(advance);
        if (codepoints == null || codepoints.isEmpty()) {
            return null;
        }
        return getGlyph(codepoints.getInt(RANDOM.nextInt(codepoints.size())));
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphStorage
    public EffectGlyph effectGlyph() {
        return this.effectGlyph;
    }

    public void setEffectGlyph(EffectGlyph effectGlyph) {
        this.effectGlyph = effectGlyph;
    }

    public void setGlyph(int codepoint, BakedGlyph glyph) {
        if (glyph == null) {
            return;
        }
        ((IntList) this.glyphsByWidth.computeIfAbsent(glyph.description().getAdvance(), f -> {
            return new IntArrayList();
        })).add(codepoint);
        this.glyphs.set(codepoint, glyph);
    }

    public void forEach(ObjIntConsumer<BakedGlyph> consumer) {
        this.glyphs.forEach(consumer);
    }

    public void clear() {
        this.glyphs.clear();
        this.glyphsByWidth.clear();
    }
}
