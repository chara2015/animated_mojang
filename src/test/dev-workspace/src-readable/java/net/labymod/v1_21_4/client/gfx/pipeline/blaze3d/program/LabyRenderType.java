package net.labymod.v1_21_4.client.gfx.pipeline.blaze3d.program;

import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.function.Function;
import net.labymod.api.Textures;
import net.labymod.api.laby3d.textures.TextureBindingSet;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/client/gfx/pipeline/blaze3d/program/LabyRenderType.class */
public final class LabyRenderType extends gmj {
    private static final Function<Float, gmj> LINES = af.b(lineWidth -> {
        return new LabyRenderType("labymod_lines", gmj.y(), new h(OptionalDouble.of(lineWidth.floatValue())));
    });
    private static final Function<TextureBindingSet, gmj> ENTITY_TRANSLUCENT = af.b(bindingSet -> {
        return new LabyRenderType("labymod_entity_translucent", gmj.c((akv) Textures.WHITE.getMinecraftLocation(), false), new TextureBindingSetRenderStateShard(bindingSet));
    });
    private final String name;
    private final gmj delegate;
    private final List<gmi> customRenderStateShards;

    /* JADX WARN: Illegal instructions before constructor call */
    public LabyRenderType(String name, gmj delegate, gmi... customRenderStateShards) {
        fga fgaVarS = delegate.S();
        c cVarT = delegate.T();
        int iR = delegate.R();
        boolean zW = delegate.W();
        boolean zY = delegate.Y();
        Objects.requireNonNull(delegate);
        Runnable runnable = delegate::a;
        Objects.requireNonNull(delegate);
        super(name, fgaVarS, cVarT, iR, zW, zY, runnable, delegate::b);
        this.name = name;
        this.delegate = delegate;
        this.customRenderStateShards = List.of((Object[]) customRenderStateShards);
    }

    public void a() {
        super.a();
        for (gmi customRenderStateShard : this.customRenderStateShards) {
            customRenderStateShard.a();
        }
    }

    public void b() {
        for (gmi customRenderStateShard : this.customRenderStateShards) {
            customRenderStateShard.b();
        }
        super.b();
    }

    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        LabyRenderType that = (LabyRenderType) object;
        return Objects.equals(this.name, that.name) && Objects.equals(this.delegate, that.delegate) && Objects.equals(this.customRenderStateShards, that.customRenderStateShards);
    }

    public int hashCode() {
        int result = Objects.hashCode(this.name);
        return (31 * ((31 * result) + Objects.hashCode(this.delegate))) + Objects.hashCode(this.customRenderStateShards);
    }

    public static gmj lines(float lineWidth) {
        return LINES.apply(Float.valueOf(lineWidth));
    }

    public static gmj entityTranslucent(TextureBindingSet bindingSet) {
        return ENTITY_TRANSLUCENT.apply(bindingSet);
    }
}
