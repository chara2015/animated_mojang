package net.labymod.v1_21_5.client.gfx.pipeline.blaze3d.program;

import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.function.Function;
import net.labymod.api.Textures;
import net.labymod.api.laby3d.textures.TextureBindingSet;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/client/gfx/pipeline/blaze3d/program/LabyRenderType.class */
public final class LabyRenderType extends a {
    private static final Function<Float, gry> LINES = ag.b(lineWidth -> {
        return new LabyRenderType("labymod_lines", gry.w(), new e(OptionalDouble.of(lineWidth.floatValue())));
    });
    private static final Function<TextureBindingSet, gry> ENTITY_TRANSLUCENT = ag.b(bindingSet -> {
        return new LabyRenderType("labymod_entity_translucent", gry.c((alr) Textures.WHITE.getMinecraftLocation(), false), new TextureBindingSetRenderStateShard(bindingSet));
    });
    private final String name;
    private final gry delegate;
    private final List<grx> customRenderStateShards;

    public LabyRenderType(String name, gry delegate, grx... customRenderStateShards) {
        super(name, delegate.O(), delegate.T(), delegate.V(), delegate.M(), ((a) delegate).E);
        this.name = name;
        this.delegate = delegate;
        this.customRenderStateShards = List.of((Object[]) customRenderStateShards);
    }

    public void a() {
        super.a();
        for (grx customRenderStateShard : this.customRenderStateShards) {
            customRenderStateShard.a();
        }
    }

    public void b() {
        for (grx customRenderStateShard : this.customRenderStateShards) {
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

    public static gry lines(float lineWidth) {
        return LINES.apply(Float.valueOf(lineWidth));
    }

    public static gry entityTranslucent(TextureBindingSet bindingSet) {
        return ENTITY_TRANSLUCENT.apply(bindingSet);
    }
}
