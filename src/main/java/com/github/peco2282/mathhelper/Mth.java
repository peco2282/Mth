package com.github.peco2282.mathhelper;

import com.github.peco2282.mathhelper.utils.ClassNotMatchException;
import com.github.peco2282.mathhelper.utils.Overload;
import com.github.peco2282.mathhelper.utils.SuppressString;
import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.IntPredicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;


/**
 * @author peco2282
 */
@SuppressWarnings({SuppressString.UNUSED})
public class Mth {
    public static final float PI = (float) Math.PI;
    public static final float HALF_PI = ((float) Math.PI / 2F);
    public static final float TWO_PI = ((float) Math.PI * 2F);
    public static final float DEG_TO_RAD = ((float) Math.PI / 180F);
    public static final float RAD_TO_DEG = (180F / (float) Math.PI);
    public static final float EPSILON = 1.0E-5F;
    public static final float SQRT_WITH_TWO = sqrt(2.0F);
    private static final float SIN_SCALE = 10430.378F;

    private static final float[] SIN = make(new float[65536], c -> {
        for (int i = 0; i < c.length; i++) {
            c[i] = (float) Math.sin(i * Math.PI * 2.0D / 65536.0D);
        }
    });
    private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION = new int[]{0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9};
    private static final double ONE_SIXTH = 0.16666666666666666D;
    private static final int FRAC_EXP = 8;
    private static final int LUT_SIZE = 257;
    private static final double FRAC_BIAS = Double.longBitsToDouble(4805340802404319232L);
    private static final double[] ASIN_TAB = new double[257];
    private static final double[] COS_TAB = new double[257];

    private static final double DOUBLE_MIN = Double.MIN_VALUE;
    private static final double DOUBLE_MAX = Double.MAX_VALUE;
    private static final float FLOAT_MIN = Float.MIN_VALUE;
    private static final float FLOAT_MAX = Float.MAX_VALUE;
    private static final float FLOAT_EXPONENT_MIN = Float.MIN_EXPONENT;
    private static final float FLOAT_EXPONENT_MAX = Float.MIN_EXPONENT;
    private static final byte BYTE_MIN = Byte.MIN_VALUE;
    private static final byte BYTE_MAX = Byte.MAX_VALUE;
    private static final short SHORT_MIN = Short.MIN_VALUE;
    private static final short SHORT_MAX = Short.MAX_VALUE;
    private static final int INT_MIN = Integer.MIN_VALUE;
    private static final int INT_MAX = Integer.MAX_VALUE;
    private static final long LONG_MIN = Long.MIN_VALUE;
    private static final long LONG_MAX = Long.MAX_VALUE;

    static {
        for (int i = 0; i < 257; ++i) {
            double d0 = (double) i / 256.0D;
            double d1 = Math.asin(d0);
            COS_TAB[i] = Math.cos(d1);
            ASIN_TAB[i] = d1;
        }

    }

    /**
     * @param value The value you want to.
     * @return sin value
     */
    public static float sin(float value) {
        return SIN[(int) (value * 10430.378F) & '\uffff'];
    }
    /**
     * @param value The value you want to.
     * @return cos value
     */
    public static float cos(float value) {
        return SIN[(int) (value * 10430.378F + 16384.0F) & '\uffff'];
    }

    public static float sqrt(float value) {
        return (float) Math.sqrt(value);
    }

    @Overload
    public static int floor(float value) {
        int i = (int) value;
        return value < (float) i ? i - 1 : i;
    }

    @Overload
    public static int floor(double value) {
        int i = (int) value;
        return value < (double) i ? i - 1 : i;
    }

    public static long lfloor(double value) {
        long i = (long) value;
        return value < (double) i ? i - 1L : i;
    }

    @Overload
    public static float abs(float value) {
        return Math.abs(value);
    }

    @Overload
    public static int abs(int value) {
        return Math.abs(value);
    }

    @Overload
    public static int ceil(float value) {
        int i = (int) value;
        return value > (float) i ? i + 1 : i;
    }

    @Overload
    public static int ceil(double value) {
        int i = (int) value;
        return value > (double) i ? i + 1 : i;
    }
    @Overload
    public static int clamp(int max1, int max2, int min) {
        return Math.min(Math.max(max1, max2), min);
    }

