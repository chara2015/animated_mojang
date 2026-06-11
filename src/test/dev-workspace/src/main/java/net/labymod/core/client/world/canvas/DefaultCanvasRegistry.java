package net.labymod.core.client.world.canvas;

import java.util.function.Supplier;
import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.canvas.CanvasRegistry;
import net.labymod.api.client.world.canvas.CanvasRendererFactory;
import net.labymod.api.client.world.canvas.template.CanvasTemplate;
import net.labymod.api.client.world.signobject.SignObjectRegistry;
import net.labymod.api.models.Implements;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/world/canvas/DefaultCanvasRegistry.class */
@Singleton
@Implements(CanvasRegistry.class)
public class DefaultCanvasRegistry implements CanvasRegistry {
    private final SignObjectRegistry registry;

    public DefaultCanvasRegistry(SignObjectRegistry registry) {
        this.registry = registry;
    }

    @Override // net.labymod.api.client.world.canvas.CanvasRegistry
    public void registerRenderer(@NotNull ResourceLocation identifier, @NotNull CanvasRendererFactory factory) {
        this.registry.registerCanvas(CanvasTemplate.simple(identifier), canvas -> {
            canvas.setRenderer(factory.createRenderer(canvas.meta()));
        });
    }

    @Override // net.labymod.api.client.world.canvas.CanvasRegistry
    public void registerActivity(@NotNull ResourceLocation identifier, @NotNull Supplier<Activity> supplier) {
        this.registry.registerCanvas(CanvasTemplate.simple(identifier), supplier);
    }
}
