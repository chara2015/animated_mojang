package net.labymod.v1_16_5.client.util.datafix;

import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.DataFixerBuilder;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/client/util/datafix/FastDataFixerBuilder.class */
public class FastDataFixerBuilder extends DataFixerBuilder {
    private static final Executor EXECUTOR = executor -> {
    };

    public FastDataFixerBuilder() {
        super(2730);
    }

    public DataFixer build(Executor executor) {
        return super.build(EXECUTOR);
    }
}
