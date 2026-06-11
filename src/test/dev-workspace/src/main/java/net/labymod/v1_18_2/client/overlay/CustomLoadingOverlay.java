package net.labymod.v1_18_2.client.overlay;

import java.util.Optional;
import java.util.function.Consumer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.core.client.gui.background.bootlogo.AbstractBootLogoRenderer;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/client/overlay/CustomLoadingOverlay.class */
public class CustomLoadingOverlay extends edj {
    private final dyr minecraft;
    private final afx reload;
    private final Consumer<Optional<Throwable>> onFinish;
    private final AbstractBootLogoRenderer loadingRenderer;
    private final DynamicBackgroundController caveRenderer;

    public CustomLoadingOverlay(dyr minecraft, afx reload, Consumer<Optional<Throwable>> onFinish, boolean fadeIn) {
        super(minecraft, reload, onFinish, fadeIn);
        this.minecraft = minecraft;
        this.reload = reload;
        this.onFinish = onFinish;
        this.loadingRenderer = LabyMod.references().bootLogoController().renderer();
        this.caveRenderer = LabyMod.references().dynamicBackgroundController();
        this.loadingRenderer.initialize();
        this.loadingRenderer.preloadResources();
    }

    public void a(dtm stack, int mouseX, int mouseY, float tickDelta) {
        dsr window = this.minecraft.aC();
        int width = window.o();
        int height = window.p();
        float progress = this.reload.b();
        this.loadingRenderer.updateProgress(progress, true);
        Stack apiStack = ((VanillaStackAccessor) stack).stack();
        LabyMod.references().renderEnvironmentContext().screenContext().runInContext(apiStack, mouseX, mouseY, tickDelta, context -> {
            this.loadingRenderer.render(context, 0.0f, 0.0f, width, height);
        });
        if (this.reload.c()) {
            this.minecraft.a((edq) null);
            try {
                this.reload.d();
                this.onFinish.accept(Optional.empty());
            } catch (Throwable throwable) {
                this.onFinish.accept(Optional.of(throwable));
            }
            if (this.minecraft.y != null) {
                this.minecraft.y.b(this.minecraft, width, height);
            }
            this.caveRenderer.playOpening();
        }
    }
}
