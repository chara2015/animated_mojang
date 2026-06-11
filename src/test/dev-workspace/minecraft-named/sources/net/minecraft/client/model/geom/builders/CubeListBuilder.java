package net.minecraft.client.model.geom.builders;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import net.minecraft.core.Direction;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/geom/builders/CubeListBuilder.class */
public class CubeListBuilder {
    private static final Set<Direction> ALL_VISIBLE = EnumSet.allOf(Direction.class);
    private final List<CubeDefinition> cubes = Lists.newArrayList();
    private int xTexOffs;
    private int yTexOffs;
    private boolean mirror;

    public CubeListBuilder texOffs(int $$0, int $$1) {
        this.xTexOffs = $$0;
        this.yTexOffs = $$1;
        return this;
    }

    public CubeListBuilder mirror() {
        return mirror(true);
    }

    public CubeListBuilder mirror(boolean $$0) {
        this.mirror = $$0;
        return this;
    }

    public CubeListBuilder addBox(String $$0, float $$1, float $$2, float $$3, int $$4, int $$5, int $$6, CubeDeformation $$7, int $$8, int $$9) {
        texOffs($$8, $$9);
        this.cubes.add(new CubeDefinition($$0, this.xTexOffs, this.yTexOffs, $$1, $$2, $$3, $$4, $$5, $$6, $$7, this.mirror, 1.0f, 1.0f, ALL_VISIBLE));
        return this;
    }

    public CubeListBuilder addBox(String $$0, float $$1, float $$2, float $$3, int $$4, int $$5, int $$6, int $$7, int $$8) {
        texOffs($$7, $$8);
        this.cubes.add(new CubeDefinition($$0, this.xTexOffs, this.yTexOffs, $$1, $$2, $$3, $$4, $$5, $$6, CubeDeformation.NONE, this.mirror, 1.0f, 1.0f, ALL_VISIBLE));
        return this;
    }

    public CubeListBuilder addBox(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
        this.cubes.add(new CubeDefinition(null, this.xTexOffs, this.yTexOffs, $$0, $$1, $$2, $$3, $$4, $$5, CubeDeformation.NONE, this.mirror, 1.0f, 1.0f, ALL_VISIBLE));
        return this;
    }

    public CubeListBuilder addBox(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5, Set<Direction> $$6) {
        this.cubes.add(new CubeDefinition(null, this.xTexOffs, this.yTexOffs, $$0, $$1, $$2, $$3, $$4, $$5, CubeDeformation.NONE, this.mirror, 1.0f, 1.0f, $$6));
        return this;
    }

    public CubeListBuilder addBox(String $$0, float $$1, float $$2, float $$3, float $$4, float $$5, float $$6) {
        this.cubes.add(new CubeDefinition($$0, this.xTexOffs, this.yTexOffs, $$1, $$2, $$3, $$4, $$5, $$6, CubeDeformation.NONE, this.mirror, 1.0f, 1.0f, ALL_VISIBLE));
        return this;
    }

    public CubeListBuilder addBox(String $$0, float $$1, float $$2, float $$3, float $$4, float $$5, float $$6, CubeDeformation $$7) {
        this.cubes.add(new CubeDefinition($$0, this.xTexOffs, this.yTexOffs, $$1, $$2, $$3, $$4, $$5, $$6, $$7, this.mirror, 1.0f, 1.0f, ALL_VISIBLE));
        return this;
    }

    public CubeListBuilder addBox(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5, boolean $$6) {
        this.cubes.add(new CubeDefinition(null, this.xTexOffs, this.yTexOffs, $$0, $$1, $$2, $$3, $$4, $$5, CubeDeformation.NONE, $$6, 1.0f, 1.0f, ALL_VISIBLE));
        return this;
    }

    public CubeListBuilder addBox(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5, CubeDeformation $$6, float $$7, float $$8) {
        this.cubes.add(new CubeDefinition(null, this.xTexOffs, this.yTexOffs, $$0, $$1, $$2, $$3, $$4, $$5, $$6, this.mirror, $$7, $$8, ALL_VISIBLE));
        return this;
    }

    public CubeListBuilder addBox(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5, CubeDeformation $$6) {
        this.cubes.add(new CubeDefinition(null, this.xTexOffs, this.yTexOffs, $$0, $$1, $$2, $$3, $$4, $$5, $$6, this.mirror, 1.0f, 1.0f, ALL_VISIBLE));
        return this;
    }

    public List<CubeDefinition> getCubes() {
        return ImmutableList.copyOf(this.cubes);
    }

    public static CubeListBuilder create() {
        return new CubeListBuilder();
    }
}
