package net.labymod.api.mapper;

import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/mapper/EnumMapperRegistry.class */
@Referenceable
public interface EnumMapperRegistry {
    <S extends Enum<S>, D extends Enum<D>> D from(S s);

    <S extends Enum<S>, D extends Enum<D>> S to(D d);
}
