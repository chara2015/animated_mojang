package net.labymod.v1_20_1.client.gfx.pipeline.blaze3d.program;

import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.function.Function;
import net.labymod.api.Textures;
import net.labymod.api.laby3d.textures.TextureBindingSet;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/client/gfx/pipeline/blaze3d/program/LabyRenderType.class */
public final class LabyRenderType extends fkf {
    private static final Function<Float, fkf> LINES = ac.b(lineWidth -> {
        return new LabyRenderType("labymod_lines", fkf.x(), new h(OptionalDouble.of(lineWidth.floatValue())));
    });
    private static final Function<TextureBindingSet, fkf> ENTITY_TRANSLUCENT = ac.b(bindingSet -> {
        return new LabyRenderType("labymod_entity_translucent", fkf.c((acq) Textures.WHITE.getMinecraftLocation(), false), new TextureBindingSetRenderStateShard(bindingSet));
    });
    private final String name;
    private final fkf delegate;
    private final List<fke> customRenderStateShards;

    /* JADX WARN: Illegal instructions before constructor call */
    public LabyRenderType(String name, fkf delegate, fke... customRenderStateShards) {
        eio eioVarI = delegate.I();
        b bVarJ = delegate.J();
        int iH = delegate.H();
        boolean zM = delegate.M();
        boolean z = delegate.cf;
        Objects.requireNonNull(delegate);
        Runnable runnable = delegate::a;
        Objects.requireNonNull(delegate);
        super(name, eioVarI, bVarJ, iH, zM, z, runnable, delegate::b);
        this.name = name;
        this.delegate = delegate;
        this.customRenderStateShards = List.of((Object[]) customRenderStateShards);
    }

    public void a() {
        super.a();
        for (fke customRenderStateShard : this.customRenderStateShards) {
            customRenderStateShard.a();
        }
    }

    public void b() {
        for (fke customRenderStateShard : this.customRenderStateShards) {
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

    public static fkf lines(float lineWidth) {
        return LINES.apply(Float.valueOf(lineWidth));
    }

    public static fkf entityTranslucent(TextureBindingSet bindingSet) {
        return ENTITY_TRANSLUCENT.apply(bindingSet);
    }
}