    @Overload
    public static float clamp(float min1, float max, float min2) {
        return min1 < max ? max : Math.min(min1, min2);
    }

    @Overload
    public static double clamp(double min1, double max, double min2) {
        return min1 < max ? max : Math.min(min1, min2);
    }

    @Overload
    public static double clampedLerp(double min1, double max, double min2) {
        if (min2 < 0.0D) {
            return min1;
        } else {
            return min2 > 1.0D ? max : lerp(min2, min1, max);
        }
    }

    @Overload
    public static float clampedLerp(float min1, float max, float min2) {
        if (min2 < 0.0F) {
            return min1;
        } else {
            return min2 > 1.0F ? max : lerp(min2, min1, max);
        }
    }

    public static double absMax(double abs1, double abs2) {
        if (abs1 < 0.0D) {
            abs1 = -abs1;
        }

        if (abs2 < 0.0D) {
            abs2 = -abs2;
        }

        return Math.max(abs1, abs2);
    }

    public static int floorDiv(int x, int y) {
        return Math.floorDiv(x, y);
    }

    public static boolean equal(float abs1, float abs2) {
        return Math.abs(abs2 - abs1) < 1.0E-5F;
    }

    public static boolean equal(double abs1, double abs2) {
        return Math.abs(abs2 - abs1) < (double) 1.0E-5F;
    }

    public static int positiveModulo(int x, int y) {
        return Math.floorMod(x, y);
    }

    public static float positiveModulo(float a, float b) {
        return (a % b + b) % b;
    }

    public static double positiveModulo(double a, double b) {
        return (a % b + b) % b;
    }

    public static boolean isMultipleOf(int multiple, int value) {
        return multiple % value == 0;
    }

    public static int wrapDegrees(int deg) {
        int i = deg % 360;
        if (i >= 180) {
            i -= 360;
        }

        if (i < -180) {
            i += 360;
        }

        return i;
    }

    public static float wrapDegrees(float deg) {
        float f = deg % 360.0F;
        if (f >= 180.0F) {
            f -= 360.0F;
        }

        if (f < -180.0F) {
            f += 360.0F;
        }

        return f;
    }

    public static double wrapDegrees(double deg) {
        double d0 = deg % 360.0D;
        if (d0 >= 180.0D) {
            d0 -= 360.0D;
        }

        if (d0 < -180.0D) {
            d0 += 360.0D;
        }

        return d0;
    }

    public static float degreesDifference(float deg1, float deg2) {
        return wrapDegrees(deg2 - deg1);
    }

    public static float degreesDifferenceAbs(float deg1, float deg2) {
        return abs(degreesDifference(deg1, deg2));
    }

    public static float rotateIfNecessary(float deg1, float deg2, float cl) {
        float f = degreesDifference(deg1, deg2);
        float f1 = clamp(f, -cl, cl);
        return deg2 - f1;
    }

    public static float approach(float cl1, float cl2, float value) {
        value = abs(value);
        return cl1 < cl2 ? clamp(cl1 + value, cl1, cl2) : clamp(cl1 - value, cl2, cl1);
    }

    public static float approachDegrees(float deg1, float deg2, float value) {
        float f = degreesDifference(deg1, deg2);
        return approach(deg1, deg1 + f, value);
    }

    public static int getInt(String str, int defaultValue) {
        return NumberUtils.toInt(str, defaultValue);
    }

    public static int smallestEncompassingPowerOfTwo(int encompass) {
        int i = encompass - 1;
        i |= i >> 1;
        i |= i >> 2;
        i |= i >> 4;
        i |= i >> 8;
        i |= i >> 16;
        return i + 1;
    }

    public static boolean isPowerOfTwo(int power) {
        return power != 0 && (power & power - 1) == 0;
    }

    public static int ceillog2(int log) {
        log = isPowerOfTwo(log) ? log : smallestEncompassingPowerOfTwo(log);
        return MULTIPLY_DE_BRUIJN_BIT_POSITION[(int) ((long) log * 125613361L >> 27) & 31];
    }

    public static int log2(int log) {
        return ceillog2(log) - (isPowerOfTwo(log) ? 0 : 1);
    }

