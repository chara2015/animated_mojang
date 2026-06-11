package net.labymod.api.client.component.format.numbers;

import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/format/numbers/NumberFormatMapper.class */
@Referenceable
@Nullable
public interface NumberFormatMapper {
    NumberFormat fromMinecraft(Object obj);

    Object toMinecraft(NumberFormat numberFormat);
}
