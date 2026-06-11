package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.Passthrough;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.RuleBlockEntityModifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/templatesystem/ProcessorRule.class */
public class ProcessorRule {
    public static final Passthrough DEFAULT_BLOCK_ENTITY_MODIFIER = Passthrough.INSTANCE;
    public static final Codec<ProcessorRule> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(RuleTest.CODEC.fieldOf("input_predicate").forGetter($$0 -> {
            return $$0.inputPredicate;
        }), RuleTest.CODEC.fieldOf("location_predicate").forGetter($$02 -> {
            return $$02.locPredicate;
        }), PosRuleTest.CODEC.lenientOptionalFieldOf("position_predicate", PosAlwaysTrueTest.INSTANCE).forGetter($$03 -> {
            return $$03.posPredicate;
        }), BlockState.CODEC.fieldOf("output_state").forGetter($$04 -> {
            return $$04.outputState;
        }), RuleBlockEntityModifier.CODEC.lenientOptionalFieldOf("block_entity_modifier", DEFAULT_BLOCK_ENTITY_MODIFIER).forGetter($$05 -> {
            return $$05.blockEntityModifier;
        })).apply($$0, ProcessorRule::new);
    });
    private final RuleTest inputPredicate;
    private final RuleTest locPredicate;
    private final PosRuleTest posPredicate;
    private final BlockState outputState;
    private final RuleBlockEntityModifier blockEntityModifier;

    public ProcessorRule(RuleTest $$0, RuleTest $$1, BlockState $$2) {
        this($$0, $$1, PosAlwaysTrueTest.INSTANCE, $$2);
    }

    public ProcessorRule(RuleTest $$0, RuleTest $$1, PosRuleTest $$2, BlockState $$3) {
        this($$0, $$1, $$2, $$3, DEFAULT_BLOCK_ENTITY_MODIFIER);
    }

    public ProcessorRule(RuleTest $$0, RuleTest $$1, PosRuleTest $$2, BlockState $$3, RuleBlockEntityModifier $$4) {
        this.inputPredicate = $$0;
        this.locPredicate = $$1;
        this.posPredicate = $$2;
        this.outputState = $$3;
        this.blockEntityModifier = $$4;
    }

    public boolean test(BlockState $$0, BlockState $$1, BlockPos $$2, BlockPos $$3, BlockPos $$4, RandomSource $$5) {
        return this.inputPredicate.test($$0, $$5) && this.locPredicate.test($$1, $$5) && this.posPredicate.test($$2, $$3, $$4, $$5);
    }

    public BlockState getOutputState() {
        return this.outputState;
    }

    public CompoundTag getOutputTag(RandomSource $$0, CompoundTag $$1) {
        return this.blockEntityModifier.apply($$0, $$1);
    }
}
