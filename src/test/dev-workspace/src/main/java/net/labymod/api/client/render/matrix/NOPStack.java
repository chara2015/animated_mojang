package net.labymod.api.client.render.matrix;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/matrix/NOPStack.class */
public class NOPStack extends Stack {
    public NOPStack() {
        super(new DefaultStackProvider());
    }

    @Override // net.labymod.api.client.render.matrix.Stack
    public void push() {
    }

    @Override // net.labymod.api.client.render.matrix.Stack
    public void pop() {
    }

    @Override // net.labymod.api.client.render.matrix.Stack
    public void pushAndPop(Runnable runnable) {
    }

    @Override // net.labymod.api.client.render.matrix.Stack
    public void translate(float x, float y, float z) {
    }

    @Override // net.labymod.api.client.render.matrix.Stack
    public void rotate(float angle, float x, float y, float z) {
    }

    @Override // net.labymod.api.client.render.matrix.Stack
    public void rotateRadians(float radians, float x, float y, float z) {
    }

    @Override // net.labymod.api.client.render.matrix.Stack
    public void scale(float scale) {
    }

    @Override // net.labymod.api.client.render.matrix.Stack
    public void scale(float x, float y, float z) {
    }

    @Override // net.labymod.api.client.render.matrix.Stack
    public void identity() {
    }
}
