package net.labymod.v1_17_1.client.gfx.pipeline.blaze3d.program;

import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.function.Function;
import net.labymod.api.Textures;
import net.labymod.api.laby3d.textures.TextureBindingSet;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/client/gfx/pipeline/blaze3d/program/LabyRenderType.class */
public final class LabyRenderType extends enq {
    private static final Function<Float, enq> LINES = ad.a(lineWidth -> {
        return new LabyRenderType("labymod_lines", enq.v(), new g(OptionalDouble.of(lineWidth.floatValue())));
    });
    private static final Function<TextureBindingSet, enq> ENTITY_TRANSLUCENT = ad.a(bindingSet -> {
        return new LabyRenderType("labymod_entity_translucent", enq.c((ww) Textures.WHITE.getMinecraftLocation(), false), new TextureBindingSetRenderStateShard(bindingSet));
    });
    private final String name;
    private final enq delegate;
    private final List<enp> customRenderStateShards;

    /* JADX WARN: Illegal instructions before constructor call */
    public LabyRenderType(String name, enq delegate, enp... customRenderStateShards) {
        dqq dqqVarZ = delegate.z();
        b bVarA = delegate.A();
        int iY = delegate.y();
        boolean zD = delegate.D();
        boolean z = delegate.bK;
        Objects.requireNonNull(delegate);
        Runnable runnable = delegate::a;
        Objects.requireNonNull(delegate);
        super(name, dqqVarZ, bVarA, iY, zD, z, runnable, delegate::b);
        this.name = name;
        this.delegate = delegate;
        this.customRenderStateShards = List.of((Object[]) customRenderStateShards);
    }

    public void a() {
        super.a();
        for (enp customRenderStateShard : this.customRenderStateShards) {
            customRenderStateShard.a();
        }
    }

    public void b() {
        for (enp customRenderStateShard : this.customRenderStateShards) {
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

    public static enq lines(float lineWidth) {
        return LINES.apply(Float.valueOf(lineWidth));
    }

    public static enq entityTranslucent(TextureBindingSet bindingSet) {
        return ENTITY_TRANSLUCENT.apply(bindingSet);
    }
}
