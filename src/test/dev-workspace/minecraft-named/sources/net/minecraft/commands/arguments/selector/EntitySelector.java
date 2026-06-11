package net.minecraft.commands.arguments.selector;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.Permissions;
import net.minecraft.util.Util;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/arguments/selector/EntitySelector.class */
public class EntitySelector {
    public static final int INFINITE = Integer.MAX_VALUE;
    public static final BiConsumer<Vec3, List<? extends Entity>> ORDER_ARBITRARY = ($$0, $$1) -> {
    };
    private static final EntityTypeTest<Entity, ?> ANY_TYPE = new EntityTypeTest<Entity, Entity>() { // from class: net.minecraft.commands.arguments.selector.EntitySelector.1
        @Override // net.minecraft.world.level.entity.EntityTypeTest
        public Entity tryCast(Entity $$0) {
            return $$0;
        }

        @Override // net.minecraft.world.level.entity.EntityTypeTest
        public Class<? extends Entity> getBaseClass() {
            return Entity.class;
        }
    };
    private final int maxResults;
    private final boolean includesEntities;
    private final boolean worldLimited;
    private final List<Predicate<Entity>> contextFreePredicates;
    private final MinMaxBounds.Doubles range;
    private final Function<Vec3, Vec3> position;
    private final AABB aabb;
    private final BiConsumer<Vec3, List<? extends Entity>> order;
    private final boolean currentEntity;
    private final String playerName;
    private final UUID entityUUID;
    private final EntityTypeTest<Entity, ?> type;
    private final boolean usesSelector;

    public EntitySelector(int $$0, boolean $$1, boolean $$2, List<Predicate<Entity>> $$3, MinMaxBounds.Doubles $$4, Function<Vec3, Vec3> $$5, AABB $$6, BiConsumer<Vec3, List<? extends Entity>> $$7, boolean $$8, String $$9, UUID $$10, EntityType<?> $$11, boolean $$12) {
        this.maxResults = $$0;
        this.includesEntities = $$1;
        this.worldLimited = $$2;
        this.contextFreePredicates = $$3;
        this.range = $$4;
        this.position = $$5;
        this.aabb = $$6;
        this.order = $$7;
        this.currentEntity = $$8;
        this.playerName = $$9;
        this.entityUUID = $$10;
        this.type = $$11 == null ? ANY_TYPE : $$11;
        this.usesSelector = $$12;
    }

    public int getMaxResults() {
        return this.maxResults;
    }

    public boolean includesEntities() {
        return this.includesEntities;
    }

    public boolean isSelfSelector() {
        return this.currentEntity;
    }

    public boolean isWorldLimited() {
        return this.worldLimited;
    }

