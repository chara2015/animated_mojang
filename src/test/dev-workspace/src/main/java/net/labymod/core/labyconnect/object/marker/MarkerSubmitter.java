package net.labymod.core.labyconnect.object.marker;

import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.state.world.CameraSnapshot;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.object.submit.WorldObjectSubmitter;
import net.labymod.api.laby3d.render.queue.SubmissionCollector;
import net.labymod.api.laby3d.render.queue.submissions.IconSubmission;
import net.labymod.api.mojang.texture.MojangTextureService;
import net.labymod.api.mojang.texture.MojangTextureType;
import net.labymod.api.util.time.TimeUtil;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/object/marker/MarkerSubmitter.class */
public final class MarkerSubmitter extends WorldObjectSubmitter<MarkerObject, MarkerSnapshot> {
    private static final long FADE_IN_DURATION = 300;
    private static final int OUTLINE_COLOR = -6750208;
    private static final int BACKGROUND_COLOR = -1;
    private final MojangTextureService textureService = Laby.references().mojangTextureService();
    private final Icon backgroundIcon = Icon.texture(Textures.WHITE);

    @Override // net.labymod.api.client.world.object.submit.WorldObjectSubmitter
    @NotNull
    public MarkerSnapshot createSnapshot(@NotNull MarkerObject marker, double x, double y, double z, int lightCoords, @NotNull CameraSnapshot camera) {
        long now = TimeUtil.getMillis();
        long elapsed = now - marker.getTimestamp();
        long duration = marker.getDurationMillis();
        long remaining = duration - elapsed;
        float fadeOut = Math.min(1.0f, (1.0f / duration) * remaining * remaining);
        float fadeIn = (float) Math.cos((((((double) (Math.min(FADE_IN_DURATION, elapsed) / 300.0f)) * 3.141592653589793d) / 1.2999999523162842d) - 2.0943951023931953d) + 0.5d);
        float transition = fadeIn * fadeOut;
        double distanceSquared = (x * x) + (y * y) + (z * z);
        float maxScale = marker.isLarge() ? 4.0f : 2.0f;
        float scale = (float) (Math.max(0.5d, Math.min(maxScale, distanceSquared / 100.0d)) * ((double) transition));
        float maxOffsetY = marker.isLarge() ? 10.0f : 2.0f;
        float offsetY = (float) ((0.5d + Math.max(0.0d, Math.min(maxOffsetY, distanceSquared / 100.0d))) * ((double) transition));
        float lineHeightOffset = marker.isLarge() ? scale * 1.5f : scale / 2.0f;
        float lineHeight = offsetY + (scale / 2.0f) + lineHeightOffset;
        float cameraYaw = camera.getYaw();
        float cameraPitch = camera.getPitch();
        CompletableResourceLocation ownerTexture = this.textureService.getTexture(marker.getOwner(), MojangTextureType.SKIN);
        return new MarkerSnapshot(x, y, z, lightCoords, marker.getOwner(), ownerTexture, marker.isLarge(), transition, scale, lineHeight, cameraYaw, cameraPitch, marker.getARGB());
    }

    @Override // net.labymod.api.client.world.object.submit.WorldObjectSubmitter
    public void submit(@NotNull Stack stack, @NotNull SubmissionCollector collector, @NotNull MarkerSnapshot snapshot) {
        stack.push();
        stack.translate(snapshot.x(), snapshot.y(), snapshot.z());
        stack.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        submitCircle(stack, collector, snapshot);
        submitLine(stack, collector, snapshot);
        submitHead(stack, collector, snapshot);
        stack.pop();
    }

    private void submitCircle(Stack stack, SubmissionCollector collector, MarkerSnapshot snapshot) {
        stack.push();
        stack.rotate(90.0f, -1.0f, 0.0f, 0.0f);
        stack.scale(snapshot.transition(), snapshot.transition(), 1.0f);
        collector.submitIcon(stack, Textures.SpriteMarker.CIRCLE, IconSubmission.DisplayMode.NORMAL, -0.5f, -0.5f, 1.0f, 1.0f, snapshot.argb());
        stack.pop();
    }

    private void submitLine(Stack stack, SubmissionCollector collector, MarkerSnapshot snapshot) {
        stack.push();
        stack.rotate(snapshot.cameraYaw(), 0.0f, 1.0f, 0.0f);
        collector.submitIcon(stack, Textures.SpriteMarker.LINE, IconSubmission.DisplayMode.NORMAL, (-snapshot.lineWidth()) / 2.0f, -snapshot.lineHeight(), snapshot.lineWidth(), snapshot.lineHeight(), snapshot.argb());
        stack.pop();
    }

    private void submitHead(Stack stack, SubmissionCollector collector, MarkerSnapshot snapshot) {
        stack.push();
        stack.rotate(snapshot.cameraYaw(), 0.0f, 1.0f, 0.0f);
        stack.translate((-snapshot.faceScale()) / 2.0f, -snapshot.lineHeight(), 0.0f);
        stack.rotate(-snapshot.cameraPitch(), 1.0f, 0.0f, 0.0f);
        stack.translate(0.0f, (-snapshot.faceScale()) / 2.0f, 0.0f);
        float faceScale = snapshot.faceScale();
        float facePadding = snapshot.facePadding();
        collector.submitIcon(stack, this.backgroundIcon, IconSubmission.DisplayMode.NORMAL, -facePadding, -facePadding, faceScale + (facePadding * 2.0f), faceScale + (facePadding * 2.0f), OUTLINE_COLOR);
        stack.translate(0.0f, 0.0f, -0.01f);
        collector.submitIcon(stack, this.backgroundIcon, IconSubmission.DisplayMode.NORMAL, 0.0f, 0.0f, faceScale, faceScale, -1);
        stack.translate(0.0f, 0.0f, -0.01f);
        ResourceLocation skinTexture = snapshot.ownerTexture();
        if (skinTexture != null) {
            Icon faceIcon = createSkinIcon(skinTexture, 8, 8);
            collector.submitIcon(stack, faceIcon, IconSubmission.DisplayMode.NORMAL, 0.0f, 0.0f, faceScale, faceScale, snapshot.argb());
            stack.translate(0.0f, 0.0f, -0.01f);
            Icon overlayIcon = createSkinIcon(skinTexture, 40, 8);
            collector.submitIcon(stack, overlayIcon, IconSubmission.DisplayMode.NORMAL, 0.0f, 0.0f, faceScale, faceScale, snapshot.argb());
        }
        stack.pop();
    }

    private Icon createSkinIcon(ResourceLocation location, int spriteX, int spriteY) {
        return Icon.sprite(location, spriteX, spriteY, 8, 8, 64, 64).ignoreResolutionExtraction();
    }
}
