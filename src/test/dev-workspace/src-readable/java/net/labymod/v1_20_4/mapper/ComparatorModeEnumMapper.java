package net.labymod.v1_20_4.mapper;

import net.labymod.api.client.world.block.properties.ComparisonOperator;
import net.labymod.api.mapper.AbstractEnumMapper;
import net.labymod.api.mapper.EnumMapper;
import net.labymod.api.service.annotation.AutoService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mapper/ComparatorModeEnumMapper.class */
@AutoService(value = EnumMapper.class, versionSpecific = true)
public class ComparatorModeEnumMapper extends AbstractEnumMapper<dka, ComparisonOperator> {
    public ComparatorModeEnumMapper() {
        super(dka.class, ComparisonOperator.class);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.mapper.EnumMapper
    public ComparisonOperator from(dka source) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$level$block$state$properties$ComparatorMode[source.ordinal()]) {
            case 1:
                return ComparisonOperator.COMPARE;
            case 2:
                return ComparisonOperator.SUBTRACT;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: renamed from: net.labymod.v1_20_4.mapper.ComparatorModeEnumMapper$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mapper/ComparatorModeEnumMapper$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$level$block$state$properties$ComparatorMode;

        static {
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$ComparisonOperator[ComparisonOperator.COMPARE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$ComparisonOperator[ComparisonOperator.SUBTRACT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            $SwitchMap$net$minecraft$world$level$block$state$properties$ComparatorMode = new int[dka.values().length];
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$ComparatorMode[dka.a.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$ComparatorMode[dka.b.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.mapper.EnumMapper
    public dka to(ComparisonOperator destination) throws MatchException {
        switch (destination) {
            case COMPARE:
                return dka.a;
            case SUBTRACT:
                return dka.b;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }
}
