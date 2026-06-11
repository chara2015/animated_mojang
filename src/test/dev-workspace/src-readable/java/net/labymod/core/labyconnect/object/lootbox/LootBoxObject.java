package net.labymod.core.labyconnect.object.lootbox;

import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.object.AbstractWorldObject;
import net.labymod.api.client.world.object.CullVolume;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.core.client.gui.screen.activity.activities.ingame.event.LootBoxActivity;
import net.labymod.core.labyconnect.object.lootbox.content.LootBoxContent;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.user.shop.item.geometry.animation.AnimationLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/object/lootbox/LootBoxObject.class */
public class LootBoxObject extends AbstractWorldObject {
    private static final float HALF_WIDTH = 1.0f;
    private static final float HEIGHT = 2.0f;
    private final Player owner;
    private final float rotation;
    private final LootBoxContent content;
    private final AnimationController animationController;
    private final ResourceLocation textureLocation;
    private Model model;
    private boolean priceRevealed;

    public LootBoxObject(@NotNull Player owner, @NotNull DoubleVector3 location, float rotation, @NotNull LootBoxContent content, int lootBoxType) {
        super(location);
        this.priceRevealed = false;
        this.owner = owner;
        this.rotation = rotation;
        this.content = content;
        LootBoxService service = LabyMod.references().lootBoxService();
        LootBox lootBox = service.byId(lootBoxType);
        if (lootBox == null || lootBox.getModel() == null) {
            this.animationController = null;
            this.textureLocation = null;
            return;
        }
        LootBoxModel model = lootBox.getModel();
        AnimationLoader animationLoader = lootBox.getAnimationLoader();
        if (animationLoader == null) {
            this.animationController = null;
        } else {
            this.animationController = Laby.references().modelService().createAnimationController().hiddenPart(part -> {
                return false;
            });
            ModelAnimation animation = animationLoader.getAnimation("open");
            if (animation != null) {
                this.animationController.playNext(animation);
            }
        }
        this.model = model.model();
        this.textureLocation = model.textureLocation();
    }

    @Override // net.labymod.api.client.world.object.WorldObject
    public void onRemove() {
        Minecraft minecraft = Laby.labyAPI().minecraft();
        if (minecraft.getClientPlayer() == this.owner) {
            Window window = minecraft.minecraftWindow();
            window.displayScreen(LootBoxActivity.showPrice(this.content));
        }
    }

    @Override // net.labymod.api.client.world.object.WorldObject
    @NotNull
    public CullVolume cullVolume() {
        DoubleVector3 position = position();
        return CullVolume.box(position.getX() - 1.0d, position.getY(), position.getZ() - 1.0d, position.getX() + 1.0d, position.getY() + 2.0d, position.getZ() + 1.0d);
    }

    @Override // net.labymod.api.client.world.object.WorldObject
    public boolean shouldRemove() {
        return this.animationController == null || !this.animationController.isPlaying();
    }

    @NotNull
    public Player getOwner() {
        return this.owner;
    }

    public float getRotation() {
        return this.rotation;
    }

    @NotNull
    public LootBoxContent getContent() {
        return this.content;
    }

    @Nullable
    public Model getModel() {
        return this.model;
    }

    @Nullable
    public ResourceLocation getTextureLocation() {
        return this.textureLocation;
    }

    @Nullable
    public AnimationController getAnimationController() {
        return this.animationController;
    }

    public boolean isPriceRevealed() {
        return this.priceRevealed;
    }

    public void markPriceRevealed() {
        this.priceRevealed = true;
    }
}
