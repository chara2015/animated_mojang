package net.labymod.v1_16_5.mixins.client.gui.components;

import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter;
import net.labymod.api.client.gui.screen.widget.converter.WidgetWatcher;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import net.labymod.v1_16_5.client.gui.EditBoxAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/gui/components/MixinEditBox.class */
@Mixin({dlq.class})
public abstract class MixinEditBox extends MixinAbstractWidget implements EditBoxAccessor {

    @Shadow
    private boolean s;

    @Shadow
    private boolean t;

    @Shadow
    private boolean u;
    private dfm labyMod$poseStack;
    private Consumer<String> labyMod$valueConsumer;

    @Override // net.labymod.v1_16_5.mixins.client.gui.components.MixinAbstractWidget
    @Insert(method = {"renderButton"}, at = @At("HEAD"), cancellable = true)
    public void render(dfm poseStack, int mouseX, int mouseY, float partialTicks, InsertInfo ci) {
        this.labyMod$poseStack = poseStack;
        getWatcher().update(this, ((dlh) this).i());
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            boolean rendered = getWatcher().render(((VanillaStackAccessor) poseStack).stack(), mouse, partialTicks);
            if (rendered) {
                ci.cancel();
            }
        });
    }

    @Redirect(method = {"renderHighlight"}, at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;vertex(DDD)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private dfq labyMod$addStack(dfh instance, double x, double y, double z) {
        return instance.a(this.labyMod$poseStack.c().a(), (float) x, (float) y, (float) z);
    }

    @Inject(method = {"mouseClicked"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$exitBoxMouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        WidgetWatcher<?> watcher = getWatcher();
        AbstractWidget widget = watcher.getWidget();
        if (widget == null) {
            return;
        }
        AbstractWidgetConverter<?, ?> widgetConverter = watcher.getWidgetConverter();
        int x = ((dlq) this).l;
        int y = ((dlq) this).m;
        int width = ((dlq) this).h();
        int height = ((dlq) this).e();
        boolean hovered = mouseX >= ((double) x) && mouseX < ((double) (x + width)) && mouseY >= ((double) y) && mouseY < ((double) (y + height));
        if (this.s) {
            d(hovered);
            widget.setFocused(hovered);
        }
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            MouseButton mouseButton = DefaultKeyMapper.pressMouse(button);
            if (mouseButton == null) {
                return;
            }
            cir.setReturnValue(Boolean.valueOf(widgetConverter.mouseClicked(widget, mouse, mouseButton)));
        });
    }

    @Inject(method = {"charTyped"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$charTyped(char c, int param1, CallbackInfoReturnable<Boolean> cir) {
        WidgetWatcher<?> watcher = getWatcher();
        AbstractWidget widget = watcher.getWidget();
        if (widget == null) {
            return;
        }
        Key key = DefaultKeyMapper.lastPressed();
        cir.setReturnValue(Boolean.valueOf(watcher.getWidgetConverter().charTyped(widget, key, c)));
    }

    @Inject(method = {"keyPressed"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$keyPressed(int keyCode, int param1, int param2, CallbackInfoReturnable<Boolean> cir) {
        WidgetWatcher<?> watcher = getWatcher();
        AbstractWidget widget = watcher.getWidget();
        if (widget == null) {
            return;
        }
        Key key = DefaultKeyMapper.lastPressed();
        cir.setReturnValue(Boolean.valueOf(watcher.getWidgetConverter().keyPressed(widget, key, KeyMapper.getInputType(key))));
    }

    @Inject(method = {"onValueChange"}, at = {@At("HEAD")})
    private void labyMod$callValueConsumer(String value, CallbackInfo ci) {
        if (this.labyMod$valueConsumer == null) {
            return;
        }
        this.labyMod$valueConsumer.accept(value);
    }

    @Inject(method = {"mouseClicked"}, at = {@At("HEAD")})
    private void labyMod$refreshShiftPressed(double param0, double param1, int param2, CallbackInfoReturnable<Boolean> cir) {
        if (this.u) {
            this.u = dot.y();
        }
    }

    @Override // net.labymod.v1_16_5.client.gui.EditBoxAccessor
    public void setValueConsumer(Consumer<String> valueConsumer) {
        this.labyMod$valueConsumer = valueConsumer;
    }

    @Override // net.labymod.v1_16_5.client.gui.EditBoxAccessor
    public boolean isEditable() {
        return this.t;
    }
}