    public static int color(float r, float g, float b) {
        return ARGB32.color(0, floor(r * 255.0F), floor(g * 255.0F), floor(b * 255.0F));
    }

    public static float frac(float value) {
        return value - (float) floor(value);
    }

    public static double frac(double value) {
        return value - (double) lfloor(value);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static long getSeed(int s1, int s2, int s3) {
        long i = (s1 * 3129871L) ^ (long) s3 * 116129781L ^ (long) s2;
        i = i * i * 42317861L + i * 11L;
        return i >> 16;
    }

    public static double inverseLerp(double v1, double value, double v2) {
        return (v1 - value) / (v2 - value);
    }

    public static float inverseLerp(float v1, float value, float v2) {
        return (v1 - value) / (v2 - value);
    }

    @SuppressWarnings({SuppressString.REASSIGNED_VARIABLE, SuppressString.SUSPICIOUS_NAME_COMBINATION})
    public static double atan2(double x, double y) {
        double d0 = y * y + x * x;
        if (Double.isNaN(d0)) {
            return Double.NaN;
        } else {
            boolean flag = x < 0.0D;
            if (flag) {
                x = -x;
            }

            boolean flag1 = y < 0.0D;
            if (flag1) {
                y = -y;
            }

            boolean flag2 = x > y;
            if (flag2) {
                double d1 = y;
                y = x;
                x = d1;
            }

            double d9 = fastInvSqrt(d0);
            y *= d9;
            x *= d9;
            double d2 = FRAC_BIAS + x;
            int i = (int) Double.doubleToRawLongBits(d2);
            double d3 = ASIN_TAB[i];
            double d4 = COS_TAB[i];
            double d5 = d2 - FRAC_BIAS;
            double d6 = x * d4 - y * d5;
            double d7 = (6.0D + d6 * d6) * d6 * 0.16666666666666666D;
            double d8 = d3 + d7;
            if (flag2) {
                d8 = (Math.PI / 2D) - d8;
            }

            if (flag1) {
                d8 = Math.PI - d8;
            }

            if (flag) {
                d8 = -d8;
            }

            return d8;
        }
    }

    public static float invSqrt(float r) {
        return 1.0f / (float) java.lang.Math.sqrt(r);
    }

    public static double invSqrt(double r) {
        return 1.0 / java.lang.Math.sqrt(r);

    }

    public static double fastInvSqrt(double value) {
        double d0 = 0.5D * value;
        long i = Double.doubleToRawLongBits(value);
        i = 6910469410427058090L - (i >> 1);
        value = Double.longBitsToDouble(i);
        return value * (1.5D - d0 * value * value);
    }

    public static float fastInvCubeRoot(float value) {
        int i = Float.floatToIntBits(value);
        i = 1419967116 - i / 3;
        float f = Float.intBitsToFloat(i);
        f = 0.6666667F * f + 1.0F / (3.0F * f * f * value);
        return 0.6666667F * f + 1.0F / (3.0F * f * f * value);
    }

    public static int hsvToRgb(float h, float s, float v) {
        int i = (int) (h * 6.0F) % 6;
        float f = h * 6.0F - (float) i;
        float f1 = v * (1.0F - s);
        float f2 = v * (1.0F - f * s);
        float f3 = v * (1.0F - (1.0F - f) * s);
        float f4;
        float f5;
        float f6;
        switch (i) {
            case 0 -> {
                f4 = v;
                f5 = f3;
                f6 = f1;
            }
            case 1 -> {
                f4 = f2;
                f5 = v;
                f6 = f1;
            }
            case 2 -> {
                f4 = f1;
                f5 = v;
                f6 = f3;
            }
            case 3 -> {
                f4 = f1;
                f5 = f2;
                f6 = v;
            }
            case 4 -> {
                f4 = f3;
                f5 = f1;
                f6 = v;
            }
            case 5 -> {
                f4 = v;
                f5 = f1;
                f6 = f2;
            }
            default ->
                    throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + h + ", " + s + ", " + v);
        }

        return ARGB32.color(0, clamp((int) (f4 * 255.0F), 0, 255), clamp((int) (f5 * 255.0F), 0, 255), clamp((int) (f6 * 255.0F), 0, 255));
    }

