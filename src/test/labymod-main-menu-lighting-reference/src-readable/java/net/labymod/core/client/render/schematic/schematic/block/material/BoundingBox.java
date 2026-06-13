package net.labymod.core.client.render.schematic.block.material;

import net.labymod.api.util.math.vector.FloatVector3;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/block/material/BoundingBox.class */
public class BoundingBox {
    private final FloatVector3[] corners = new FloatVector3[8];

    public BoundingBox(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        set(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public FloatVector3 getCorner(int index) {
        return this.corners[index];
    }

    public FloatVector3 getCorner(int index, int rotation) {
        for (int i = 0; i < rotation; i++) {
            switch (index) {
                case 0:
                    index = 2;
                    break;
                case 1:
                case 5:
                    index--;
                    break;
                case 2:
                case 6:
                    index++;
                    break;
                case 3:
                    index = 1;
                    break;
                case 4:
                    index = 6;
                    break;
                case 7:
                    index = 5;
                    break;
            }
        }
        return this.corners[index];
    }

    public void setCorner(int index, float x, float y, float z) {
        FloatVector3 corner = this.corners[index];
        if (corner == null) {
            corner = new FloatVector3();
        }
        corner.set(x, y, z);
        this.corners[index] = corner;
    }

    public void set(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        setCorner(0, minX, minY, minZ);
        setCorner(1, minX, minY, maxZ);
        setCorner(2, maxX, minY, minZ);
        setCorner(3, maxX, minY, maxZ);
        setCorner(4, minX, maxY, minZ);
        setCorner(5, minX, maxY, maxZ);
        setCorner(6, maxX, maxY, minZ);
        setCorner(7, maxX, maxY, maxZ);
    }

    public void rotateX(float pivotX, float pivotY, float pivotZ, float degree) {
        translate(-pivotX, -pivotY, -pivotZ);
        rotateX(degree);
        translate(pivotX, pivotY, pivotZ);
    }

    public void rotateY(float pivotX, float pivotY, float pivotZ, float degree) {
        translate(-pivotX, -pivotY, -pivotZ);
        rotateY(degree);
        translate(pivotX, pivotY, pivotZ);
    }

    public void rotateZ(float pivotX, float pivotY, float pivotZ, float degree) {
        translate(-pivotX, -pivotY, -pivotZ);
        rotateZ(degree);
        translate(pivotX, pivotY, pivotZ);
    }

    private void rotateX(double degree) {
        float angleRadians = (float) Math.toRadians(degree);
        for (int i = 0; i < 8; i++) {
            FloatVector3 corner = getCorner(i);
            float y = corner.getY();
            float z = corner.getZ();
            corner.setY((float) ((Math.cos(angleRadians) * ((double) y)) - (Math.sin(angleRadians) * ((double) z))));
            corner.setZ((float) ((Math.sin(angleRadians) * ((double) y)) + (Math.cos(angleRadians) * ((double) z))));
        }
    }

    private void rotateY(double degree) {
        float angleRadians = (float) Math.toRadians(degree);
        for (int i = 0; i < 8; i++) {
            FloatVector3 corner = getCorner(i);
            float x = corner.getX();
            float z = corner.getZ();
            corner.setX((float) ((Math.cos(angleRadians) * ((double) x)) + (Math.sin(angleRadians) * ((double) z))));
            corner.setZ((float) (((-Math.sin(angleRadians)) * ((double) x)) + (Math.cos(angleRadians) * ((double) z))));
        }
    }

    private void rotateZ(double degree) {
        float angleRadians = (float) Math.toRadians(degree);
        for (int i = 0; i < 8; i++) {
            FloatVector3 corner = getCorner(i);
            float x = corner.getX();
            float y = corner.getY();
            corner.setX((float) ((Math.cos(angleRadians) * ((double) x)) - (Math.sin(angleRadians) * ((double) y))));
            corner.setY((float) ((Math.sin(angleRadians) * ((double) x)) + (Math.cos(angleRadians) * ((double) y))));
        }
    }

    private void translate(float deltaX, float deltaY, float deltaZ) {
        for (int i = 0; i < 8; i++) {
            FloatVector3 corner = getCorner(i);
            corner.setX(corner.getX() + deltaX);
            corner.setY(corner.getY() + deltaY);
            corner.setZ(corner.getZ() + deltaZ);
        }
    }
}
