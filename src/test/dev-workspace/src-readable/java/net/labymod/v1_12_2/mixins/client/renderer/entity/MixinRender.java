package net.labymod.v1_12_2.mixins.client.renderer.entity;

import java.util.Objects;
import javax.annotation.Nullable;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.entity.player.tag.event.NameTagBackgroundRenderEvent;
import net.labymod.api.client.gfx.GlConst;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.PlayerNameTagRenderEvent;
import net.labymod.api.event.client.render.entity.EntityRenderEvent;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.util.RenderUtil;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.client.render.state.entity.EntitySnapshotCreator;
import net.labymod.core.client.render.state.entity.mutable.AbstractLiveEntitySnapshot;
import net.labymod.v1_12_2.client.render.matrix.VersionedStackProvider;
import net.labymod.v1_12_2.client.renderer.EntityRendererAccessor;
import net.labymod.v1_12_2.client.util.MinecraftUtil;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/renderer/entity/MixinRender.class */
@Mixin({bzg.class})
public abstract class MixinRender {

    @Shadow
    @Final
    protected bzf b;

    @Shadow
    public abstract bzf e();

    @Shadow
    public abstract bip d();

    @Inject(method = {"doRender"}, at = {@At("HEAD")})
    private void labyMod$fireEntityRenderPre(vg p_doRender_1_, double p_doRender_2_, double p_doRender_4_, double p_doRender_6_, float p_doRender_8_, float p_doRender_9_, CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderEvent((Entity) p_doRender_1_, Phase.PRE));
    }

    @Inject(method = {"doRender"}, at = {@At("HEAD")})
    private void labyMod$fireEntityRenderPost(vg p_doRender_1_, double p_doRender_2_, double p_doRender_4_, double p_doRender_6_, float p_doRender_8_, float p_doRender_9_, CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderEvent((Entity) p_doRender_1_, Phase.POST));
    }

    @ModifyConstant(method = {"renderLivingLabel"}, constant = {@Constant(intValue = 8)})
    private int labyMod$modifyNameTagBackgroundHeight(int nameTagHeight) {
        return MathHelper.ceil(Laby.labyAPI().renderPipeline().textRenderer().getLineHeight());
    }

    @Unique
    @Nullable
    private static String labyMod$firePlayerNameTagRenderEvent(Player entity, String name, TagType type) {
        ComponentMapper componentMapper = Laby.labyAPI().minecraft().componentMapper();
        Component preEventNameTag = componentMapper.fromMinecraftComponent(new ho(name));
        PlayerNameTagRenderEvent event = new PlayerNameTagRenderEvent(PlayerNameTagRenderEvent.Context.PLAYER_RENDER, entity.networkPlayerInfo(), preEventNameTag, type);
        Laby.fireEvent(event);
        if (event.isCancelled()) {
            RenderUtil.setNameTagType(TagType.INVALID);
            return null;
        }
        Component postEventNameTag = event.nameTag();
        if (!Objects.equals(postEventNameTag, preEventNameTag)) {
            hh customNameTag = (hh) componentMapper.toMinecraftComponent(postEventNameTag.append(PlayerNameTagRenderEvent.EDITED_COMPONENT));
            name = customNameTag.d();
        }
        return name;
    }

    @Overwrite
    protected void a(vg entity, String name, double x, double y, double z, int distance) {
        double distanceToEntity = entity.h(this.b.c);
        if (distanceToEntity <= distance * distance) {
            TagType type = RenderUtil.getNameTagType();
            if (type == TagType.MAIN_TAG && (entity instanceof Player)) {
                name = labyMod$firePlayerNameTagRenderEvent((Player) entity, name, type);
            }
            if (name == null) {
                return;
            }
            bip font = d();
            float scale = 0.016666668f * 1.6f;
            bus.G();
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            bus.b(x, y + ((double) entity.H) + 0.5d, z);
            EntityRendererAccessor accessor = bib.z().o;
            boolean thirdPersonFront = this.b.g.aw == 2;
            float viewY = thirdPersonFront ? accessor.getCameraYaw() + 180.0f : accessor.getCameraYaw();
            float viewX = thirdPersonFront ? -accessor.getCameraPitch() : accessor.getCameraPitch();
            bus.b(-viewY, 0.0f, 1.0f, 0.0f);
            bus.b(viewX, 1.0f, 0.0f, 0.0f);
            bus.b(-scale, -scale, scale);
            boolean sneaking = entity.aU();
            if (sneaking) {
                bus.c(0.0f, 9.374999f, 0.0f);
            }
            bus.g();
            bus.a(false);
            if (!sneaking) {
                bus.j();
            }
            int nameWidth = font.a(name);
            Stack defaultStack = VersionedStackProvider.DEFAULT_STACK;
            Laby.references().renderEnvironmentContext().setPackedLight(MinecraftUtil.getPackedLight(entity));
            LabyAPI labyAPI = Laby.labyAPI();
            if (!(entity instanceof abz)) {
                Laby3D laby3D = Laby.references().laby3D();
                laby3D.storeStates();
                AbstractLiveEntitySnapshot snapshot = ((EntitySnapshotCreator) entity).createSnapshot(Laby.labyAPI().minecraft().getPartialTicks());
                labyAPI.tagRegistry().render(defaultStack, snapshot, nameWidth + 2.0f, type);
                laby3D.restoreStates();
            }
            bus.m();
            bus.a(GlConst.GL_SRC_ALPHA, GlConst.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
            bve tessellator = bve.a();
            buk bufferBuilder = tessellator.c();
            int nameHeight = 0;
            if (name.equals("deadmau5")) {
                nameHeight = -10;
            }
            bus.z();
            NameTagBackgroundRenderEvent bgEvent = (NameTagBackgroundRenderEvent) Laby.fireEvent(NameTagBackgroundRenderEvent.singleton());
            int nameWidth2 = (int) (nameWidth / 2.0f);
            if (!bgEvent.isCancelled()) {
                bufferBuilder.a(7, cdy.f);
                int backgroundColor = bgEvent.getColor();
                ColorFormat colorFormat = ColorFormat.ARGB32;
                float red = colorFormat.normalizedRed(backgroundColor);
                float green = colorFormat.normalizedGreen(backgroundColor);
                float blue = colorFormat.normalizedBlue(backgroundColor);
                bufferBuilder.b((-nameWidth2) - 1, (-1) + nameHeight, 0.006d).a(red, green, blue, 0.25f).d();
                bufferBuilder.b((-nameWidth2) - 1, 8 + nameHeight, 0.006d).a(red, green, blue, 0.25f).d();
                bufferBuilder.b(nameWidth2 + 1, 8 + nameHeight, 0.006d).a(red, green, blue, 0.25f).d();
                bufferBuilder.b(nameWidth2 + 1, (-1) + nameHeight, 0.006d).a(red, green, blue, 0.25f).d();
                tessellator.b();
            }
            bus.y();
            if (!sneaking) {
                font.a(name, (-font.a(name)) / 2, nameHeight, 553648127);
                bus.k();
            }
            bus.a(true);
            font.a(name, (-font.a(name)) / 2, nameHeight, sneaking ? 553648127 : -1);
            bus.f();
            bus.l();
            bus.c(1.0f, 1.0f, 1.0f, 1.0f);
            bus.H();
        }
    }
}
