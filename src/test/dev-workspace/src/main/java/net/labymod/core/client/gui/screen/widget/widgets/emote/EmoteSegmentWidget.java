package net.labymod.core.client.gui.screen.widget.widgets.emote;

import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.WheelWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.client.gui.screen.widget.widgets.model.EmoteModelWidget;
import net.labymod.core.client.render.model.animation.DefaultAnimationController;
import net.labymod.core.main.user.shop.emote.model.EmoteItem;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/emote/EmoteSegmentWidget.class */
@AutoWidget
public class EmoteSegmentWidget extends WheelWidget.Segment {
    private static final float Z_DISTANCE = 100.0f;
    private final EmoteItem emote;
    private final AnimationController animationController;
    private final boolean blocked;
    private final boolean cosmetics;
    private final Style emoteTextStyle;
    private final Model model;
    private EmoteModelWidget modelWidget;

    public EmoteSegmentWidget(EmoteItem emote, Model playerModel, boolean blocked) {
        this(emote, playerModel, blocked, false, Style.builder().color(NamedTextColor.WHITE).build());
    }

    public EmoteSegmentWidget(EmoteItem emote, Model model, boolean blocked, boolean cosmetics, Style emoteTextStyle) {
        this.emote = emote;
        this.animationController = new DefaultAnimationController();
        this.blocked = blocked;
        this.cosmetics = cosmetics;
        this.emoteTextStyle = emoteTextStyle;
        this.model = model;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        if (this.blocked) {
            super.addChild(new IconWidget(Textures.SpriteCommon.WHITE_X));
            this.modelWidget = null;
        } else {
            this.modelWidget = new EmoteModelWidget(ResourceLocation.create("labymod", "emote_model"), this.model, this.animationController, 16.0f, 32.0f, null, this.cosmetics);
            this.modelWidget.updatePlayer(Laby.labyAPI().getUniqueId());
            this.modelWidget.addId("emote-segment-model");
            super.addChild(this.modelWidget);
        }
        ComponentWidget componentWidgetComponent = ComponentWidget.component(Component.text(getEmoteName(), this.emoteTextStyle));
        componentWidgetComponent.addId("emote-segment-name");
        super.addChild(componentWidgetComponent);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        MutableMouse mouse = context.mouse();
        if (this.modelWidget != null) {
            Bounds bounds = bounds();
            float xDistance = bounds.getCenterX(BoundsType.INNER) - mouse.getX();
            float yDistance = (bounds.getY(BoundsType.INNER) + ((bounds.getHeight(BoundsType.INNER) * 0.0625f) * 2.0f)) - mouse.getY();
            double distance = Math.sqrt(MathHelper.square(xDistance) + MathHelper.square(yDistance) + MathHelper.square(Z_DISTANCE));
            float yaw = (float) Math.atan2(xDistance, 100.0d);
            float pitch = (float) (-Math.asin(((double) yDistance) / distance));
            this.modelWidget.rotation().set(0.0f, yaw, 0.0f);
            this.modelWidget.playerModel().getHead().getAnimationTransformation().setRotation(pitch, 0.0f, 0.0f);
        }
        super.renderWidget(context);
        if (this.modelWidget != null && this.emote != null) {
            if (super.isSegmentSelected() && !this.animationController.isPlaying()) {
                this.modelWidget.playEmote(this.emote);
            } else if (!super.isSegmentSelected() && this.animationController.isPlaying()) {
                this.modelWidget.stopEmote();
            }
        }
    }

    public EmoteItem getEmote() {
        return this.emote;
    }

    public void updateModel(Model model) {
        if (this.modelWidget != null) {
            this.modelWidget.setModel(model);
        }
    }

    public void updateModelAndTexture(Model model, ResourceLocation textureLocation) {
        if (this.modelWidget != null) {
            this.modelWidget.setModelAndTexture(model, textureLocation);
        }
    }

    private String getEmoteName() {
        if (this.emote == null) {
            return "?";
        }
        return this.emote.getName();
    }
}
