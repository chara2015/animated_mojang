package net.labymod.api.mapper;

import java.lang.Enum;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/mapper/EnumMapper.class */
public interface EnumMapper<S extends Enum<S>, D extends Enum<D>> {
    D from(S s);

    S to(D d);

    Class<S> getSourceEnum();

    Class<D> getDestinationEnum();
}
