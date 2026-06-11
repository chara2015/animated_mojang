package com.mojang.blaze3d;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/Blaze3D.class */
public class Blaze3D {
    public static void youJustLostTheGame() {
        MemoryUtil.memSet(0L, 0, 1L);
    }

    public static double getTime() {
        return GLFW.glfwGetTime();
    }

    private Blaze3D() {
    }
}
