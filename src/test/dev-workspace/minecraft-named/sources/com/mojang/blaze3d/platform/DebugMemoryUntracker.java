package com.mojang.blaze3d.platform;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.lwjgl.system.Pointer;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/platform/DebugMemoryUntracker.class */
public class DebugMemoryUntracker {
    private static final MethodHandle UNTRACK = (MethodHandle) GLX.make(() -> {
        try {
            MethodHandles.Lookup $$0 = MethodHandles.lookup();
            Class<?> $$1 = Class.forName("org.lwjgl.system.MemoryManage$DebugAllocator");
            Method $$2 = $$1.getDeclaredMethod("untrack", Long.TYPE);
            $$2.setAccessible(true);
            Field $$3 = Class.forName("org.lwjgl.system.MemoryUtil$LazyInit").getDeclaredField("ALLOCATOR");
            $$3.setAccessible(true);
            Object $$4 = $$3.get(null);
            if ($$1.isInstance($$4)) {
                return $$0.unreflect($$2);
            }
            return null;
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException | NoSuchMethodException $$5) {
            throw new RuntimeException($$5);
        }
    });

    public static void untrack(long $$0) {
        if (UNTRACK == null) {
            return;
        }
        try {
            (void) UNTRACK.invoke($$0);
        } catch (Throwable $$1) {
            throw new RuntimeException($$1);
        }
    }

    public static void untrack(Pointer $$0) {
        untrack($$0.address());
    }
}
