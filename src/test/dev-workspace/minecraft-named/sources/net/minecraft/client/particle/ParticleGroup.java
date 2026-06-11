package net.minecraft.client.particle;

import com.google.common.collect.EvictingQueue;
import java.util.Iterator;
import java.util.Objects;
import java.util.Queue;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.state.ParticleGroupRenderState;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/particle/ParticleGroup.class */
public abstract class ParticleGroup<P extends Particle> {
    private static final int MAX_PARTICLES = 16384;
    protected final ParticleEngine engine;
    protected final Queue<P> particles = EvictingQueue.create(16384);

    public abstract ParticleGroupRenderState extractRenderState(Frustum frustum, Camera camera, float f);

    public ParticleGroup(ParticleEngine $$0) {
        this.engine = $$0;
    }

    public boolean isEmpty() {
        return this.particles.isEmpty();
    }

    public void tickParticles() {
        if (!this.particles.isEmpty()) {
            Iterator<P> $$0 = this.particles.iterator();
            while ($$0.hasNext()) {
                P $$1 = $$0.next();
                tickParticle($$1);
                if (!$$1.isAlive()) {
                    $$1.getParticleLimit().ifPresent($$02 -> {
                        this.engine.updateCount($$02, -1);
                    });
                    $$0.remove();
                }
            }
        }
    }

    private void tickParticle(Particle $$0) {
        try {
            $$0.tick();
        } catch (Throwable $$1) {
            CrashReport $$2 = CrashReport.forThrowable($$1, "Ticking Particle");
            CrashReportCategory $$3 = $$2.addCategory("Particle being ticked");
            Objects.requireNonNull($$0);
            $$3.setDetail("Particle", $$0::toString);
            ParticleRenderType group = $$0.getGroup();
            Objects.requireNonNull(group);
            $$3.setDetail("Particle Type", group::toString);
            throw new ReportedException($$2);
        }
    }

    public void add(Particle $$0) {
        this.particles.add($$0);
    }

    public int size() {
        return this.particles.size();
    }

    public Queue<P> getAll() {
        return this.particles;
    }
}
