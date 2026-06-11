package net.minecraft.gizmos;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/gizmos/GizmoCollector.class */
public interface GizmoCollector {
    public static final GizmoProperties IGNORED = new GizmoProperties() { // from class: net.minecraft.gizmos.GizmoCollector.1
        @Override // net.minecraft.gizmos.GizmoProperties
        public GizmoProperties setAlwaysOnTop() {
            return this;
        }

        @Override // net.minecraft.gizmos.GizmoProperties
        public GizmoProperties persistForMillis(int $$0) {
            return this;
        }

        @Override // net.minecraft.gizmos.GizmoProperties
        public GizmoProperties fadeOut() {
            return this;
        }
    };
    public static final GizmoCollector NOOP = $$0 -> {
        return IGNORED;
    };

    GizmoProperties add(Gizmo gizmo);
}
