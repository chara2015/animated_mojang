package net.labymod.core.loader.vanilla.launchwrapper.transformer.patch.lwjgl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ListIterator;
import net.labymod.api.volt.asm.util.ASMHelper;
import net.labymod.core.loader.isolated.util.IsolatedClassLoader;
import net.labymod.core.loader.isolated.util.IsolatedClassLoaders;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/transformer/patch/lwjgl/GLCapabilitiesExtensionsTransformer.class */
public class GLCapabilitiesExtensionsTransformer implements IClassTransformer {
    private static final String NAME = "org.lwjgl.opengl.GLCapabilities";
    private static final String[] MISSING_EXTENSIONS = {"GL_EXT_multi_draw_arrays", "GL_EXT_paletted_texture", "GL_EXT_rescale_normal", "GL_EXT_texture_3d", "GL_EXT_texture_lod_bias", "GL_EXT_vertex_shader", "GL_EXT_vertex_weighting"};

    public byte[] transform(String name, String transformedName, byte... classData) {
        if (name.equals(NAME) && classData == null) {
            classData = getClassBytes(name);
        }
        if (!name.equals(NAME) || classData == null) {
            return classData;
        }
        return ASMHelper.transformClassData(classData, this::patch);
    }

    private byte[] getClassBytes(String name) {
        IsolatedClassLoader lwjglClassLoader = IsolatedClassLoaders.LWJGL_CLASS_LOADER;
        URL resource = lwjglClassLoader.findResource(name.replace(".", "/").concat(".class"));
        if (resource == null) {
            throw new RuntimeException();
        }
        try {
            InputStream stream = resource.openStream();
            try {
                byte[] allBytes = stream.readAllBytes();
                if (stream != null) {
                    stream.close();
                }
                return allBytes;
            } finally {
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void patch(ClassNode classNode) {
        for (String missingExtension : MISSING_EXTENSIONS) {
            classNode.fields.add(createExtensionField(missingExtension));
        }
        for (MethodNode method : classNode.methods) {
            if (method.name.equals("<init>")) {
                AbstractInsnNode lastReturnInstruction = null;
                ListIterator it = method.instructions.iterator();
                while (it.hasNext()) {
                    AbstractInsnNode instruction = (AbstractInsnNode) it.next();
                    if (instruction.getOpcode() == 177) {
                        lastReturnInstruction = instruction;
                    }
                }
                if (lastReturnInstruction != null) {
                }
                return;
            }
        }
    }

    private FieldNode createExtensionField(String name) {
        return new FieldNode(17, name, "Z", (String) null, false);
    }
}
