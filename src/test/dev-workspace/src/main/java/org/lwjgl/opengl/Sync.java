package org.lwjgl.opengl;

import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:org/lwjgl/opengl/Sync.class */
class Sync {
    private static final long NANOS_IN_SECOND = 1000000000;
    private static long nextFrame = 0;
    private static boolean initialised = false;
    private static RunningAvg sleepDurations = new RunningAvg(10);
    private static RunningAvg yieldDurations = new RunningAvg(10);

    Sync() {
    }

    /* JADX WARN: Type inference failed for: r0v14, types: [long, org.lwjgl.opengl.Sync$RunningAvg] */
    /* JADX WARN: Type inference failed for: r0v17, types: [long, org.lwjgl.opengl.Sync$RunningAvg] */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public static void sync(int fps) {
        if (fps <= 0) {
            return;
        }
        if (!initialised) {
            initialise();
        }
        try {
            long t0 = getTime();
            while (nextFrame - t0 > sleepDurations.avg()) {
                Thread.sleep(1L);
                ?? r0 = sleepDurations;
                long t1 = getTime();
                r0.add(r0 - t0);
                t0 = t1;
            }
            sleepDurations.dampenForLowResTicker();
            long t02 = getTime();
            while (nextFrame - t02 > yieldDurations.avg()) {
                Thread.yield();
                ?? r02 = yieldDurations;
                long t12 = getTime();
                r02.add(r02 - t02);
                t02 = t12;
            }
        } catch (InterruptedException e) {
        }
        nextFrame = Math.max(nextFrame + (NANOS_IN_SECOND / ((long) fps)), getTime());
    }

    private static void initialise() {
        initialised = true;
        sleepDurations.init(1000000L);
        yieldDurations.init((int) ((-(getTime() - getTime())) * 1.333d));
        nextFrame = getTime();
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Win")) {
            Thread timerAccuracyThread = new Thread(new Runnable() { // from class: org.lwjgl.opengl.Sync.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        Thread.sleep(Long.MAX_VALUE);
                    } catch (Exception e) {
                    }
                }
            });
            timerAccuracyThread.setName("LWJGL Timer");
            timerAccuracyThread.setDaemon(true);
            timerAccuracyThread.start();
        }
    }

    private static long getTime() {
        return TimeUtil.getNanoTime();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:org/lwjgl/opengl/Sync$RunningAvg.class */
    private static class RunningAvg {
        private final long[] slots;
        private int offset = 0;
        private static final long DAMPEN_THRESHOLD = 10000000;
        private static final float DAMPEN_FACTOR = 0.9f;

        public RunningAvg(int slotCount) {
            this.slots = new long[slotCount];
        }

        public void init(long value) {
            while (this.offset < this.slots.length) {
                long[] jArr = this.slots;
                int i = this.offset;
                this.offset = i + 1;
                jArr[i] = value;
            }
        }

        public void add(long value) {
            long[] jArr = this.slots;
            int i = this.offset;
            this.offset = i + 1;
            jArr[i % this.slots.length] = value;
            this.offset %= this.slots.length;
        }

        public long avg() {
            long sum = 0;
            for (int i = 0; i < this.slots.length; i++) {
                sum += this.slots[i];
            }
            return sum / ((long) this.slots.length);
        }

        public void dampenForLowResTicker() {
            if (avg() > DAMPEN_THRESHOLD) {
                for (int i = 0; i < this.slots.length; i++) {
                    this.slots[i] = (long) (r0[r1] * DAMPEN_FACTOR);
                }
            }
        }
    }
}
