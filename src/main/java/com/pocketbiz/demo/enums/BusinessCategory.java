package com.pocketbiz.demo.enums;

public enum BusinessCategory {

    // --- 1. NEW CATEGORIES (Required to fix the error) ---
    RETAIL("Order", "Customer", false, true),
    DOCTOR("Appointment", "Patient", false, false),
    GYM("Membership", "Member", false, false),
    FREELANCE("Project", "Client", false, false),

    // --- 2. OLD CATEGORIES (Keep these too) ---
    HANDYMAN("Job", "Client", true, false),
    RETAIL_SHOP("Order", "Customer", false, true),
    CONSULTANT("Session", "Student", false, false);

    // Fields
    public final String orderTerm;
    public final String customerTerm;
    public final boolean needsCamera;
    public final boolean needsInventory;

    BusinessCategory(String orderTerm, String customerTerm, boolean needsCamera, boolean needsInventory) {
        this.orderTerm = orderTerm;
        this.customerTerm = customerTerm;
        this.needsCamera = needsCamera;
        this.needsInventory = needsInventory;
    }
}