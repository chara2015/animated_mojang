package net.labymod.core.labyconnect.object.lootbox;

import java.util.Collections;
import java.util.Locale;
import net.laby.lib.bedrock.animation.AnimationFile;
import net.laby.lib.bedrock.geometry.GeometryFile;
import net.laby.lib.incentives.IncentiveService;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.render.model.ModelService;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.TextureDetails;
import net.labymod.core.main.user.shop.item.geometry.BedrockModelLoader;
import net.labymod.core.main.user.shop.item.geometry.animation.AnimationLoader;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/object/lootbox/LootBox.class */
public class LootBox {
    private static final String AVAILABLE_ID = "available";
    private static final String OPENED_ID = "opened";
    private final int type;
    private final String id;
    private final IncentiveService incentiveService;

    @Nullable
    private LootBoxModel model;

    @Nullable
    private AnimationLoader animationLoader;
    private final AnimationController availableController;
    private final AnimationController openedController;
    private ModelAnimation availableAnimation;
    private ModelAnimation openedAnimation;

    public LootBox(int type, String id, IncentiveService incentiveService) {
        this.type = type;
        this.id = id;
        this.incentiveService = incentiveService;
        ModelService modelService = Laby.references().modelService();
        this.availableController = modelService.createAnimationController();
        this.openedController = modelService.createAnimationController();
    }

    public void tick() {
        LootBoxModel model = getModel();
        if (model == null) {
            return;
        }
        if (!this.availableController.isPlaying() && this.availableAnimation != null) {
            this.availableController.playNext(this.availableAnimation);
        }
        if (!this.openedController.isPlaying() && this.openedAnimation != null) {
            this.openedController.playNext(this.openedAnimation);
        }
    }

    public void loadModel() {
        this.incentiveService.getGeometry(this.id).ifSuccess(this::loadModel).ifFailure(error -> {
            throw new LootBoxException("No lootbox model for " + this.type + ":" + this.id + " is present! (" + error.message() + ")");
        });
        this.incentiveService.getAnimation(this.id).ifSuccess(this::loadAnimations).ifFailure(error2 -> {
            throw new LootBoxException("No lootbox animations for " + this.type + ":" + this.id + " is present! (" + error2.message() + ")");
        });
        ResourceLocation textureLocation = ResourceLocation.create("labymod", "incentives/" + this.type + "/texture.png");
        TextureDetails details = TextureDetails.builder(textureLocation).withFallbackLocation(Textures.WHITE).withUrl(String.format(Locale.ROOT, Constants.Urls.REMOTE_INCENTIVES_TEXTURE, this.id)).withFinishHandler(texture -> {
            if (this.model != null) {
                this.model.setTextureLocation(textureLocation);
            }
        }).build();
        CompletableResourceLocation location = Laby.references().textureRepository().getOrRegisterTexture(details);
        location.addCompletableListener(() -> {
            if (this.model != null) {
                this.model.setTextureLocation(textureLocation);
            }
        });
    }

    private void loadModel(GeometryFile geometryFile) {
        BedrockModelLoader loader = BedrockModelLoader.create(geometryFile);
        this.model = new LootBoxModel(loader.getModel(), loader.getEffects());
    }

    private void loadAnimations(AnimationFile animationFile) {
        this.animationLoader = AnimationLoader.fromAnimationFile(animationFile, Collections.emptyList());
        this.availableAnimation = this.animationLoader.getAnimation(AVAILABLE_ID);
        this.openedAnimation = this.animationLoader.getAnimation(OPENED_ID);
    }

    public int getType() {
        return this.type;
    }

    public String getId() {
        return this.id;
    }

    public AnimationController getAnimationController(boolean available) {
        return available ? this.availableController : this.openedController;
    }

    @Nullable
    public LootBoxModel getModel() {
        return this.model;
    }

    @Nullable
    public AnimationLoader getAnimationLoader() {
        return this.animationLoader;
    }

    public LootBox copy() {
        LootBox lootBox = new LootBox(this.type, this.id, this.incentiveService);
        lootBox.model = this.model == null ? null : this.model.copy();
        lootBox.animationLoader = this.animationLoader;
        lootBox.availableAnimation = this.availableAnimation;
        lootBox.openedAnimation = this.openedAnimation;
        return lootBox;
    }
}
