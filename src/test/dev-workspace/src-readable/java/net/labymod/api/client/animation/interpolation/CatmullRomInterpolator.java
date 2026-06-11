package net.labymod.api.client.animation.interpolation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/animation/interpolation/CatmullRomInterpolator.class */
public interface CatmullRomInterpolator<T> extends TypeInterpolator<T> {
    T catmullRom(T t, T t2, T t3, T t4, double d, T t5);
}
