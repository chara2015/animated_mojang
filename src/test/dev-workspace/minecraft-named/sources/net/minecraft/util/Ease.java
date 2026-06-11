package net.minecraft.util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/Ease.class */
public class Ease {
    public static float inBack(float $$0) {
        return Mth.square($$0) * ((2.70158f * $$0) - 1.70158f);
    }

    public static float inBounce(float $$0) {
        return 1.0f - outBounce(1.0f - $$0);
    }

    public static float inCubic(float $$0) {
        return Mth.cube($$0);
    }

    public static float inElastic(float $$0) {
        if ($$0 == 0.0f) {
            return 0.0f;
        }
        if ($$0 == 1.0f) {
            return 1.0f;
        }
        return (float) ((-Math.pow(2.0d, (10.0d * ((double) $$0)) - 10.0d)) * Math.sin(((((double) $$0) * 10.0d) - 10.75d) * 2.094395160675049d));
    }

    public static float inExpo(float $$0) {
        if ($$0 == 0.0f) {
            return 0.0f;
        }
        return (float) Math.pow(2.0d, (10.0d * ((double) $$0)) - 10.0d);
    }

    public static float inQuart(float $$0) {
        return Mth.square(Mth.square($$0));
    }

    public static float inQuint(float $$0) {
        return Mth.square(Mth.square($$0)) * $$0;
    }

    public static float inSine(float $$0) {
        return 1.0f - Mth.cos($$0 * 1.5707964f);
    }

    public static float inOutBounce(float $$0) {
        if ($$0 < 0.5f) {
            return (1.0f - outBounce(1.0f - (2.0f * $$0))) / 2.0f;
        }
        return (1.0f + outBounce((2.0f * $$0) - 1.0f)) / 2.0f;
    }

    public static float inOutCirc(float $$0) {
        if ($$0 < 0.5f) {
            return (float) ((1.0d - Math.sqrt(1.0d - Math.pow(2.0d * ((double) $$0), 2.0d))) / 2.0d);
        }
        return (float) ((Math.sqrt(1.0d - Math.pow(((-2.0d) * ((double) $$0)) + 2.0d, 2.0d)) + 1.0d) / 2.0d);
    }

    public static float inOutCubic(float $$0) {
        if ($$0 < 0.5f) {
            return 4.0f * Mth.cube($$0);
        }
        return (float) (1.0d - (Math.pow(((-2.0d) * ((double) $$0)) + 2.0d, 3.0d) / 2.0d));
    }

    public static float inOutQuad(float $$0) {
        if ($$0 < 0.5f) {
            return 2.0f * Mth.square($$0);
        }
        return (float) (1.0d - (Math.pow(((-2.0d) * ((double) $$0)) + 2.0d, 2.0d) / 2.0d));
    }

    public static float inOutQuart(float $$0) {
        if ($$0 < 0.5f) {
            return 8.0f * Mth.square(Mth.square($$0));
        }
        return (float) (1.0d - (Math.pow(((-2.0d) * ((double) $$0)) + 2.0d, 4.0d) / 2.0d));
    }

    public static float inOutQuint(float $$0) {
        if ($$0 < 0.5d) {
            return 16.0f * $$0 * $$0 * $$0 * $$0 * $$0;
        }
        return (float) (1.0d - (Math.pow(((-2.0d) * ((double) $$0)) + 2.0d, 5.0d) / 2.0d));
    }

    public static float outBounce(float $$0) {
        if ($$0 < 0.36363637f) {
            return 7.5625f * Mth.square($$0);
        }
        if ($$0 < 0.72727275f) {
            return (7.5625f * Mth.square($$0 - 0.54545456f)) + 0.75f;
        }
        if ($$0 < 0.9090909090909091d) {
            return (7.5625f * Mth.square($$0 - 0.8181818f)) + 0.9375f;
        }
        return (7.5625f * Mth.square($$0 - 0.95454544f)) + 0.984375f;
    }

    public static float outElastic(float $$0) {
        if ($$0 == 0.0f) {
            return 0.0f;
        }
        if ($$0 == 1.0f) {
            return 1.0f;
        }
        return (float) ((Math.pow(2.0d, (-10.0d) * ((double) $$0)) * Math.sin(((((double) $$0) * 10.0d) - 0.75d) * 2.094395160675049d)) + 1.0d);
    }

    public static float outExpo(float $$0) {
        if ($$0 == 1.0f) {
            return 1.0f;
        }
        return 1.0f - ((float) Math.pow(2.0d, (-10.0d) * ((double) $$0)));
    }

    public static float outQuad(float $$0) {
        return 1.0f - Mth.square(1.0f - $$0);
    }

    public static float outQuint(float $$0) {
        return 1.0f - ((float) Math.pow(1.0d - ((double) $$0), 5.0d));
    }

    public static float outSine(float $$0) {
        return Mth.sin($$0 * 1.5707964f);
    }

    public static float inOutSine(float $$0) {
        return (-(Mth.cos(3.1415927f * $$0) - 1.0f)) / 2.0f;
    }

    public static float outBack(float $$0) {
        return 1.0f + (2.70158f * Mth.cube($$0 - 1.0f)) + (1.70158f * Mth.square($$0 - 1.0f));
    }

    public static float outQuart(float $$0) {
        return 1.0f - Mth.square(Mth.square(1.0f - $$0));
    }

    public static float outCubic(float $$0) {
        return 1.0f - Mth.cube(1.0f - $$0);
    }

    public static float inOutExpo(float $$0) {
        if ($$0 < 0.5f) {
            if ($$0 == 0.0f) {
                return 0.0f;
            }
            return (float) (Math.pow(2.0d, (20.0d * ((double) $$0)) - 10.0d) / 2.0d);
        }
        if ($$0 == 1.0f) {
            return 1.0f;
        }
        return (float) ((2.0d - Math.pow(2.0d, ((-20.0d) * ((double) $$0)) + 10.0d)) / 2.0d);
    }

    public static float inQuad(float $$0) {
        return $$0 * $$0;
    }

    public static float outCirc(float $$0) {
        return (float) Math.sqrt(1.0f - Mth.square($$0 - 1.0f));
    }

    public static float inOutElastic(float $$0) {
        if ($$0 == 0.0f) {
            return 0.0f;
        }
        if ($$0 == 1.0f) {
            return 1.0f;
        }
        double $$2 = Math.sin(((20.0d * ((double) $$0)) - 11.125d) * 1.3962634801864624d);
        if ($$0 < 0.5f) {
            return (float) ((-(Math.pow(2.0d, (20.0d * ((double) $$0)) - 10.0d) * $$2)) / 2.0d);
        }
        return (float) (((Math.pow(2.0d, ((-20.0d) * ((double) $$0)) + 10.0d) * $$2) / 2.0d) + 1.0d);
    }

    public static float inCirc(float $$0) {
        return ((float) (-Math.sqrt(1.0f - ($$0 * $$0)))) + 1.0f;
    }

    public static float inOutBack(float $$0) {
        if ($$0 < 0.5f) {
            return (((4.0f * $$0) * $$0) * ((7.189819f * $$0) - 2.5949094f)) / 2.0f;
        }
        float $$3 = (2.0f * $$0) - 2.0f;
        return ((($$3 * $$3) * ((3.5949094f * $$3) + 2.5949094f)) + 2.0f) / 2.0f;
    }
}
