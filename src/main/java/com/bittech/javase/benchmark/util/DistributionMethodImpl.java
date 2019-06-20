package com.bittech.javase.benchmark.util;

public class DistributionMethodImpl {
    private static final double percent = 0.02;

    public static DistributionMethod getRandom() {
        return new DistributionMethod() {
            @Override
            public String name() {
                return "随机分布";
            }

            public int[] build(int length) {
                return DistributionUtil.buildRandom(length);
            }
        };
    }

    public static DistributionMethod getEquivalent() {
        return new DistributionMethod() {
            @Override
            public String name() {
                return "全部相等";
            }

            public int[] build(int length) {
                return DistributionUtil.buildEquivalent(length);
            }
        };
    }

    public static DistributionMethod getNearlyEquivalent() {
        return new DistributionMethod() {
            @Override
            public String name() {
                return "基本全部相等";
            }

            public int[] build(int length) {
                return DistributionUtil.buildNearlyEquivalent(length, percent);
            }
        };
    }

    public static DistributionMethod getOrderedAscend() {
        return new DistributionMethod() {
            @Override
            public String name() {
                return "升序分布";
            }

            public int[] build(int length) {
                return DistributionUtil.buildOrderedAscend(length);
            }
        };
    }

    public static DistributionMethod getNearlyOrderedAscend() {
        return new DistributionMethod() {
            @Override
            public String name() {
                return "基本升序分布";
            }

            public int[] build(int length) {
                return DistributionUtil.buildNearlyOrderedAscend(length, percent);
            }
        };
    }

    public static DistributionMethod getOrderedDescend() {
        return new DistributionMethod() {
            @Override
            public String name() {
                return "降序分布";
            }

            public int[] build(int length) {
                return DistributionUtil.buildOrderedDescend(length);
            }
        };
    }

    public static DistributionMethod getNearlyOrderedDescend() {
        return new DistributionMethod() {
            @Override
            public String name() {
                return "基本降序分布";
            }

            public int[] build(int length) {
                return DistributionUtil.buildNearlyOrderedDescend(length, percent);
            }
        };
    }
}
