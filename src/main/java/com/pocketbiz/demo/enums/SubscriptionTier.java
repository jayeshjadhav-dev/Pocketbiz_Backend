package com.pocketbiz.demo.enums;

public enum SubscriptionTier {
    BASIC(20, false),       // 20 Products, No Staff
    GOLD(100, true),        // 100 Products, Can Add Staff
    PLATINUM(1000, true);   // 1000 Products, Can Add Staff

    public final int maxProducts;
    public final boolean canAddStaff;

    SubscriptionTier(int maxProducts, boolean canAddStaff) {
        this.maxProducts = maxProducts;
        this.canAddStaff = canAddStaff;
    }
}