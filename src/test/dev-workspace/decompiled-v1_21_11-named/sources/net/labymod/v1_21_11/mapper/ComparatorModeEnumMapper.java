package net.labymod.v1_21_11.mapper;

import net.labymod.api.client.world.block.properties.ComparisonOperator;
import net.labymod.api.mapper.AbstractEnumMapper;
import net.labymod.api.mapper.EnumMapper;
import net.labymod.api.service.annotation.AutoService;
import net.minecraft.world.level.block.state.properties.ComparatorMode;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mapper/ComparatorModeEnumMapper.class */
@AutoService(value = EnumMapper.class, versionSpecific = true)
public class ComparatorModeEnumMapper extends AbstractEnumMapper<ComparatorMode, ComparisonOperator> {
    public ComparatorModeEnumMapper() {
        super(ComparatorMode.class, ComparisonOperator.class);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public ComparisonOperator from(ComparatorMode source) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$level$block$state$properties$ComparatorMode[source.ordinal()]) {
            case 1:
                return ComparisonOperator.COMPARE;
            case 2:
                return ComparisonOperator.SUBTRACT;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_11.mapper.ComparatorModeEnumMapper$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mapper/ComparatorModeEnumMapper$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$level$block$state$properties$ComparatorMode;
        static final /* synthetic */ int[] $SwitchMap$net$labymod$api$client$world$block$properties$ComparisonOperator = new int[ComparisonOperator.values().length];

        static {
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$ComparisonOperator[ComparisonOperator.COMPARE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$ComparisonOperator[ComparisonOperator.SUBTRACT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            $SwitchMap$net$minecraft$world$level$block$state$properties$ComparatorMode = new int[ComparatorMode.values().length];
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$ComparatorMode[ComparatorMode.COMPARE.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$ComparatorMode[ComparatorMode.SUBTRACT.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public ComparatorMode to(ComparisonOperator destination) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$labymod$api$client$world$block$properties$ComparisonOperator[destination.ordinal()]) {
            case 1:
                return ComparatorMode.COMPARE;
            case 2:
                return ComparatorMode.SUBTRACT;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }
}
