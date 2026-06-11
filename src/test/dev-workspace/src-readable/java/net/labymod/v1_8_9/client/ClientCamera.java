package net.labymod.v1_8_9.client;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.options.Perspective;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.core.client.world.ExtendedMinecraftCamera;
import net.labymod.laby3d.api.math.Axis;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/ClientCamera.class */
public final class ClientCamera implements ExtendedMinecraftCamera {
    public static final ClientCamera INSTANCE = new ClientCamera();
    private final Quaternionf rotation = new Quaternionf();
    private final DoubleVector3 position = new DoubleVector3();
    private final DoubleVector3 renderPosition = new DoubleVector3();
    private final DoubleVector3 fromD = new DoubleVector3();
    private final DoubleVector3 toD = new DoubleVector3();
    private final DoubleVector3 lookVectorD = new DoubleVector3();
    private final DoubleVector3 hitVectorD = new DoubleVector3();

    private ClientCamera() {
    }

    @Override // net.labymod.api.client.world.MinecraftCamera
    @NotNull
    public DoubleVector3 position() {
        pk renderEntity = ave.A().ac();
        if (renderEntity == null) {
            this.position.set(0.0d, 0.0d, 0.0d);
            return this.position;
        }
        aui eyesVec = renderEntity.e(1.0f);
        this.position.set(eyesVec.a, eyesVec.b, eyesVec.c);
        aui lookVec = renderEntity.ap();
        this.lookVectorD.set((float) lookVec.a, (float) lookVec.b, (float) lookVec.c);
        Perspective perspective = Laby.labyAPI().minecraft().options().perspective();
        if (perspective == Perspective.FIRST_PERSON) {
            return this.position;
        }
        double distance = 4.0d;
        boolean frontView = perspective == Perspective.THIRD_PERSON_FRONT;
        double maxX = this.position.getX() - ((this.lookVectorD.getX() * 4.0d) * ((double) (frontView ? -1 : 1)));
        double maxY = this.position.getY() - ((this.lookVectorD.getY() * 4.0d) * ((double) (frontView ? -1 : 1)));
        double maxZ = this.position.getZ() - ((this.lookVectorD.getZ() * 4.0d) * ((double) (frontView ? -1 : 1)));
        for (int i = 0; i < 8; i++) {
            float offsetX = (((i & 1) * 2) - 1) * 0.1f;
            float offsetY = ((((i >> 1) & 1) * 2) - 1) * 0.1f;
            float offsetZ = ((((i >> 2) & 1) * 2) - 1) * 0.1f;
            this.fromD.set(this.position);
            this.toD.set(maxX, maxY, maxZ);
            this.fromD.add(offsetX, offsetY, offsetZ);
            this.toD.add(offsetX, offsetY, offsetZ);
            auh target = ave.A().f.a(new aui(this.fromD.getX(), this.fromD.getY(), this.fromD.getZ()), new aui(this.toD.getX(), this.toD.getY(), this.toD.getZ()));
            if (target != null) {
                this.hitVectorD.set(target.c.a, target.c.b, target.c.c);
                double distanceToCollision = this.hitVectorD.distance(this.position);
                if (distanceToCollision < distance) {
                    distance = distanceToCollision;
                }
            }
        }
        this.position.set(this.position.getX() - ((this.lookVectorD.getX() * distance) * ((double) (frontView ? -1 : 1))), this.position.getY() - ((this.lookVectorD.getY() * distance) * ((double) (frontView ? -1 : 1))), this.position.getZ() - ((this.lookVectorD.getZ() * distance) * ((double) (frontView ? -1 : 1))));
        return this.position;
    }

    @Override // net.labymod.api.client.world.MinecraftCamera
    @NotNull
    public DoubleVector3 renderPosition() {
        pk renderEntity = ave.A().ac();
        if (renderEntity == null) {
            this.renderPosition.set(0.0d, 0.0d, 0.0d);
            return this.renderPosition;
        }
        float partialTicks = Laby.labyAPI().minecraft().getPartialTicks();
        this.renderPosition.set(MathHelper.lerp(renderEntity.s, renderEntity.p, partialTicks), MathHelper.lerp(renderEntity.t, renderEntity.q, partialTicks), MathHelper.lerp(renderEntity.u, renderEntity.r, partialTicks));
        return this.renderPosition;
    }

    @Override // net.labymod.api.client.world.MinecraftCamera
    @NotNull
    public Quaternionf cameraRotation() {
        biu renderManager = ave.A().af();
        Perspective perspective = Laby.labyAPI().minecraft().options().perspective();
        float modifier = perspective == Perspective.THIRD_PERSON_FRONT ? -1.0f : 1.0f;
        this.rotation.identity();
        this.rotation.mul(Axis.YP.rotationDegrees(-renderManager.e));
        this.rotation.mul(Axis.XP.rotationDegrees(renderManager.f * modifier));
        return this.rotation;
    }

    @Override // net.labymod.core.client.world.ExtendedMinecraftCamera
    public float getEyeHeight(float partialTicks) {
        Entity entity = Laby.labyAPI().minecraft().getCameraEntity();
        if (entity == null) {
            return 0.0f;
        }
        return entity.getEyeHeight();
    }

    @Override // net.labymod.api.client.world.MinecraftCamera
    public float getPitch() {
        biu renderManager = ave.A().af();
        Perspective perspective = Laby.labyAPI().minecraft().options().perspective();
        float modifier = perspective == Perspective.THIRD_PERSON_FRONT ? -1.0f : 1.0f;
        return (renderManager.f * modifier) % 360.0f;
    }

    @Override // net.labymod.api.client.world.MinecraftCamera
    public float getYaw() {
        biu renderManager = ave.A().af();
        return renderManager.e % 360.0f;
    }
}
