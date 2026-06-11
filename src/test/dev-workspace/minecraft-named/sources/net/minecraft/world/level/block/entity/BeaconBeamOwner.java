package net.minecraft.world.level.block.entity;

import java.util.List;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/entity/BeaconBeamOwner.class */
public interface BeaconBeamOwner {
    List<Section> getBeamSections();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/entity/BeaconBeamOwner$Section.class */
    public static class Section {
        private final int color;
        private int height = 1;

        public Section(int $$0) {
            this.color = $$0;
        }

        public void increaseHeight() {
            this.height++;
        }

        public int getColor() {
            return this.color;
        }

        public int getHeight() {
            return this.height;
        }
    }
}
