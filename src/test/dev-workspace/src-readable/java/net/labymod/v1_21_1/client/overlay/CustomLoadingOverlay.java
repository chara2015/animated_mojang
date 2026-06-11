package net.labymod.v1_21_1.client.overlay;

import java.util.Optional;
import java.util.function.Consumer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.core.client.gui.background.bootlogo.AbstractBootLogoRenderer;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/client/overlay/CustomLoadingOverlay.class */
public class CustomLoadingOverlay extends fnt {
    private final fgo minecraft;
    private final aua reload;
    private final Consumer<Optional<Throwable>> onFinish;
    private final AbstractBootLogoRenderer loadingRenderer;
    private final DynamicBackgroundController caveRenderer;

    public CustomLoadingOverlay(fgo minecraft, aua reload, Consumer<Optional<Throwable>> onFinish, boolean fadeIn) {
        super(minecraft, reload, onFinish, fadeIn);
        this.minecraft = minecraft;
        this.reload = reload;
        this.onFinish = onFinish;
        this.loadingRenderer = LabyMod.references().bootLogoController().renderer();
        this.caveRenderer = LabyMod.references().dynamicBackgroundController();
        this.loadingRenderer.initialize();
        this.loadingRenderer.preloadResources();
    }

    public void a(fhz gui, int mouseX, int mouseY, float partialTicks) {
        fam window = this.minecraft.aM();
        int width = window.p();
        int height = window.q();
        float progress = this.reload.b();
        this.loadingRenderer.updateProgress(progress, true);
        Stack apiStack = gui.c().stack();
        LabyMod.references().renderEnvironmentContext().screenContext().runInContext(apiStack, mouseX, mouseY, partialTicks, context -> {
            this.loadingRenderer.render(context, 0.0f, 0.0f, width, height);
        });
        if (this.reload.c()) {
            this.minecraft.a((fnx) null);
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
