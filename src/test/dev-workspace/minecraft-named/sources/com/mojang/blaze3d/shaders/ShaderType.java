package com.mojang.blaze3d.shaders;

import com.mojang.blaze3d.DontObfuscate;
import net.minecraft.client.renderer.ShaderManager;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/shaders/ShaderType.class */
@DontObfuscate
public enum ShaderType {
    VERTEX("vertex", ".vsh"),
    FRAGMENT("fragment", ".fsh");

    private static final ShaderType[] TYPES = values();
    private final String name;
    private final String extension;

    ShaderType(String $$0, String $$1) {
        this.name = $$0;
        this.extension = $$1;
    }

    public static ShaderType byLocation(Identifier $$0) {
        for (ShaderType $$1 : TYPES) {
            if ($$0.getPath().endsWith($$1.extension)) {
                return $$1;
            }
        }
        return null;
    }

    public String getName() {
        return this.name;
    }

    public FileToIdConverter idConverter() {
        return new FileToIdConverter(ShaderManager.SHADER_PATH, this.extension);
    }
}
