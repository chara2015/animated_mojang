package net.labymod.v1_20_6.client.gfx.pipeline.blaze3d.program;

import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.function.Function;
import net.labymod.api.Textures;
import net.labymod.api.laby3d.textures.TextureBindingSet;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/client/gfx/pipeline/blaze3d/program/LabyRenderType.class */
public final class LabyRenderType extends gdy {
    private static final Function<Float, gdy> LINES = ac.b(lineWidth -> {
        return new LabyRenderType("labymod_lines", gdy.y(), new h(OptionalDouble.of(lineWidth.floatValue())));
    });
    private static final Function<TextureBindingSet, gdy> ENTITY_TRANSLUCENT = ac.b(bindingSet -> {
        return new LabyRenderType("labymod_entity_translucent", gdy.c((alf) Textures.WHITE.getMinecraftLocation(), false), new TextureBindingSetRenderStateShard(bindingSet));
    });
    private final String name;
    private final gdy delegate;
    private final List<gdx> customRenderStateShards;

    /* JADX WARN: Illegal instructions before constructor call */
    public LabyRenderType(String name, gdy delegate, gdx... customRenderStateShards) {
        faf fafVarJ = delegate.J();
        b bVarK = delegate.K();
        int I = delegate.I();
        boolean zN = delegate.N();
        boolean z = delegate.cf;
        Objects.requireNonNull(delegate);
        Runnable runnable = delegate::a;
        Objects.requireNonNull(delegate);
        super(name, fafVarJ, bVarK, I, zN, z, runnable, delegate::b);
        this.name = name;
        this.delegate = delegate;
        this.customRenderStateShards = List.of((Object[]) customRenderStateShards);
    }

    public void a() {
        super.a();
        for (gdx customRenderStateShard : this.customRenderStateShards) {
            customRenderStateShard.a();
        }
    }

    public void b() {
        for (gdx customRenderStateShard : this.customRenderStateShards) {
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

    public static gdy lines(float lineWidth) {
        return LINES.apply(Float.valueOf(lineWidth));
    }

    public static gdy entityTranslucent(TextureBindingSet bindingSet) {
        return ENTITY_TRANSLUCENT.apply(bindingSet);
    }
}
