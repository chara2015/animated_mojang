package net.labymod.v1_21_3.client.gfx.pipeline.blaze3d.program;

import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.function.Function;
import net.labymod.api.Textures;
import net.labymod.api.laby3d.textures.TextureBindingSet;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/gfx/pipeline/blaze3d/program/LabyRenderType.class */
public final class LabyRenderType extends glv {
    private static final Function<Float, glv> LINES = ae.b(lineWidth -> {
        return new LabyRenderType("labymod_lines", glv.y(), new h(OptionalDouble.of(lineWidth.floatValue())));
    });
    private static final Function<TextureBindingSet, glv> ENTITY_TRANSLUCENT = ae.b(bindingSet -> {
        return new LabyRenderType("labymod_entity_translucent", glv.c((alz) Textures.WHITE.getMinecraftLocation(), false), new TextureBindingSetRenderStateShard(bindingSet));
    });
    private final String name;
    private final glv delegate;
    private final List<glu> customRenderStateShards;

    /* JADX WARN: Illegal instructions before constructor call */
    public LabyRenderType(String name, glv delegate, glu... customRenderStateShards) {
        fgx fgxVarO = delegate.O();
        c cVarP = delegate.P();
        int iN = delegate.N();
        boolean zS = delegate.S();
        boolean zU = delegate.U();
        Objects.requireNonNull(delegate);
        Runnable runnable = delegate::a;
        Objects.requireNonNull(delegate);
        super(name, fgxVarO, cVarP, iN, zS, zU, runnable, delegate::b);
        this.name = name;
        this.delegate = delegate;
        this.customRenderStateShards = List.of((Object[]) customRenderStateShards);
    }

    public void a() {
        super.a();
        for (glu customRenderStateShard : this.customRenderStateShards) {
            customRenderStateShard.a();
        }
    }

    public void b() {
        for (glu customRenderStateShard : this.customRenderStateShards) {
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

    public static glv lines(float lineWidth) {
        return LINES.apply(Float.valueOf(lineWidth));
    }

    public static glv entityTranslucent(TextureBindingSet bindingSet) {
        return ENTITY_TRANSLUCENT.apply(bindingSet);
    }
}