    public static int murmurHash3Mixer(int value) {
        value ^= value >>> 16;
        value *= -2048144789;
        value ^= value >>> 13;
        value *= -1028477387;
        return value ^ value >>> 16;
    }

    public static int binarySearch(int bin, int value, IntPredicate pred) {
        int i = value - bin;

        while (i > 0) {
            int j = i / 2;
            int k = bin + j;
            if (pred.test(k)) {
                i = j;
            } else {
                bin = k + 1;
                i -= j + 1;
            }
        }

        return bin;
    }

    public static int lerpInt(float min, int base, int max) {
        return base + floor(min * (float) (max - base));
    }

    public static float lerp(float value, float x, float y) {
        return x + value * (y - x);
    }

    public static double lerp(double value, double x, double y) {
        return x + value * (y - x);
    }

    public static double lerp2(double value1, double value2, double x1, double y1, double x2, double y2) {
        return lerp(value2, lerp(value1, x1, y1), lerp(value1, x2, y2));
    }

    public static double lerp3(double value1, double value2, double value3, double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
        return lerp(value3, lerp2(value1, value2, x1, y1, x2, y2), lerp2(value1, value2, x3, y3, x4, y4));
    }

    public static float catmullrom(float value1, float value2, float value3, float value4, float value5) {
        return 0.5F * (2.0F * value3 + (value4 - value2) * value1 + (2.0F * value2 - 5.0F * value3 + 4.0F * value4 - value5) * value1 * value1 + (3.0F * value3 - value2 - 3.0F * value4 + value5) * value1 * value1 * value1);
    }

    public static double smoothstep(double step) {
        return step * step * step * (step * (step * 6.0D - 15.0D) + 10.0D);
    }

    public static double smoothstepDerivative(double step) {
        return 30.0D * step * step * (step - 1.0D) * (step - 1.0D);
    }

    public static int sign(double s) {
        if (s == 0.0D) {
            return 0;
        } else {
            return s > 0.0D ? 1 : -1;
        }
    }

    public static float rotLerp(float multi, float deg1, float deg2) {
        return deg1 + multi * wrapDegrees(deg2 - deg1);
    }

    public static float triangleWave(float x, float y) {
        return (Math.abs(x % y - y * 0.5F) - y * 0.25F) / (y * 0.25F);
    }

    public static float square(float value) {
        return value * value;
    }

    public static double square(double value) {
        return value * value;
    }

    public static int square(int value) {
        return value * value;
    }

    public static long square(long value) {
        return value * value;
    }

    public static double clampedMap(double v1, double value, double v2, double min1, double max) {
        return clampedLerp(min1, max, inverseLerp(v1, value, v2));
    }

    public static float clampedMap(float v1, float value, float v2, float min1, float max) {
        return clampedLerp(min1, max, inverseLerp(v1, value, v2));
    }

    public static double map(double v1, double value, double v2, double x, double y) {
        return lerp(inverseLerp(v1, value, v2), x, y);
    }

    public static float map(float v1, float value, float v2, float x, float y) {
        return lerp(inverseLerp(v1, value, v2), x, y);
    }

    public static int roundToward(int x, int y) {
        return positiveCeilDiv(x, y) * y;
    }

    public static int positiveCeilDiv(int x, int y) {
        return -Math.floorDiv(-x, y);
    }

    public static double lengthSquared(double x, double y) {
        return x * x + y * y;
    }

    public static double length(double x, double y) {
        return Math.sqrt(lengthSquared(x, y));
    }

    public static double lengthSquared(double x, double y, double z) {
        return x * x + y * y + z * z;
    }

    public static double length(double x, double y, double z) {
        return Math.sqrt(lengthSquared(x, y, z));
    }

    public static int quantize(double value1, int value2) {
        return floor(value1 / (double) value2) * value2;
    }

    public static IntStream outFromOrigin(int p_216296_, int p_216297_, int p_216298_) {
        return outFromOrigin(p_216296_, p_216297_, p_216298_, 1);
    }

