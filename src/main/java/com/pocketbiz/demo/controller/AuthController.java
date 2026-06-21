package com.pocketbiz.demo.controller;

import com.pocketbiz.demo.entity.Vendor;
import com.pocketbiz.demo.entity.VendorConfig;
import com.pocketbiz.demo.enums.SubscriptionTier;
import com.pocketbiz.demo.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth") // Standard endpoint
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private VendorRepository vendorRepository;

    @PostMapping("/signup")
    public Vendor signup(@RequestBody Vendor vendor) {
        if (vendorRepository.findByMobileNumber(vendor.getMobileNumber()).isPresent()) {
            throw new RuntimeException("Mobile number already registered");
        }

        // 1. FORCE NEW USERS TO BASIC
        vendor.setTier(SubscriptionTier.BASIC);

        // 2. Initialize Empty Config if needed
        if (vendor.getConfig() == null) {
            VendorConfig config = new VendorConfig();
            config.setThemeColor("#2563EB");
            vendor.setConfig(config);
        }

        return vendorRepository.save(vendor);
    }

    @PostMapping("/login")
    public Vendor login(@RequestBody Vendor loginRequest) {
        return vendorRepository.findByMobileNumber(loginRequest.getMobileNumber())
                .filter(v -> v.getPassword().equals(loginRequest.getPassword()))
                .orElseThrow(() -> new RuntimeException("Invalid Credentials"));
    }
}