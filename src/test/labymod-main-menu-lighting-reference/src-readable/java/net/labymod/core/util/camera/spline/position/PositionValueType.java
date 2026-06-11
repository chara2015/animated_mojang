package net.labymod.core.util.camera.spline.position;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/util/camera/spline/position/PositionValueType.class */
public enum PositionValueType {
    X(new PositionValueAdapter() { // from class: net.labymod.core.util.camera.spline.position.PositionValueType.1
        @Override // net.labymod.core.util.camera.spline.position.PositionValueAdapter
        public double get(Location position) {
            return position.getX();
        }

        @Override // net.labymod.core.util.camera.spline.position.PositionValueAdapter
        public void set(Location position, double value) {
            position.setX(value);
        }
    }),
    Y(new PositionValueAdapter() { // from class: net.labymod.core.util.camera.spline.position.PositionValueType.2
        @Override // net.labymod.core.util.camera.spline.position.PositionValueAdapter
        public double get(Location position) {
            return position.getY();
        }

        @Override // net.labymod.core.util.camera.spline.position.PositionValueAdapter
        public void set(Location position, double value) {
            position.setY(value);
        }
    }),
    Z(new PositionValueAdapter() { // from class: net.labymod.core.util.camera.spline.position.PositionValueType.3
        @Override // net.labymod.core.util.camera.spline.position.PositionValueAdapter
        public double get(Location position) {
            return position.getZ();
        }

        @Override // net.labymod.core.util.camera.spline.position.PositionValueAdapter
        public void set(Location position, double value) {
            position.setZ(value);
        }
    }),
    YAW(new PositionValueAdapter() { // from class: net.labymod.core.util.camera.spline.position.PositionValueType.4
        @Override // net.labymod.core.util.camera.spline.position.PositionValueAdapter
        public double get(Location position) {
            return position.getYaw();
        }

        @Override // net.labymod.core.util.camera.spline.position.PositionValueAdapter
        public void set(Location position, double value) {
            position.setYaw(value);
        }
    }),
    PITCH(new PositionValueAdapter() { // from class: net.labymod.core.util.camera.spline.position.PositionValueType.5
        @Override // net.labymod.core.util.camera.spline.position.PositionValueAdapter
        public double get(Location position) {
            return position.getPitch();
        }

        @Override // net.labymod.core.util.camera.spline.position.PositionValueAdapter
        public void set(Location position, double value) {
            position.setPitch(value);
        }
    }),
    TILT(new PositionValueAdapter() { // from class: net.labymod.core.util.camera.spline.position.PositionValueType.6
        @Override // net.labymod.core.util.camera.spline.position.PositionValueAdapter
        public double get(Location position) {
            return position.getRoll();
        }

        @Override // net.labymod.core.util.camera.spline.position.PositionValueAdapter
        public void set(Location position, double value) {
            position.setRoll(value);
        }
    });

    private final PositionValueAdapter adapter;

    PositionValueType(PositionValueAdapter adapter) {
        this.adapter = adapter;
    }

    public PositionValueAdapter getAdapter() {
        return this.adapter;
    }

    public double get(Location position) {
        return this.adapter.get(position);
    }
}
