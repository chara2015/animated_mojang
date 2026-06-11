package net.labymod.api.volt.asm.primitive;

import java.util.Objects;
import org.objectweb.asm.Type;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/volt/asm/primitive/Primitive.class */
public class Primitive {
    private final String bytecodeName;
    private final Type type;
    private final String methodName;
    private final String methodDescriptor;
    private final int returnOpcode;
    private final int loadOpcode;
    private final int arrayLoadOpcode;
    private final int storeOpcode;
    private final int arrayStoreOpcode;

    @Deprecated(forRemoval = true, since = "4.1.3")
    public Primitive(String bytecodeName, String type, String methodName, String methodDescriptor, int returnOpcode) {
        this(bytecodeName, Type.getType("L" + type + ";"), methodName, methodDescriptor, returnOpcode, 0, 0, 0, 0);
    }

    public Primitive(String bytecodeName, Type type, String methodName, String methodDescriptor, int returnOpcode, int loadOpcode, int arrayLoadOpcode, int storeOpcode, int arrayStoreOpcode) {
        this.bytecodeName = bytecodeName;
        this.type = type;
        this.methodName = methodName;
        this.methodDescriptor = methodDescriptor;
        this.returnOpcode = returnOpcode;
        this.loadOpcode = loadOpcode;
        this.arrayLoadOpcode = arrayLoadOpcode;
        this.storeOpcode = storeOpcode;
        this.arrayStoreOpcode = arrayStoreOpcode;
    }

    public String getBytecodeName() {
        return this.bytecodeName;
    }

    @Deprecated(forRemoval = true, since = "4.1.3")
    public String getType() {
        return getInternalName();
    }

    public String getInternalName() {
        return this.type.getInternalName();
    }

    public Type getASMType() {
        return this.type;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public String getMethodDescriptor() {
        return this.methodDescriptor;
    }

    @Deprecated(forRemoval = true, since = "4.1.3")
    public int getReturnType() {
        return getReturnOpcode();
    }

    public int getReturnOpcode() {
        return this.returnOpcode;
    }

    public int getLoadOpcode(boolean array) {
        return array ? this.arrayLoadOpcode : this.loadOpcode;
    }

    public int getStoreOpcode(boolean array) {
        return array ? this.arrayStoreOpcode : this.storeOpcode;
    }

    public boolean isPrimitive(String name) {
        return getInternalName().equals(name) || getBytecodeName().equals(name);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Primitive primitive = (Primitive) object;
        return this.returnOpcode == primitive.returnOpcode && this.loadOpcode == primitive.loadOpcode && this.arrayLoadOpcode == primitive.arrayLoadOpcode && this.storeOpcode == primitive.storeOpcode && this.arrayStoreOpcode == primitive.arrayStoreOpcode && Objects.equals(this.bytecodeName, primitive.bytecodeName) && Objects.equals(this.type, primitive.type) && Objects.equals(this.methodName, primitive.methodName) && Objects.equals(this.methodDescriptor, primitive.methodDescriptor);
    }

    public int hashCode() {
        int result = this.bytecodeName != null ? this.bytecodeName.hashCode() : 0;
        return (31 * ((31 * ((31 * ((31 * ((31 * ((31 * ((31 * ((31 * result) + (this.type != null ? this.type.hashCode() : 0))) + (this.methodName != null ? this.methodName.hashCode() : 0))) + (this.methodDescriptor != null ? this.methodDescriptor.hashCode() : 0))) + this.returnOpcode)) + this.loadOpcode)) + this.arrayLoadOpcode)) + this.storeOpcode)) + this.arrayStoreOpcode;
    }
}
