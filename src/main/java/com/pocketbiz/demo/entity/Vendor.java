package com.pocketbiz.demo.entity;

import com.pocketbiz.demo.enums.BusinessCategory;
import com.pocketbiz.demo.enums.SubscriptionTier;
import jakarta.persistence.*;

@Entity
@Table(name = "vendors") // Postgres Table Name
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String businessName;

    @Column(unique = true, nullable = false)
    private String mobileNumber;

    @Column(nullable = false)
    private String password;

    // --- THE CRITICAL FIELD ---
    // Stores "BASIC", "GOLD", "PLATINUM" in Postgres (not 0, 1, 2)
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20) DEFAULT 'BASIC'")
    private SubscriptionTier tier = SubscriptionTier.BASIC;
    // --------------------------

    @Enumerated(EnumType.STRING)
    private BusinessCategory category;

    // Use CascadeType.ALL so saving Vendor also saves Config
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "config_id", referencedColumnName = "id")
    private VendorConfig config;

    // Constructors
    public Vendor() {}

    public Vendor(String businessName, String mobileNumber, String password, SubscriptionTier tier) {
        this.businessName = businessName;
        this.mobileNumber = mobileNumber;
        this.password = password;
        this.tier = tier;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBusinessName() { return businessName; }
    public void setBusinessName(String businessName) { this.businessName = businessName; }
    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public SubscriptionTier getTier() { return tier; }
    public void setTier(SubscriptionTier tier) { this.tier = tier; }

    public BusinessCategory getCategory() { return category; }
    public void setCategory(BusinessCategory category) { this.category = category; }

    public VendorConfig getConfig() { return config; }
    public void setConfig(VendorConfig config) { this.config = config; }
}