package net.minecraft.world.entity.player;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/player/Input.class */
public final class Input extends Record {
    private final boolean forward;
    private final boolean backward;
    private final boolean left;
    private final boolean right;
    private final boolean jump;
    private final boolean shift;
    private final boolean sprint;
    private static final byte FLAG_FORWARD = 1;
    private static final byte FLAG_BACKWARD = 2;
    private static final byte FLAG_LEFT = 4;
    private static final byte FLAG_RIGHT = 8;
    private static final byte FLAG_JUMP = 16;
    private static final byte FLAG_SHIFT = 32;
    private static final byte FLAG_SPRINT = 64;
    public static final StreamCodec<FriendlyByteBuf, Input> STREAM_CODEC = new StreamCodec<FriendlyByteBuf, Input>() { // from class: net.minecraft.world.entity.player.Input.1
        @Override // net.minecraft.network.codec.StreamEncoder
        public void encode(FriendlyByteBuf $$0, Input $$1) {
            byte $$2 = (byte) (0 | ($$1.forward() ? 1 : 0));
            $$0.m1593writeByte((int) ((byte) (((byte) (((byte) (((byte) (((byte) (((byte) ($$2 | ($$1.backward() ? (byte) 2 : (byte) 0))) | ($$1.left() ? (byte) 4 : (byte) 0))) | ($$1.right() ? (byte) 8 : (byte) 0))) | ($$1.jump() ? (byte) 16 : (byte) 0))) | ($$1.shift() ? (byte) 32 : (byte) 0))) | ($$1.sprint() ? (byte) 64 : (byte) 0))));
        }

        @Override // net.minecraft.network.codec.StreamDecoder
        public Input decode(FriendlyByteBuf $$0) {
            byte $$1 = $$0.readByte();
            boolean $$2 = ($$1 & 1) != 0;
            boolean $$3 = ($$1 & 2) != 0;
            boolean $$4 = ($$1 & 4) != 0;
            boolean $$5 = ($$1 & 8) != 0;
            boolean $$6 = ($$1 & 16) != 0;
            boolean $$7 = ($$1 & 32) != 0;
            boolean $$8 = ($$1 & 64) != 0;
            return new Input($$2, $$3, $$4, $$5, $$6, $$7, $$8);
        }
    };
    public static Input EMPTY = new Input(false, false, false, false, false, false, false);

    public Input(boolean $$0, boolean $$1, boolean $$2, boolean $$3, boolean $$4, boolean $$5, boolean $$6) {
        this.forward = $$0;
        this.backward = $$1;
        this.left = $$2;
        this.right = $$3;
        this.jump = $$4;
        this.shift = $$5;
        this.sprint = $$6;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Input.class), Input.class, "forward;backward;left;right;jump;shift;sprint", "FIELD:Lnet/minecraft/world/entity/player/Input;->forward:Z", "FIELD:Lnet/minecraft/world/entity/player/Input;->backward:Z", "FIELD:Lnet/minecraft/world/entity/player/Input;->left:Z", "FIELD:Lnet/minecraft/world/entity/player/Input;->right:Z", "FIELD:Lnet/minecraft/world/entity/player/Input;->jump:Z", "FIELD:Lnet/minecraft/world/entity/player/Input;->shift:Z", "FIELD:Lnet/minecraft/world/entity/player/Input;->sprint:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Input.class), Input.class, "forward;backward;left;right;jump;shift;sprint", "FIELD:Lnet/minecraft/world/entity/player/Input;->forward:Z", "FIELD:Lnet/minecraft/world/entity/player/Input;->backward:Z", "FIELD:Lnet/minecraft/world/entity/player/Input;->left:Z", "FIELD:Lnet/minecraft/world/entity/player/Input;->right:Z", "FIELD:Lnet/minecraft/world/entity/player/Input;->jump:Z", "FIELD:Lnet/minecraft/world/entity/player/Input;->shift:Z", "FIELD:Lnet/minecraft/world/entity/player/Input;->sprint:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Input.class, Object.class), Input.class, "forward;backward;left;right;jump;shift;sprint", "FIELD:Lnet/minecraft/world/entity/player/Input;->forward:Z", "FIELD:Lnet/minecraft/world/entity/player/Input;->backward:Z", "FIELD:Lnet/minecraft/world/entity/player/Input;->left:Z", "FIELD:Lnet/minecraft/world/entity/player/Input;->right:Z", "FIELD:Lnet/minecraft/world/entity/player/Input;->jump:Z", "FIELD:Lnet/minecraft/world/entity/player/Input;->shift:Z", "FIELD:Lnet/minecraft/world/entity/player/Input;->sprint:Z").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public boolean forward() {
        return this.forward;
    }

    public boolean backward() {
        return this.backward;
    }

    public boolean left() {
        return this.left;
    }

    public boolean right() {
        return this.right;
    }

    public boolean jump() {
        return this.jump;
    }

    public boolean shift() {
        return this.shift;
    }

    public boolean sprint() {
        return this.sprint;
    }
}
