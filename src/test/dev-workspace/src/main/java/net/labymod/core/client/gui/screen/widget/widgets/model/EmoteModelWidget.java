package net.labymod.core.client.gui.screen.widget.widgets.model;

import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.DefaultModelBuffer;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.ModelBuffer;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.client.render.model.animation.meta.AnimationTrigger;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.core.main.user.shop.emote.animation.EmoteAnimationMeta;
import net.labymod.core.main.user.shop.emote.model.EmoteItem;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/model/EmoteModelWidget.class */
@AutoWidget
public class EmoteModelWidget extends CosmeticModelWidget {
    private static final float PROPS_SHIFT = -1.5f;
    protected EmoteItem emoteItem;
    protected boolean cosmetics;
    protected Model propsModel;
    protected ModelBuffer propsModelBuffer;
    private boolean forceProjectionMatrix;
    private Model originalModel;

    public EmoteModelWidget(ResourceLocation id, Model model, AnimationController animationController, float modelWidth, float modelHeight, EmoteItem emoteItem, boolean cosmetics) {
        super(id, model, animationController, modelWidth, modelHeight);
        this.forceProjectionMatrix = true;
        this.cosmetics = cosmetics;
        if (emoteItem != null) {
            setEmote(emoteItem);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.ModelWidget
    public void renderModel(ScreenContext context, int color, float tickDelta) {
        if (this.emoteItem != null && (!this.animationController.isPlaying() || !((Boolean) this.animationController.getPlaying().getMetaDefault(EmoteAnimationMeta.START_ANIMATION, false)).booleanValue())) {
            this.emoteItem.animationContainer().handleAnimationTrigger(AnimationTrigger.IDLE, this.animationController, null);
        }
        super.renderModel(context, color, tickDelta);
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.model.CosmeticModelWidget, net.labymod.api.client.gui.screen.widget.widgets.ModelWidget
    protected void renderModelAttachments(CommandBuffer cmd, Stack stack, int lightCoords, float tickDelta) {
        if (this.cosmetics) {
            super.renderModelAttachments(cmd, stack, lightCoords, tickDelta);
        }
        if (this.emoteItem == null || this.propsModel == null || this.propsModelBuffer == null || !this.animationController.isPlaying()) {
            return;
        }
        this.animationController.applyAnimation(this.propsModel, new String[0]);
        this.emoteItem.updateAnimatedTexture(this.propsModelBuffer);
        stack.push();
        stack.translate(0.0f, PROPS_SHIFT, 0.0f);
        this.propsModelBuffer.render(cmd, stack);
        stack.pop();
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.model.CosmeticModelWidget, net.labymod.api.client.gui.screen.widget.widgets.ModelWidget
    public void setModel(Model model) {
        if (this.emoteItem != null && this.emoteItem.getAlexModel() != null && this.emoteItem.getSteveModel() != null) {
            boolean slim = model.metadata().get("variant", MinecraftServices.SkinVariant.CLASSIC) == MinecraftServices.SkinVariant.SLIM;
            Model emoteModel = slim ? this.emoteItem.getAlexModel() : this.emoteItem.getSteveModel();
            if (emoteModel != null) {
                this.originalModel = model;
                super.setModel(emoteModel.copy());
                return;
            }
        }
        super.setModel(model);
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.ModelWidget
    public void rebuildModel() {
        super.rebuildModel();
        if (this.propsModelBuffer != null) {
            this.propsModelBuffer.rebuildModel();
        }
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.model.CosmeticModelWidget, net.labymod.api.client.gui.screen.widget.widgets.ModelWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.util.Disposable
    public void dispose() {
        super.dispose();
        if (this.propsModelBuffer != null) {
            this.propsModelBuffer.dispose();
        }
    }

    public void playEmote(EmoteItem emoteItem) {
        if (this.animationController.isPlaying()) {
            this.animationController.stop();
            this.model.reset();
        }
        setEmote(emoteItem);
        if (emoteItem != null) {
            this.animationController.playNext(emoteItem.getStartAnimation());
        }
    }

    public void stopEmote() {
        playEmote(null);
    }

    protected void setEmote(EmoteItem emoteItem) {
        this.emoteItem = emoteItem;
        this.propsModel = null;
        if (this.propsModelBuffer != null) {
            this.propsModelBuffer.dispose();
            this.propsModelBuffer = null;
        }
        if (emoteItem != null && emoteItem.getPropsModel() != null) {
            this.propsModel = emoteItem.getPropsModel().copy();
            if (this.propsModel != null) {
                this.propsModelBuffer = new DefaultModelBuffer(this.propsModel, true);
                this.propsModelBuffer.setResourceLocation(emoteItem.getPropsTextureLocation());
            }
        }
        setModel(this.originalModel == null ? this.model : this.originalModel);
    }

    public boolean isForceProjectionMatrix() {
        return this.forceProjectionMatrix;
    }

    public void setForceProjectionMatrix(boolean forceProjectionMatrix) {
        this.forceProjectionMatrix = forceProjectionMatrix;
    }
}
