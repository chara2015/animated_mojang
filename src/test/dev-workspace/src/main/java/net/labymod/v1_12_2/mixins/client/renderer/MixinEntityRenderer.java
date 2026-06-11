package net.labymod.v1_12_2.mixins.client.renderer;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.entity.player.ClientPlayerTurnEvent;
import net.labymod.api.event.client.render.RenderHandEvent;
import net.labymod.api.event.client.render.camera.CameraRotationEvent;
import net.labymod.api.event.client.render.camera.CameraSetupEvent;
import net.labymod.api.event.client.render.overlay.IngameOverlayRenderEvent;
import net.labymod.api.event.client.render.world.RenderBlockSelectionBoxEvent;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.util.Color;
import net.labymod.core.event.client.render.RenderHandEventCaller;
import net.labymod.core.event.client.render.camera.CameraEyeHeightEvent;
import net.labymod.core.event.client.render.world.RenderWorldEventCaller;
import net.labymod.core.main.LabyMod;
import net.labymod.v1_12_2.client.render.matrix.VersionedStackProvider;
import net.labymod.v1_12_2.client.renderer.EntityRendererAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/renderer/MixinEntityRenderer.class */
@Mixin({buq.class})
public abstract class MixinEntityRenderer implements EntityRendererAccessor {

    @Shadow
    private bib h;
    private float labyMod$cameraYaw;
    private float labyMod$cameraPitch;

