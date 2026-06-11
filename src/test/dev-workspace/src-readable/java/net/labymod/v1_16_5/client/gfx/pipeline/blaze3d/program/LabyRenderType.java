package net.labymod.v1_16_5.client.gfx.pipeline.blaze3d.program;

import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.function.Function;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.laby3d.textures.TextureBindingSet;
import net.labymod.api.util.function.FunctionMemoizeStorage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/client/gfx/pipeline/blaze3d/program/LabyRenderType.class */
public final class LabyRenderType extends eao {
    private static final FunctionMemoizeStorage MEMOIZE = Laby.references().functionMemoizeStorage();
    private static final Function<Float, eao> LINES = MEMOIZE.memoize(lineWidth -> {
        return new LabyRenderType("labymod_lines", eao.t(), new i(OptionalDouble.of(lineWidth.floatValue())));
    });
    private static final Function<TextureBindingSet, eao> ENTITY_TRANSLUCENT = MEMOIZE.memoize(bindingSet -> {
        return new LabyRenderType("labymod_entity_translucent", eao.c((vk) Textures.WHITE.getMinecraftLocation(), false), new TextureBindingSetRenderStateShard(bindingSet));
    });
    private final String name;
    private final eao delegate;
    private final List<ean> customRenderStateShards;

    /* JADX WARN: Illegal instructions before constructor call */
    public LabyRenderType(String name, eao delegate, ean... customRenderStateShards) {
        dfr dfrVarW = delegate.w();
        int iX = delegate.x();
        int iV = delegate.v();
        boolean zA = delegate.A();
        boolean z = delegate.ar;
        Objects.requireNonNull(delegate);
        Runnable runnable = delegate::a;
        Objects.requireNonNull(delegate);
        super(name, dfrVarW, iX, iV, zA, z, runnable, delegate::b);
        this.name = name;
        this.delegate = delegate;
        this.customRenderStateShards = List.of((Object[]) customRenderStateShards);
    }

    public void a() {
        super.a();
        for (ean customRenderStateShard : this.customRenderStateShards) {
            customRenderStateShard.a();
        }
    }

    public void b() {
        for (ean customRenderStateShard : this.customRenderStateShards) {
            customRenderStateShard.b();
        }
        super.b();
    }

    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass() || !super.equals(object)) {
            return false;
        }
        LabyRenderType that = (LabyRenderType) object;
        return Objects.equals(this.name, that.name) && Objects.equals(this.delegate, that.delegate) && Objects.equals(this.customRenderStateShards, that.customRenderStateShards);
    }

    public int hashCode() {
        int result = super.hashCode();
        return (31 * ((31 * ((31 * result) + Objects.hashCode(this.name))) + Objects.hashCode(this.delegate))) + Objects.hashCode(this.customRenderStateShards);
    }

    public static eao lines(float lineWidth) {
        return LINES.apply(Float.valueOf(lineWidth));
    }

    public static eao entityTranslucent(TextureBindingSet bindingSet) {
        return ENTITY_TRANSLUCENT.apply(bindingSet);
    }
}
