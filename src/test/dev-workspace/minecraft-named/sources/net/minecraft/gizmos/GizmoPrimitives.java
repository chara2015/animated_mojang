package net.minecraft.gizmos;

import net.minecraft.gizmos.TextGizmo;
import net.minecraft.world.phys.Vec3;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/gizmos/GizmoPrimitives.class */
public interface GizmoPrimitives {
    void addPoint(Vec3 vec3, int i, float f);

    void addLine(Vec3 vec3, Vec3 vec32, int i, float f);

    void addTriangleFan(Vec3[] vec3Arr, int i);

    void addQuad(Vec3 vec3, Vec3 vec32, Vec3 vec33, Vec3 vec34, int i);

    void addText(Vec3 vec3, String str, TextGizmo.Style style);
}
