package net.labymod.core.labyconnect.object.lootbox;

import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.object.snapshot.AbstractWorldObjectSnapshot;
import net.labymod.api.laby3d.renderer.snapshot.Extras;
import net.labymod.core.labyconnect.object.lootbox.content.LootBoxContent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/object/lootbox/LootBoxSnapshot.class */
public final class LootBoxSnapshot extends AbstractWorldObjectSnapshot {
    private final boolean clientPlayer;
    private final float rotation;
    private final LootBoxContent content;
    private final Model model;
    private final ResourceLocation textureLocation;
    private final AnimationController animationController;
    private final long animationProgress;
    private final boolean priceRevealed;
    private final boolean shouldRevealPrice;
    private final float cameraYaw;
    private final float cameraPitch;

    public LootBoxSnapshot(double x, double y, double z, int lightCoords, boolean clientPlayer, float rotation, @NotNull LootBoxContent content, @Nullable Model model, @Nullable ResourceLocation textureLocation, @Nullable AnimationController animationController, long animationProgress, boolean priceRevealed, boolean shouldRevealPrice, float cameraYaw, float cameraPitch) {
        super(x, y, z, lightCoords, Extras.empty());
        this.clientPlayer = clientPlayer;
        this.rotation = rotation;
        this.content = content;
        this.model = model;
        this.textureLocation = textureLocation;
        this.animationController = animationController;
        this.animationProgress = animationProgress;
        this.priceRevealed = priceRevealed;
        this.shouldRevealPrice = shouldRevealPrice;
        this.cameraYaw = cameraYaw;
        this.cameraPitch = cameraPitch;
    }

    public boolean isClientPlayer() {
        return this.clientPlayer;
    }

    public float rotation() {
        return this.rotation;
    }

    @NotNull
    public LootBoxContent content() {
        return this.content;
    }

    @Nullable
    public Model model() {
        return this.model;
    }

    @Nullable
    public ResourceLocation textureLocation() {
        return this.textureLocation;
    }

    @Nullable
    public AnimationController animationController() {
        return this.animationController;
    }

    public long animationProgress() {
        return this.animationProgress;
    }

    public boolean priceRevealed() {
        return this.priceRevealed;
    }

    public boolean shouldRevealPrice() {
        return this.shouldRevealPrice;
    }

    public float cameraYaw() {
        return this.cameraYaw;
    }

    public float cameraPitch() {
        return this.cameraPitch;
    }

    public boolean hasModel() {
        return (this.model == null || this.textureLocation == null) ? false : true;
    }

    public boolean isAnimationComplete() {
        return (this.animationController == null || this.animationController.isPlaying()) ? false : true;
    }
}
