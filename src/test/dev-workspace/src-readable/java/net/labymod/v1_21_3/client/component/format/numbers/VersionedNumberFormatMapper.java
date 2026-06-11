package net.labymod.v1_21_3.client.component.format.numbers;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.component.format.numbers.BlankFormat;
import net.labymod.api.client.component.format.numbers.FixedFormat;
import net.labymod.api.client.component.format.numbers.NumberFormat;
import net.labymod.api.client.component.format.numbers.NumberFormatMapper;
import net.labymod.api.client.component.format.numbers.StyledFormat;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/component/format/numbers/VersionedNumberFormatMapper.class */
@Singleton
@Implements(NumberFormatMapper.class)
public class VersionedNumberFormatMapper implements NumberFormatMapper {
    private final ComponentMapper componentMapper;

    @Inject
    public VersionedNumberFormatMapper(ComponentMapper componentMapper) {
        this.componentMapper = componentMapper;
    }

    @Override // net.labymod.api.client.component.format.numbers.NumberFormatMapper
    public NumberFormat fromMinecraft(Object obj) {
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof zl)) {
            throw new IllegalArgumentException("Object is not an instance of " + String.valueOf(zl.class));
        }
        zk zkVar = (zl) obj;
        if (zkVar instanceof zj) {
            return NumberFormat.blank();
        }
        if (zkVar instanceof zk) {
            zk fixedFormat = zkVar;
            return NumberFormat.fixed(this.componentMapper.fromMinecraftComponent(fixedFormat.b));
        }
        if (zkVar instanceof zo) {
            zo styledFormat = (zo) zkVar;
            return NumberFormat.styled(styledFormat.e);
        }
        throw new IllegalArgumentException("Unsupported number format: " + String.valueOf(zkVar.getClass()));
    }

    @Override // net.labymod.api.client.component.format.numbers.NumberFormatMapper
    public Object toMinecraft(NumberFormat format) {
        if (format == null) {
            return null;
        }
        if (format instanceof BlankFormat) {
            return zj.a;
        }
        if (format instanceof FixedFormat) {
            FixedFormat fixedFormat = (FixedFormat) format;
            return new zk((xv) this.componentMapper.toMinecraftComponent(fixedFormat.value()));
        }
        if (format instanceof StyledFormat) {
            StyledFormat styledFormat = (StyledFormat) format;
            return new zo(styledFormat.style());
        }
        throw new IllegalArgumentException("Unsupported number format: " + String.valueOf(format.getClass()));
    }
}
