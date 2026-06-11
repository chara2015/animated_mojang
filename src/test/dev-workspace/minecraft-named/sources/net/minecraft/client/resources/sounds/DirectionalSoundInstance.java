package net.minecraft.client.resources.sounds;

import net.minecraft.client.Camera;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/sounds/DirectionalSoundInstance.class */
public class DirectionalSoundInstance extends AbstractTickableSoundInstance {
    private final Camera camera;
    private final float xAngle;
    private final float yAngle;

    public DirectionalSoundInstance(SoundEvent $$0, SoundSource $$1, RandomSource $$2, Camera $$3, float $$4, float $$5) {
        super($$0, $$1, $$2);
        this.camera = $$3;
        this.xAngle = $$4;
        this.yAngle = $$5;
        setPosition();
    }

    private void setPosition() {
        Vec3 $$0 = Vec3.directionFromRotation(this.xAngle, this.yAngle).scale(10.0d);
        this.x = this.camera.position().x + $$0.x;
        this.y = this.camera.position().y + $$0.y;
        this.z = this.camera.position().z + $$0.z;
        this.attenuation = SoundInstance.Attenuation.NONE;
    }

    @Override // net.minecraft.client.resources.sounds.TickableSoundInstance
    public void tick() {
        setPosition();
    }
}
