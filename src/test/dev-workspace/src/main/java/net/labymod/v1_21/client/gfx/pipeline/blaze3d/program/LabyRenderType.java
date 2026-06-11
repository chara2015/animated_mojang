package net.labymod.v1_21.client.gfx.pipeline.blaze3d.program;

import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.function.Function;
import net.labymod.api.Textures;
import net.labymod.api.laby3d.textures.TextureBindingSet;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/client/gfx/pipeline/blaze3d/program/LabyRenderType.class */
public final class LabyRenderType extends gfh {
    private static final Function<Float, gfh> LINES = ad.b(lineWidth -> {
        return new LabyRenderType("labymod_lines", gfh.y(), new h(OptionalDouble.of(lineWidth.floatValue())));
    });
    private static final Function<TextureBindingSet, gfh> ENTITY_TRANSLUCENT = ad.b(bindingSet -> {
        return new LabyRenderType("labymod_entity_translucent", gfh.c((akr) Textures.WHITE.getMinecraftLocation(), false), new TextureBindingSetRenderStateShard(bindingSet));
    });
    private final String name;
    private final gfh delegate;
    private final List<gfg> customRenderStateShards;

    /* JADX WARN: Illegal instructions before constructor call */
    public LabyRenderType(String name, gfh delegate, gfg... customRenderStateShards) {
        fbn fbnVarK = delegate.K();
        c cVarL = delegate.L();
        int iJ = delegate.J();
        boolean zO = delegate.O();
        boolean zQ = delegate.Q();
        Objects.requireNonNull(delegate);
        Runnable runnable = delegate::a;
        Objects.requireNonNull(delegate);
        super(name, fbnVarK, cVarL, iJ, zO, zQ, runnable, delegate::b);
        this.name = name;
        this.delegate = delegate;
        this.customRenderStateShards = List.of((Object[]) customRenderStateShards);
    }

    public void a() {
        super.a();
        for (gfg customRenderStateShard : this.customRenderStateShards) {
            customRenderStateShard.a();
        }
    }

    public void b() {
        for (gfg customRenderStateShard : this.customRenderStateShards) {
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

    public static gfh lines(float lineWidth) {
        return LINES.apply(Float.valueOf(lineWidth));
    }

    public static gfh entityTranslucent(TextureBindingSet bindingSet) {
        return ENTITY_TRANSLUCENT.apply(bindingSet);
    }
}
