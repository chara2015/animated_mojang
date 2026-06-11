package net.labymod.api.client.world.signobject;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.BlockPosition;
import net.labymod.api.client.world.canvas.Canvas;
import net.labymod.api.client.world.canvas.template.CanvasTemplate;
import net.labymod.api.client.world.signobject.object.SignObject;
import net.labymod.api.client.world.signobject.template.SignObjectFactory;
import net.labymod.api.client.world.signobject.template.SignObjectMeta;
import net.labymod.api.client.world.signobject.template.SignObjectTemplate;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/signobject/SignObjectRegistry.class */
@Referenceable
public interface SignObjectRegistry {
    <M extends SignObjectMeta<? extends SignObjectTemplate>> SignObject<M> createObject(SignObjectPosition signObjectPosition, String[] strArr);

    <O extends SignObject<M>, M extends SignObjectMeta<T>, T extends SignObjectTemplate> void registerFactory(T t, SignObjectFactory<O, M, T> signObjectFactory, Consumer<O> consumer);

    void registerCanvas(CanvasTemplate canvasTemplate, Consumer<Canvas> consumer);

    void registerCanvas(CanvasTemplate canvasTemplate, Supplier<Activity> supplier);

    void registerDummy(SignObjectTemplate signObjectTemplate, Consumer<SignObject<SignObjectMeta<SignObjectTemplate>>> consumer);

    @NotNull
    Collection<PlacedSignObject> getObjectsInWorld();

    Collection<SignObject<?>> getObjectsInWorld(@NotNull String str);

    Collection<SignObject<?>> getObjectsInWorld(@NotNull ResourceLocation resourceLocation);

    Collection<SignObject<?>> getObjectsInWorld(@NotNull Predicate<SignObject<?>> predicate);

    void getObjectsInWorld(@NotNull String str, @NotNull Consumer<SignObject<?>> consumer);

    void getObjectsInWorld(@NotNull ResourceLocation resourceLocation, @NotNull Consumer<SignObject<?>> consumer);

    void getObjectsInWorld(@NotNull Predicate<SignObject<?>> predicate, @NotNull Consumer<SignObject<?>> consumer);

    @Nullable
    PlacedSignObject getObjectInWorld(@NotNull BlockPosition blockPosition);

    default <O extends SignObject<M>, M extends SignObjectMeta<T>, T extends SignObjectTemplate> void registerFactory(T template, SignObjectFactory<O, M, T> factory) {
        registerFactory(template, factory, null);
    }
}