    public static IntStream outFromOrigin(int value1, int value2, int value3, int value4) {
        if (value2 > value3) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "upperbound %d expected to be > lowerBound %d", value3, value2));
        } else if (value4 < 1) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "steps expected to be >= 1, was %d", value4));
        } else {
            return value1 >= value2 && value1 <= value3 ? IntStream.iterate(value1, (p_216282_) -> {
                int i = Math.abs(value1 - p_216282_);
                return value1 - i >= value2 || value1 + i <= value3;
            }, (p_216260_) -> {
                boolean flag = p_216260_ <= value1;
                int i = Math.abs(value1 - p_216260_);
                boolean flag1 = value1 + i + value4 <= value3;
                if (!flag || !flag1) {
                    int j = value1 - i - (flag ? value4 : 0);
                    if (j >= value2) {
                        return j;
                    }
                }

                return value1 + i + value4;
            }) : IntStream.empty();
        }
    }

    private static <T> T make(Supplier<T> tSupplier) {
        return tSupplier.get();
    }

    private static <T> T make(T t, Consumer<T> tConsumer) {
        tConsumer.accept(t);
        return t;
    }

    @SuppressWarnings({SuppressString.UNCHECKED})
    @NotNull
    public static <T extends Number> T compareMax(T t1, T t2) {
        if (!t1.getClass().equals(t2.getClass()))
            throw new ClassNotMatchException(String.format("Two parameter's class is not same!. 1st: %s, 2nd: %s", t1.getClass().getName(), t2.getClass().getName()));
        if (t1 instanceof Long) return (T) (Long) Math.max((Long) t1, (Long) t2);
        if (t1 instanceof Integer) return (T) (Integer) Math.max(((Integer) t1), (Integer) t2);
        if (t1 instanceof Float) return (T) (Float) Math.max((Float) t1, (Float) t2);
        if (t1 instanceof Double) return (T) (Double) Math.max((Double) t1, (Double) t2);
        return t1.doubleValue() > t2.doubleValue() ? t1 : t2;
    }
    @SuppressWarnings({SuppressString.UNCHECKED})
    @NotNull
    public static <T extends Number> T compareMin(T t1, T t2) {
        if (!t1.getClass().equals(t2.getClass()))
            throw new ClassNotMatchException(String.format("Two parameter's class is not same!. 1st: %s, 2nd: %s", t1.getClass().getName(), t2.getClass().getName()));
        if (t1 instanceof Long) return (T) (Long) Math.max((Long) t1, (Long) t2);
        if (t1 instanceof Integer) return (T) (Integer) Math.max(((Integer) t1), (Integer) t2);
        if (t1 instanceof Float) return (T) (Float) Math.max((Float) t1, (Float) t2);
        if (t1 instanceof Double) return (T) (Double) Math.max((Double) t1, (Double) t2);
        return t1.doubleValue() < t2.doubleValue() ? t1 : t2;
    }

    private interface ABGR {
        static int alpha(int a) {
        return a >>> 24;
    }
    }

    private static class ABGR32 implements ABGR {


        public static int red(int r) {
            return r & 255;
        }

        public static int green(int g) {
            return g >> 8 & 255;
        }

        public static int blue(int b) {
            return b >> 16 & 255;
        }

        public static int transparent(int t) {
            return t & 16777215;
        }

        public static int opaque(int o) {
            return o | -16777216;
        }

        public static int color(int r, int g, int b, int a) {
            return r << 24 | g << 16 | b << 8 | a;
        }

        public static int color(int x, int y) {
            return x << 24 | y & 16777215;
        }
    }

    private static class ARGB32 implements ABGR {
        public static int red(int r) {
            return r >> 16 & 255;
        }

        public static int green(int g) {
            return g >> 8 & 255;
        }

        public static int blue(int b) {
            return b & 255;
        }

        public static int color(int r, int g, int b, int a) {
            return r << 24 | g << 16 | b << 8 | a;
        }

        public static int multiply(int m1, int m2) {
            return color(ABGR.alpha(m1) * ABGR.alpha(m2) / 255, red(m1) * red(m2) / 255, green(m1) * green(m2) / 255, blue(m1) * blue(m2) / 255);
        }

        public static int lerp(float min, int value1, int value2) {
            int i = Mth.lerpInt(min, ABGR.alpha(value1), ABGR.alpha(value2));
            int j = Mth.lerpInt(min, red(value1), red(value2));
            int k = Mth.lerpInt(min, green(value1), green(value2));
            int l = Mth.lerpInt(min, blue(value1), blue(value2));
            return color(i, j, k, l);
        }
    }
}
