package com.pocketbiz.demo.controller;

import com.pocketbiz.demo.entity.ServiceItem;
import com.pocketbiz.demo.entity.Vendor;
import com.pocketbiz.demo.repository.ServiceItemRepository;
import com.pocketbiz.demo.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.pocketbiz.demo.enums.SubscriptionTier;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = "http://localhost:5173")
public class ServiceController {

    @Autowired
    private ServiceItemRepository serviceRepository;

    @Autowired
    private VendorRepository vendorRepository;

    // 1. GET: Fetch Menu for a specific vendor
    @GetMapping("/vendor/{vendorId}")
    public List<ServiceItem> getServices(@PathVariable Long vendorId) {
        return serviceRepository.findByVendorId(vendorId);
    }

    // 2. POST: Add a new item to the menu
    @PostMapping("/vendor/{vendorId}")
    public ServiceItem addService(@PathVariable Long vendorId, @RequestBody ServiceItem item) {
        Vendor vendor = vendorRepository.findById(vendorId).orElseThrow(() -> new RuntimeException("Vendor not found"));

        // 1. CHECK LIMITS
        SubscriptionTier tier = vendor.getTier();
        int currentCount = serviceRepository.countByVendorId(vendorId);

        if (tier.maxProducts != -1 && currentCount >= tier.maxProducts) {
            throw new RuntimeException("Plan Limit Reached! Upgrade to add more.");
        }

        // 2. Save Item
        item.setVendor(vendor);
        return serviceRepository.save(item);
    }

    // ==========================================
    // 3. DELETE: Remove an item (THE FIX)
    // ==========================================
    @DeleteMapping("/{id}")
    public String deleteService(@PathVariable Long id) {
        if (serviceRepository.existsById(id)) {
            serviceRepository.deleteById(id);
            return "Item deleted successfully";
        } else {
            throw new RuntimeException("Item not found");
        }
    }
}