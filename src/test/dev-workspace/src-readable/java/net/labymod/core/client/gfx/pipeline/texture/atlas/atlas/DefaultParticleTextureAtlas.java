package net.labymod.core.client.gfx.pipeline.texture.atlas.atlas;

import net.labymod.api.Textures;
import net.labymod.core.client.gfx.pipeline.texture.atlas.AbstractTextureAtlas;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/texture/atlas/atlas/DefaultParticleTextureAtlas.class */
public class DefaultParticleTextureAtlas extends AbstractTextureAtlas {
    public DefaultParticleTextureAtlas() {
        super(Textures.Splash.PARTICLES, 128, 128);
        register8(createLabyMod("particle/flame"), 0, 3);
        register8(createLabyMod("particle/soul_fire_flame"), 0, 4);
        registerAnimatedHorizontal(createLabyMod("particle/generic"), 0, 0, 8, 8, 8);
    }
}