    @Inject(method = {"orientCamera(F)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getRenderViewEntity()Lnet/minecraft/entity/Entity;", shift = At.Shift.BEFORE)})
    private void labyMod$preCameraSetup(float partialTicks, CallbackInfo ci) {
        labyMod$fireCameraSetupEvent(Phase.PRE);
    }

    @Inject(method = {"orientCamera(F)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V", shift = At.Shift.BEFORE, ordinal = 4)})
    private void labyMod$postCameraSetup(float partialTicks, CallbackInfo ci) {
        labyMod$fireCameraSetupEvent(Phase.POST);
    }

    private void labyMod$fireCameraSetupEvent(Phase phase) {
        Laby.fireEvent(new CameraSetupEvent(VersionedStackProvider.DEFAULT_STACK, phase));
    }

    @WrapOperation(method = {"renderWorldPass"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/renderer/EntityRenderer;renderHand:Z")})
    private boolean labyMod$firePreRenderHandEvent(buq instance, Operation<Boolean> original) {
        RenderHandEvent event = RenderHandEventCaller.call(VersionedStackProvider.DEFAULT_STACK, Phase.PRE);
        if (!event.isCancelled()) {
            return ((Boolean) original.call(new Object[]{instance})).booleanValue();
        }
        return false;
    }

    @Inject(method = {"renderWorldPass"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;renderHand(FI)V", shift = At.Shift.AFTER)})
    private void labyMod$firePostRenderHandEvent(int p_renderWorldPass_1_, float p_renderWorldPass_2_, long p_renderWorldPass_3_, CallbackInfo ci) {
        RenderHandEventCaller.call(VersionedStackProvider.DEFAULT_STACK, Phase.POST);
    }

    @Inject(method = {"orientCamera"}, at = {@At("TAIL")})
    private void labyMod$postOrientCamera(float partialTicks, CallbackInfo ci) {
        vg entity = this.h.aa();
        if (entity == null) {
            return;
        }
        float eyeHeight = entity.by();
        CameraEyeHeightEvent event = (CameraEyeHeightEvent) Laby.fireEvent(new CameraEyeHeightEvent(partialTicks, eyeHeight));
        bus.c(0.0f, eyeHeight - event.getEyeHeight(), 0.0f);
    }

    @Redirect(method = {"orientCamera"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getRenderViewEntity()Lnet/minecraft/entity/Entity;"))
    private vg labyMod$preOrientCamera(bib mc) {
        vg entity = mc.aa();
        CameraRotationEvent event = (CameraRotationEvent) Laby.fireEvent(new CameraRotationEvent(entity.v, entity.w));
        this.labyMod$cameraYaw = event.getYaw();
        this.labyMod$cameraPitch = event.getPitch();
        return entity;
    }

    @Redirect(method = {"orientCamera"}, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;rotationYaw:F"))
    private float labyMod$rotationYaw(vg entity) {
        return this.labyMod$cameraYaw;
    }

    @Redirect(method = {"orientCamera"}, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;rotationPitch:F"))
    private float labyMod$rotationPitch(vg entity) {
        return this.labyMod$cameraPitch;
    }

    @Redirect(method = {"orientCamera"}, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;prevRotationYaw:F"))
    private float labyMod$prevRotationYaw(vg entity) {
        return this.labyMod$cameraYaw;
    }

    @Redirect(method = {"orientCamera"}, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;prevRotationPitch:F"))
    private float labyMod$prevRotationPitch(vg entity) {
        return this.labyMod$cameraPitch;
    }

    @Redirect(method = {"renderWorldPass"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;drawSelectionBox(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/math/RayTraceResult;IF)V"))
    public void labyMod$renderCustomOutline(buy instance, aed player, bhc result, int i, float partialTicks) {
        if (i != 0 || result.a != a.b) {
            return;
        }
        Laby3D laby3D = Laby.references().laby3D();
        laby3D.storeStates();
        amu world = player.l;
        et blockPos = result.a();
        RenderBlockSelectionBoxEvent event = new RenderBlockSelectionBoxEvent(blockPos.p(), blockPos.q(), blockPos.r());
        Laby.fireEvent(event);
        if (event.isCancelled()) {
            laby3D.restoreStates();
            return;
        }
        bus.m();
        bus.z();
        bus.a(false);
        awt blockState = world.o(blockPos);
        if (blockState.a() != bcz.a && world.al().a(blockPos)) {
            double offsetX = player.M + ((player.p - player.M) * ((double) partialTicks));
            double offsetY = player.N + ((player.q - player.N) * ((double) partialTicks));
            double offsetZ = player.O + ((player.r - player.O) * ((double) partialTicks));
            bhb boundingBox = blockState.c(world, blockPos).d(-offsetX, -offsetY, -offsetZ);
            if (event.getLineColor() != null) {
                labyMod$drawLines(boundingBox.g(0.0020000000949949026d), event.getLineColor());
            }
            if (event.getOverlayColor() != null) {
                labyMod$drawOverlay(boundingBox.g(0.0010000000474974513d), event.getOverlayColor());
            }
        }
        bus.a(true);
        bus.y();
        bus.l();
        laby3D.restoreStates();
    }

    private void labyMod$drawLines(bhb boundingBox, Color color) {
        bus.a(r.l, l.j, r.e, l.n);
        bus.d(2.0f);
        buy.a(boundingBox, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }

    private void labyMod$drawOverlay(bhb box, Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        int alpha = color.getAlpha();
        bve tessellator = bve.a();
        buk worldRenderer = tessellator.c();
        double minX = box.a;
        double minY = box.b;
        double minZ = box.c;
        double maxX = box.d;
        double maxY = box.e;
        double maxZ = box.f;
        worldRenderer.a(7, cdy.f);
        worldRenderer.b(minX, minY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, maxY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, minY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, maxY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, minY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, maxY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, minY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, maxY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, maxY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, minY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, maxY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, minY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, maxY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, minY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, maxY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, minY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, maxY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, maxY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, maxY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, maxY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, maxY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, maxY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, maxY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, maxY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, minY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, minY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, minY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, minY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, minY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, minY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, minY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, minY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, minY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, maxY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, minY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, maxY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, minY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, maxY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, minY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, maxY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, maxY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, minY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, maxY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, minY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, maxY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, minY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, maxY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, minY, maxZ).b(red, green, blue, alpha).d();
        tessellator.b();
    }

    @Redirect(method = {"updateCameraAndRender"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;turn(FF)V"))
    private void redirectPlayerRotation(bud player, float x, float y) {
        ClientPlayerTurnEvent event = (ClientPlayerTurnEvent) Laby.fireEvent(new ClientPlayerTurnEvent((ClientPlayer) player, x / 8.0f, (-y) / 8.0f));
        if (event.isCancelled()) {
            return;
        }
        player.c(((float) event.getX()) * 8.0f, ((float) (-event.getY())) * 8.0f);
    }

    @Redirect(method = {"updateCameraAndRender"}, at = @At(value = "FIELD", opcode = 180, target = "Lnet/minecraft/client/settings/GameSettings;hideGUI:Z"))
    private boolean labyMod$fireHiddenOverlayRenderEvent(bid settings) {
        boolean hideGui = settings.av;
        if (hideGui && this.h.m == null) {
            Stack stack = VersionedStackProvider.DEFAULT_STACK;
            Laby.references().renderEnvironmentContext().screenContext().runInContext(stack, Laby.labyAPI().minecraft().mouse(), Laby.labyAPI().minecraft().getTickDelta(), context -> {
                Laby.fireEvent(new IngameOverlayRenderEvent(context, Phase.PRE, true));
                Laby.fireEvent(new IngameOverlayRenderEvent(context, Phase.POST, true));
            });
        }
        return hideGui;
    }

    @Inject(method = {"renderWorldPass"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;renderBlockLayer(Lnet/minecraft/util/BlockRenderLayer;DILnet/minecraft/entity/Entity;)I", ordinal = 3, shift = At.Shift.BEFORE)})
    private void renderWorldEvent(int index, float partialTicks, long time, CallbackInfo ci) {
        Stack stack = VersionedStackProvider.DEFAULT_STACK;
        RenderWorldEventCaller.callPost(stack, partialTicks);
    }

    @Override // net.labymod.v1_12_2.client.renderer.EntityRendererAccessor
    public float getCameraYaw() {
        return this.labyMod$cameraYaw;
    }

    @Override // net.labymod.v1_12_2.client.renderer.EntityRendererAccessor
    public void setCameraYaw(float yaw) {
        this.labyMod$cameraYaw = yaw;
    }

    @Override // net.labymod.v1_12_2.client.renderer.EntityRendererAccessor
    public float getCameraPitch() {
        return this.labyMod$cameraPitch;
    }

    @Override // net.labymod.v1_12_2.client.renderer.EntityRendererAccessor
    public void setCameraPitch(float pitch) {
        this.labyMod$cameraPitch = pitch;
    }

    @WrapWithCondition(method = {"setupCameraTransform"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;applyBobbing(F)V")})
    private boolean labyMod$noViewBobbing(buq instance, float partialTicks) {
        return !LabyMod.getInstance().config().ingame().noViewBobbing().get().booleanValue();
    }
}
