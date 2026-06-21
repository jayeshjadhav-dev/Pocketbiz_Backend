package com.pocketbiz.demo.controller;

import com.pocketbiz.demo.entity.Plan;
import com.pocketbiz.demo.entity.Vendor;
import com.pocketbiz.demo.entity.VendorConfig;
import com.pocketbiz.demo.enums.SubscriptionTier;
import com.pocketbiz.demo.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendor")
@CrossOrigin(origins = "http://localhost:5173")
public class VendorController {

    @Autowired
    private VendorRepository vendorRepository;

    // ==========================
    // 1. AUTHENTICATION (New)
    // ==========================

    @PostMapping("/signup")
    public Vendor signup(@RequestBody Vendor vendor) {
        // 1. Check if user exists
        if (vendorRepository.findByMobileNumber(vendor.getMobileNumber()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        // 2. Set Default Tier to BASIC (Database Column)
        vendor.setTier(SubscriptionTier.BASIC);

        // 3. Create Default Config & Plan (Fixes "Plan is null" errors)
        if (vendor.getConfig() == null) {
            VendorConfig config = new VendorConfig();
            config.setThemeColor("#2563EB"); // Default Blue

            // Create the Basic Plan Object
            // Ensure you have the Plan entity created as discussed before
            Plan basicPlan = new Plan();
            basicPlan.setName("BASIC");
            basicPlan.setPrice(0.0);
            basicPlan.setDuration("Lifetime");

            config.setPlan(basicPlan); // Link Plan to Config
            vendor.setConfig(config);  // Link Config to Vendor
        }

        return vendorRepository.save(vendor);
    }

    @PostMapping("/login")
    public Vendor login(@RequestBody Vendor loginRequest) {
        return vendorRepository.findByMobileNumber(loginRequest.getMobileNumber())
                .filter(v -> v.getPassword().equals(loginRequest.getPassword()))
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }

    // ==========================
    // 2. PROFILE & SUBSCRIPTION
    // ==========================

    @GetMapping("/{mobile}")
    public Vendor getVendor(@PathVariable String mobile) {
        return vendorRepository.findByMobileNumber(mobile)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // UPDATE PROFILE (Handles Subscription Upgrades)
    @PostMapping("/update")
    public Vendor updateVendor(@RequestBody Vendor updatedData) {
        Vendor existingVendor = vendorRepository.findByMobileNumber(updatedData.getMobileNumber())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 1. Update Basic Info
        if (updatedData.getBusinessName() != null) {
            existingVendor.setBusinessName(updatedData.getBusinessName());
        }

        // 2. Update Config & SYNC SUBSCRIPTION TIER
        if (updatedData.getConfig() != null) {
            VendorConfig newConfig = updatedData.getConfig();

            // Ensure config exists
            if (existingVendor.getConfig() == null) {
                existingVendor.setConfig(newConfig);
            } else {
                VendorConfig current = existingVendor.getConfig();
                if(newConfig.getThemeColor() != null) current.setThemeColor(newConfig.getThemeColor());
                if(newConfig.getCategory() != null) current.setCategory(newConfig.getCategory());

                // === THE PERMANENT FIX ===
                // If the incoming data has a Plan, we MUST update the main 'tier' column
                if(newConfig.getPlan() != null && newConfig.getPlan().getName() != null) {
                    // Update the Config object (for frontend UI)
                    current.setPlan(newConfig.getPlan());

                    // Update the Database Column (for backend queries)
                    try {
                        String planName = newConfig.getPlan().getName(); // e.g., "PLATINUM"
                        existingVendor.setTier(SubscriptionTier.valueOf(planName));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Unknown Plan: " + newConfig.getPlan().getName());
                        // Don't crash, just keep existing tier
                    }
                }
                // =========================
            }
        }

        return vendorRepository.save(existingVendor);
    }
}