    public boolean usesSelector() {
        return this.usesSelector;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.brigadier.exceptions.CommandSyntaxException */
    private void checkPermissions(CommandSourceStack $$0) throws CommandSyntaxException {
        if (this.usesSelector && !$$0.permissions().hasPermission(Permissions.COMMANDS_ENTITY_SELECTORS)) {
            throw EntityArgument.ERROR_SELECTORS_NOT_ALLOWED.create();
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.brigadier.exceptions.CommandSyntaxException */
    public Entity findSingleEntity(CommandSourceStack $$0) throws CommandSyntaxException {
        checkPermissions($$0);
        List<? extends Entity> $$1 = findEntities($$0);
        if ($$1.isEmpty()) {
            throw EntityArgument.NO_ENTITIES_FOUND.create();
        }
        if ($$1.size() > 1) {
            throw EntityArgument.ERROR_NOT_SINGLE_ENTITY.create();
        }
        return $$1.get(0);
    }

    public List<? extends Entity> findEntities(CommandSourceStack $$0) throws CommandSyntaxException {
        checkPermissions($$0);
        if (!this.includesEntities) {
            return findPlayers($$0);
        }
        if (this.playerName != null) {
            ServerPlayer $$1 = $$0.getServer().getPlayerList().getPlayerByName(this.playerName);
            if ($$1 == null) {
                return List.of();
            }
            return List.of($$1);
        }
        if (this.entityUUID != null) {
            Iterator<ServerLevel> it = $$0.getServer().getAllLevels().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ServerLevel $$2 = it.next();
                Entity $$3 = $$2.getEntity(this.entityUUID);
                if ($$3 != null) {
                    if ($$3.getType().isEnabled($$0.enabledFeatures())) {
                        return List.of($$3);
                    }
                }
            }
            return List.of();
        }
        Vec3 $$4 = this.position.apply($$0.getPosition());
        AABB $$5 = getAbsoluteAabb($$4);
        if (this.currentEntity) {
            Predicate<Entity> $$6 = getPredicate($$4, $$5, null);
            if ($$0.getEntity() != null && $$6.test($$0.getEntity())) {
                return List.of($$0.getEntity());
            }
            return List.of();
        }
        Predicate<Entity> $$7 = getPredicate($$4, $$5, $$0.enabledFeatures());
        ObjectArrayList objectArrayList = new ObjectArrayList();
        if (isWorldLimited()) {
            addEntities(objectArrayList, $$0.getLevel(), $$5, $$7);
        } else {
            for (ServerLevel $$9 : $$0.getServer().getAllLevels()) {
                addEntities(objectArrayList, $$9, $$5, $$7);
            }
        }
        return sortAndLimit($$4, objectArrayList);
    }

    private void addEntities(List<Entity> $$0, ServerLevel $$1, AABB $$2, Predicate<Entity> $$3) {
        int $$4 = getResultLimit();
        if ($$0.size() >= $$4) {
            return;
        }
        if ($$2 != null) {
            $$1.getEntities(this.type, $$2, $$3, $$0, $$4);
        } else {
            $$1.getEntities(this.type, $$3, $$0, $$4);
        }
    }

    private int getResultLimit() {
        if (this.order == ORDER_ARBITRARY) {
            return this.maxResults;
        }
        return Integer.MAX_VALUE;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.brigadier.exceptions.CommandSyntaxException */
    public ServerPlayer findSinglePlayer(CommandSourceStack $$0) throws CommandSyntaxException {
        checkPermissions($$0);
        List<ServerPlayer> $$1 = findPlayers($$0);
        if ($$1.size() != 1) {
            throw EntityArgument.NO_PLAYERS_FOUND.create();
        }
        return $$1.get(0);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.brigadier.exceptions.CommandSyntaxException */
    public List<ServerPlayer> findPlayers(CommandSourceStack $$0) throws CommandSyntaxException {
        List<ServerPlayer> $$9;
        checkPermissions($$0);
        if (this.playerName != null) {
            ServerPlayer $$1 = $$0.getServer().getPlayerList().getPlayerByName(this.playerName);
            if ($$1 == null) {
                return List.of();
            }
            return List.of($$1);
        }
        if (this.entityUUID != null) {
            ServerPlayer $$2 = $$0.getServer().getPlayerList().getPlayer(this.entityUUID);
            if ($$2 == null) {
                return List.of();
            }
            return List.of($$2);
        }
        Vec3 $$3 = this.position.apply($$0.getPosition());
        AABB $$4 = getAbsoluteAabb($$3);
        Predicate<Entity> $$5 = getPredicate($$3, $$4, null);
        if (this.currentEntity) {
            Entity entity = $$0.getEntity();
            if (entity instanceof ServerPlayer) {
                ServerPlayer $$6 = (ServerPlayer) entity;
                if ($$5.test($$6)) {
                    return List.of($$6);
                }
            }
            return List.of();
        }
        int $$7 = getResultLimit();
        if (isWorldLimited()) {
            $$9 = $$0.getLevel().getPlayers($$5, $$7);
        } else {
            $$9 = new ObjectArrayList<>();
            for (ServerPlayer $$10 : $$0.getServer().getPlayerList().getPlayers()) {
                if ($$5.test($$10)) {
                    $$9.add($$10);
                    if ($$9.size() >= $$7) {
                        return $$9;
                    }
                }
            }
        }
        return sortAndLimit($$3, $$9);
    }

    private AABB getAbsoluteAabb(Vec3 $$0) {
        if (this.aabb != null) {
            return this.aabb.move($$0);
        }
        return null;
    }

    private Predicate<Entity> getPredicate(Vec3 $$0, AABB $$1, FeatureFlagSet $$2) {
        List<Predicate<Entity>> $$9;
        boolean $$3 = $$2 != null;
        boolean $$4 = $$1 != null;
        boolean $$5 = this.range != null;
        int $$6 = ($$3 ? 1 : 0) + ($$4 ? 1 : 0) + ($$5 ? 1 : 0);
        if ($$6 == 0) {
            $$9 = this.contextFreePredicates;
        } else {
            List<Predicate<Entity>> $$8 = new ObjectArrayList<>(this.contextFreePredicates.size() + $$6);
            $$8.addAll(this.contextFreePredicates);
            if ($$3) {
                $$8.add($$12 -> {
                    return $$12.getType().isEnabled($$2);
                });
            }
            if ($$4) {
                $$8.add($$13 -> {
                    return $$1.intersects($$13.getBoundingBox());
                });
            }
            if ($$5) {
                $$8.add($$14 -> {
                    return this.range.matchesSqr($$14.distanceToSqr($$0));
                });
            }
            $$9 = $$8;
        }
        return Util.allOf($$9);
    }

    private <T extends Entity> List<T> sortAndLimit(Vec3 $$0, List<T> $$1) {
        if ($$1.size() > 1) {
            this.order.accept($$0, $$1);
        }
        return $$1.subList(0, Math.min(this.maxResults, $$1.size()));
    }

    public static Component joinNames(List<? extends Entity> $$0) {
        return ComponentUtils.formatList($$0, (v0) -> {
            return v0.getDisplayName();
        });
    }
}
