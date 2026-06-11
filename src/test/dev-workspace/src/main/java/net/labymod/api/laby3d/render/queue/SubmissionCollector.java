package net.labymod.api.laby3d.render.queue;

import java.util.function.Predicate;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.EnhancedModelPart;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.group.RenderGroup;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.laby3d.render.queue.submissions.IconSubmission;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.color.GradientDirection;
import net.labymod.laby3d.api.pipeline.RenderState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/render/queue/SubmissionCollector.class */
@Referenceable
public interface SubmissionCollector {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/render/queue/SubmissionCollector$SubmissionChannel.class */
    public interface SubmissionChannel extends AutoCloseable {
        @Override // java.lang.AutoCloseable
        void close();
    }

    @NotNull
    SubmissionCollector order(int i);

    void submitComponent(@NotNull Stack stack, @NotNull Component component, float f, float f2, int i, int i2, int i3, int i4);

    void submitIcon(@NotNull Stack stack, @NotNull Icon icon, @NotNull IconSubmission.DisplayMode displayMode, float f, float f2, float f3, float f4, int i);

    void submitIcon(@Nullable RenderState renderState, @NotNull Stack stack, @NotNull Icon icon, @NotNull IconSubmission.DisplayMode displayMode, float f, float f2, float f3, float f4, int i);

    void submitModel(@NotNull Stack stack, @NotNull Model model, @NotNull Material material, int i, int i2);

    void submitCustomGeometry(@NotNull Stack stack, @NotNull Material material, @NotNull CustomGeometryRenderer customGeometryRenderer);

    void submitRectangle(@NotNull Stack stack, float f, float f2, float f3, float f4, int i, int i2);

    void submitRectangle(@NotNull Material material, @NotNull Stack stack, float f, float f2, float f3, float f4, int i, int i2);

    void submitGradientRectangle(@NotNull Stack stack, @NotNull GradientDirection gradientDirection, float f, float f2, float f3, float f4, int i, int i2, int i3);

    void submitGradientRectangle(@NotNull Material material, @NotNull Stack stack, @NotNull GradientDirection gradientDirection, float f, float f2, float f3, float f4, int i, int i2, int i3);

    @NotNull
    SubmissionChannel channel(@Nullable ChannelType channelType);

    void submit(@NotNull Submission submission);

    @NotNull
    SubmissionBus bus();

    default void submitModel(@NotNull Stack stack, @NotNull Model model, @NotNull Material material, int lightCoords) {
        submitModel(stack, model, material, lightCoords, RenderEnvironmentContext.NO_OVERLAY);
    }

    default void submitModel(@NotNull Stack stack, @NotNull Model model, @NotNull Material material, @NotNull RenderGroup group, int lightCoords, int overlayCoords) {
        submitModel(stack, model, material, lightCoords, overlayCoords);
    }

    default void submitModelParts(@NotNull Stack stack, @NotNull Model model, @NotNull Material material, @NotNull Predicate<EnhancedModelPart> filter, int lightCoords, int overlayCoords) {
        submitModel(stack, model, material, lightCoords, overlayCoords);
    }
}
