package net.minecraft.server.jsonrpc.api;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.net.URI;
import java.util.List;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/api/SchemaComponent.class */
public final class SchemaComponent<T> extends Record {
    private final String name;
    private final URI ref;
    private final Schema<T> schema;

    public SchemaComponent(String $$0, URI $$1, Schema<T> $$2) {
        this.name = $$0;
        this.ref = $$1;
        this.schema = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SchemaComponent.class), SchemaComponent.class, "name;ref;schema", "FIELD:Lnet/minecraft/server/jsonrpc/api/SchemaComponent;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/jsonrpc/api/SchemaComponent;->ref:Ljava/net/URI;", "FIELD:Lnet/minecraft/server/jsonrpc/api/SchemaComponent;->schema:Lnet/minecraft/server/jsonrpc/api/Schema;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SchemaComponent.class), SchemaComponent.class, "name;ref;schema", "FIELD:Lnet/minecraft/server/jsonrpc/api/SchemaComponent;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/jsonrpc/api/SchemaComponent;->ref:Ljava/net/URI;", "FIELD:Lnet/minecraft/server/jsonrpc/api/SchemaComponent;->schema:Lnet/minecraft/server/jsonrpc/api/Schema;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SchemaComponent.class, Object.class), SchemaComponent.class, "name;ref;schema", "FIELD:Lnet/minecraft/server/jsonrpc/api/SchemaComponent;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/jsonrpc/api/SchemaComponent;->ref:Ljava/net/URI;", "FIELD:Lnet/minecraft/server/jsonrpc/api/SchemaComponent;->schema:Lnet/minecraft/server/jsonrpc/api/Schema;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public String name() {
        return this.name;
    }

    public URI ref() {
        return this.ref;
    }

    public Schema<T> schema() {
        return this.schema;
    }

    public Schema<T> asRef() {
        return Schema.ofRef(this.ref, this.schema.codec());
    }

    public Schema<List<T>> asArray() {
        return Schema.arrayOf(asRef(), this.schema.codec());
    }
}
