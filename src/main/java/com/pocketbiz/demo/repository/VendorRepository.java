package com.pocketbiz.demo.repository;

import com.pocketbiz.demo.entity.Vendor;
import com.pocketbiz.demo.enums.SubscriptionTier;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

    // For Login
    Optional<Vendor> findByMobileNumber(String mobileNumber);

    // For Community (Find all PLATINUM users)
    List<Vendor> findByTier(SubscriptionTier tier);
}