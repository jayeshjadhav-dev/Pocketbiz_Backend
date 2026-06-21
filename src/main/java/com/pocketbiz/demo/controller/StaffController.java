package com.pocketbiz.demo.controller;

import com.pocketbiz.demo.entity.Staff;
import com.pocketbiz.demo.entity.Vendor;
import com.pocketbiz.demo.repository.StaffRepository;
import com.pocketbiz.demo.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
@CrossOrigin(origins = "http://localhost:5173")
public class StaffController {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private VendorRepository vendorRepository;

    // 1. GET ALL STAFF
    @GetMapping("/vendor/{vendorId}")
    public List<Staff> getStaffByVendor(@PathVariable Long vendorId) {
        return staffRepository.findByVendorId(vendorId);
    }

    // 2. ADD STAFF (With Plan Check)
    @PostMapping("/vendor/{vendorId}")
    public Staff addStaff(@PathVariable Long vendorId, @RequestBody Staff staff) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        // CHECK LIMITS
        // If plan is BASIC (canAddStaff = false), block request
        if (!vendor.getTier().canAddStaff) {
            throw new RuntimeException("Your plan does not support adding staff. Upgrade to Gold.");
        }

        staff.setVendor(vendor);
        // Generate a cool avatar based on name if no photo provided
        if(staff.getPhotoUrl() == null || staff.getPhotoUrl().isEmpty()) {
            staff.setPhotoUrl("https://ui-avatars.com/api/?name=" + staff.getFullName().replace(" ", "+") + "&background=random");
        }

        return staffRepository.save(staff);
    }

    // 3. EDIT STAFF
    @PutMapping("/{id}")
    public Staff updateStaff(@PathVariable Long id, @RequestBody Staff updatedDetails) {
        Staff staff = staffRepository.findById(id).orElseThrow(() -> new RuntimeException("Staff not found"));

        staff.setFullName(updatedDetails.getFullName());
        staff.setMobile(updatedDetails.getMobile());
        staff.setRole(updatedDetails.getRole());
        staff.setSalary(updatedDetails.getSalary());

        return staffRepository.save(staff);
    }

    // 4. DELETE STAFF
    @DeleteMapping("/{id}")
    public String deleteStaff(@PathVariable Long id) {
        staffRepository.deleteById(id);
        return "Staff removed";
    }
}