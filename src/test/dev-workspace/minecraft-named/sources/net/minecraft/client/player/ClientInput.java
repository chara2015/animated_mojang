package net.minecraft.client.player;

import net.minecraft.world.entity.player.Input;
import net.minecraft.world.phys.Vec2;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/player/ClientInput.class */
public class ClientInput {
    public Input keyPresses = Input.EMPTY;
    protected Vec2 moveVector = Vec2.ZERO;

    public void tick() {
    }

    public Vec2 getMoveVector() {
        return this.moveVector;
    }

    public boolean hasForwardImpulse() {
        return this.moveVector.y > 1.0E-5f;
    }

    public void makeJump() {
        this.keyPresses = new Input(this.keyPresses.forward(), this.keyPresses.backward(), this.keyPresses.left(), this.keyPresses.right(), true, this.keyPresses.shift(), this.keyPresses.sprint());
    }
}
