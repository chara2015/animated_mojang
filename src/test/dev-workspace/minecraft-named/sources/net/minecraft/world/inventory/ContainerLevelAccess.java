package net.minecraft.world.inventory;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/inventory/ContainerLevelAccess.class */
public interface ContainerLevelAccess {
    public static final ContainerLevelAccess NULL = new ContainerLevelAccess() { // from class: net.minecraft.world.inventory.ContainerLevelAccess.1
        @Override // net.minecraft.world.inventory.ContainerLevelAccess
        public <T> Optional<T> evaluate(BiFunction<Level, BlockPos, T> $$0) {
            return Optional.empty();
        }
    };

    <T> Optional<T> evaluate(BiFunction<Level, BlockPos, T> biFunction);

    static ContainerLevelAccess create(final Level $$0, final BlockPos $$1) {
        return new ContainerLevelAccess() { // from class: net.minecraft.world.inventory.ContainerLevelAccess.2
            @Override // net.minecraft.world.inventory.ContainerLevelAccess
            public <T> Optional<T> evaluate(BiFunction<Level, BlockPos, T> $$02) {
                return Optional.of($$02.apply($$0, $$1));
            }
        };
    }

    default <T> T evaluate(BiFunction<Level, BlockPos, T> $$0, T $$1) {
        return evaluate($$0).orElse($$1);
    }

    default void execute(BiConsumer<Level, BlockPos> $$0) {
        evaluate(($$1, $$2) -> {
            $$0.accept($$1, $$2);
            return Optional.empty();
        });
    }
}
