package net.minecraft.client.renderer;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.buffers.Std140SizeCalculator;
import com.mojang.blaze3d.systems.RenderSystem;
import java.nio.ByteBuffer;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.system.MemoryStack;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/GlobalSettingsUniform.class */
public class GlobalSettingsUniform implements AutoCloseable {
    public static final int UBO_SIZE = new Std140SizeCalculator().putIVec3().putVec3().putVec2().putFloat().putFloat().putInt().putInt().get();
    private final GpuBuffer buffer = RenderSystem.getDevice().createBuffer(() -> {
        return "Global Settings UBO";
    }, 136, UBO_SIZE);

    public void update(int $$0, int $$1, double $$2, long $$3, DeltaTracker $$4, int $$5, Camera $$6, boolean $$7) {
        Vec3 $$8 = $$6.position();
        MemoryStack $$9 = MemoryStack.stackPush();
        try {
            int $$10 = Mth.floor($$8.x);
            int $$11 = Mth.floor($$8.y);
            int $$12 = Mth.floor($$8.z);
            ByteBuffer $$13 = Std140Builder.onStack($$9, UBO_SIZE).putIVec3($$10, $$11, $$12).putVec3((float) (((double) $$10) - $$8.x), (float) (((double) $$11) - $$8.y), (float) (((double) $$12) - $$8.z)).putVec2($$0, $$1).putFloat((float) $$2).putFloat((($$3 % 24000) + $$4.getGameTimeDeltaPartialTick(false)) / 24000.0f).putInt($$5).putInt($$7 ? 1 : 0).get();
            RenderSystem.getDevice().createCommandEncoder().writeToBuffer(this.buffer.slice(), $$13);
            if ($$9 != null) {
                $$9.close();
            }
            RenderSystem.setGlobalSettingsUniform(this.buffer);
        } catch (Throwable th) {
            if ($$9 != null) {
                try {
                    $$9.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.buffer.close();
    }
}
