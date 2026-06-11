package com.mojang.blaze3d.shaders;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/shaders/UniformType.class */
public enum UniformType {
    UNIFORM_BUFFER("ubo"),
    TEXEL_BUFFER("utb");

    final String name;

    UniformType(String $$0) {
        this.name = $$0;
    }
}
