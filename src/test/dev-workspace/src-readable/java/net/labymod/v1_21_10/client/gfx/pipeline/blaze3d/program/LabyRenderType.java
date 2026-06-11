package net.labymod.v1_21_10.client.gfx.pipeline.blaze3d.program;

import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.function.Function;
import net.labymod.api.Textures;
import net.labymod.api.laby3d.textures.TextureBindingSet;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/client/gfx/pipeline/blaze3d/program/LabyRenderType.class */
public final class LabyRenderType extends a {
    private static final Function<Float, hgk> LINES = ag.b(lineWidth -> {
        return new LabyRenderType("labymod_lines", hgk.v(), new e(OptionalDouble.of(lineWidth.floatValue())));
    });
    private static final Function<TextureBindingSet, hgk> ENTITY_TRANSLUCENT = ag.b(bindingSet -> {
        return new LabyRenderType("labymod_entity_translucent", hgk.c((amj) Textures.WHITE.getMinecraftLocation(), false), new TextureBindingSetRenderStateShard(bindingSet));
    });
    private final String name;
    private final hgk delegate;
    private final List<hgj> customRenderStateShards;

    public LabyRenderType(String name, hgk delegate, hgj... customRenderStateShards) {
        super(name, delegate.D(), delegate.J(), delegate.L(), delegate.I(), ((a) delegate).C);
        this.name = name;
        this.delegate = delegate;
        this.customRenderStateShards = List.of((Object[]) customRenderStateShards);
    }

    public void a() {
        super.a();
        for (hgj customRenderStateShard : this.customRenderStateShards) {
            customRenderStateShard.a();
        }
    }

    public void b() {
        for (hgj customRenderStateShard : this.customRenderStateShards) {
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

    public static hgk lines(float lineWidth) {
        return LINES.apply(Float.valueOf(lineWidth));
    }

    public static hgk entityTranslucent(TextureBindingSet bindingSet) {
        return ENTITY_TRANSLUCENT.apply(bindingSet);
    }
}
