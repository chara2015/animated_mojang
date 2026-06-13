package net.labymod.api.laby3d.shaders.block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.labymod.laby3d.api.RenderDevice;
import net.labymod.laby3d.api.RenderDeviceException;
import net.labymod.laby3d.api.buffers.layout.LayoutDefinition;
import net.labymod.laby3d.api.buffers.layout.LayoutMember;
import net.labymod.laby3d.api.shaders.UniformType;
import net.labymod.laby3d.api.shaders.block.AbstractUniformBlock;
import net.labymod.laby3d.api.shaders.block.property.FloatUniformProperty;
import net.labymod.laby3d.api.shaders.block.property.IntUniformProperty;
import net.labymod.laby3d.api.shaders.block.property.Matrix2fUniformProperty;
import net.labymod.laby3d.api.shaders.block.property.Matrix3fUniformProperty;
import net.labymod.laby3d.api.shaders.block.property.Matrix4fUniformProperty;
import net.labymod.laby3d.api.shaders.block.property.UniformProperty;
import net.labymod.laby3d.api.shaders.block.property.Vector2fUniformProperty;
import net.labymod.laby3d.api.shaders.block.property.Vector2iUniformProperty;
import net.labymod.laby3d.api.shaders.block.property.Vector3fUniformProperty;
import net.labymod.laby3d.api.shaders.block.property.Vector3iUniformProperty;
import net.labymod.laby3d.api.shaders.block.property.Vector4fUniformProperty;
import net.labymod.laby3d.api.shaders.block.property.Vector4iUniformProperty;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/shaders/block/CustomPostProcessorUniformBlock.class */
public class CustomPostProcessorUniformBlock extends AbstractUniformBlock {
    private final List<UniformProperty<?>> properties;
    private final Map<String, UniformProperty<?>> nameToPropertyMap;

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: net.labymod.laby3d.api.RenderDeviceException */
    public CustomPostProcessorUniformBlock(RenderDevice device, String name, LayoutDefinition layout) throws RenderDeviceException, MatchException {
        super(device, name, layout);
        this.properties = new ArrayList();
        this.nameToPropertyMap = new HashMap();
        for (LayoutMember member : layout.members()) {
            UniformProperty<?> property = createProperty(member);
            this.properties.add(property);
            this.nameToPropertyMap.put(member.name(), property);
        }
    }

    @NotNull
    protected List<UniformProperty<?>> buildProperties() {
        return this.properties;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: net.labymod.laby3d.api.RenderDeviceException */
    /* JADX WARN: Multi-variable type inference failed */
    public <T> UniformProperty<T> getProperty(String name) throws RenderDeviceException {
        UniformProperty<?> property = this.nameToPropertyMap.get(name);
        if (property == null) {
            throw new RenderDeviceException("Unable to find a property with the name '" + name + "'.");
        }
        return property;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: net.labymod.laby3d.api.RenderDeviceException */
    private UniformProperty<?> createProperty(LayoutMember member) throws RenderDeviceException, MatchException {
        String name = member.name();
        UniformType uniformType = member.attribute().uniformType();
        if (uniformType == null) {
            throw new RenderDeviceException("Unable to create a uniform property for the member '" + name + "'. Because the uniform type is null.");
        }
        switch (AnonymousClass1.$SwitchMap$net$labymod$laby3d$api$shaders$UniformType[uniformType.ordinal()]) {
            case 1:
                return createProperty(name, FloatUniformProperty::new);
            case 2:
                return createProperty(name, Vector2fUniformProperty::new);
            case 3:
                return createProperty(name, Vector3fUniformProperty::new);
            case 4:
                return createProperty(name, Vector4fUniformProperty::new);
            case 5:
                return createProperty(name, IntUniformProperty::new);
            case 6:
                return createProperty(name, Vector2iUniformProperty::new);
            case 7:
                return createProperty(name, Vector3iUniformProperty::new);
            case 8:
                return createProperty(name, Vector4iUniformProperty::new);
            case 9:
                return createProperty(name, Matrix2fUniformProperty::new);
            case 10:
                return createProperty(name, Matrix3fUniformProperty::new);
            case 11:
                return createProperty(name, Matrix4fUniformProperty::new);
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: renamed from: net.labymod.api.laby3d.shaders.block.CustomPostProcessorUniformBlock$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/shaders/block/CustomPostProcessorUniformBlock$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$labymod$laby3d$api$shaders$UniformType = new int[UniformType.values().length];

        static {
            try {
                $SwitchMap$net$labymod$laby3d$api$shaders$UniformType[UniformType.FLOAT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$shaders$UniformType[UniformType.VEC2.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$shaders$UniformType[UniformType.VEC3.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$shaders$UniformType[UniformType.VEC4.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$shaders$UniformType[UniformType.INT.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$shaders$UniformType[UniformType.IVEC2.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$shaders$UniformType[UniformType.IVEC3.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$shaders$UniformType[UniformType.IVEC4.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$shaders$UniformType[UniformType.MAT2.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$shaders$UniformType[UniformType.MAT3.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$shaders$UniformType[UniformType.MAT4.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
        }
    }
}
