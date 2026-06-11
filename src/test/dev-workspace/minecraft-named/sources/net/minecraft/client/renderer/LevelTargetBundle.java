package net.minecraft.client.renderer;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.resource.ResourceHandle;
import java.util.Set;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/LevelTargetBundle.class */
public class LevelTargetBundle implements PostChain.TargetBundle {
    public static final Identifier MAIN_TARGET_ID = PostChain.MAIN_TARGET_ID;
    public static final Identifier TRANSLUCENT_TARGET_ID = Identifier.withDefaultNamespace("translucent");
    public static final Identifier ITEM_ENTITY_TARGET_ID = Identifier.withDefaultNamespace("item_entity");
    public static final Identifier PARTICLES_TARGET_ID = Identifier.withDefaultNamespace("particles");
    public static final Identifier WEATHER_TARGET_ID = Identifier.withDefaultNamespace("weather");
    public static final Identifier CLOUDS_TARGET_ID = Identifier.withDefaultNamespace("clouds");
    public static final Identifier ENTITY_OUTLINE_TARGET_ID = Identifier.withDefaultNamespace("entity_outline");
    public static final Set<Identifier> MAIN_TARGETS = Set.of(MAIN_TARGET_ID);
    public static final Set<Identifier> OUTLINE_TARGETS = Set.of(MAIN_TARGET_ID, ENTITY_OUTLINE_TARGET_ID);
    public static final Set<Identifier> SORTING_TARGETS = Set.of(MAIN_TARGET_ID, TRANSLUCENT_TARGET_ID, ITEM_ENTITY_TARGET_ID, PARTICLES_TARGET_ID, WEATHER_TARGET_ID, CLOUDS_TARGET_ID);
    public ResourceHandle<RenderTarget> main = ResourceHandle.invalid();
    public ResourceHandle<RenderTarget> translucent;
    public ResourceHandle<RenderTarget> itemEntity;
    public ResourceHandle<RenderTarget> particles;
    public ResourceHandle<RenderTarget> weather;
    public ResourceHandle<RenderTarget> clouds;
    public ResourceHandle<RenderTarget> entityOutline;

    @Override // net.minecraft.client.renderer.PostChain.TargetBundle
    public void replace(Identifier $$0, ResourceHandle<RenderTarget> $$1) {
        if ($$0.equals(MAIN_TARGET_ID)) {
            this.main = $$1;
            return;
        }
        if ($$0.equals(TRANSLUCENT_TARGET_ID)) {
            this.translucent = $$1;
            return;
        }
        if ($$0.equals(ITEM_ENTITY_TARGET_ID)) {
            this.itemEntity = $$1;
            return;
        }
        if ($$0.equals(PARTICLES_TARGET_ID)) {
            this.particles = $$1;
            return;
        }
        if ($$0.equals(WEATHER_TARGET_ID)) {
            this.weather = $$1;
        } else if ($$0.equals(CLOUDS_TARGET_ID)) {
            this.clouds = $$1;
        } else {
            if ($$0.equals(ENTITY_OUTLINE_TARGET_ID)) {
                this.entityOutline = $$1;
                return;
            }
            throw new IllegalArgumentException("No target with id " + String.valueOf($$0));
        }
    }

    @Override // net.minecraft.client.renderer.PostChain.TargetBundle
    public ResourceHandle<RenderTarget> get(Identifier $$0) {
        if ($$0.equals(MAIN_TARGET_ID)) {
            return this.main;
        }
        if ($$0.equals(TRANSLUCENT_TARGET_ID)) {
            return this.translucent;
        }
        if ($$0.equals(ITEM_ENTITY_TARGET_ID)) {
            return this.itemEntity;
        }
        if ($$0.equals(PARTICLES_TARGET_ID)) {
            return this.particles;
        }
        if ($$0.equals(WEATHER_TARGET_ID)) {
            return this.weather;
        }
        if ($$0.equals(CLOUDS_TARGET_ID)) {
            return this.clouds;
        }
        if ($$0.equals(ENTITY_OUTLINE_TARGET_ID)) {
            return this.entityOutline;
        }
        return null;
    }

    public void clear() {
        this.main = ResourceHandle.invalid();
        this.translucent = null;
        this.itemEntity = null;
        this.particles = null;
        this.weather = null;
        this.clouds = null;
        this.entityOutline = null;
    }
}
