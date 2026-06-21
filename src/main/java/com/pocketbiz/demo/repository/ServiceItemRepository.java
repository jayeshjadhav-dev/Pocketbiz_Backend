package com.pocketbiz.demo.repository;

import com.pocketbiz.demo.entity.ServiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServiceItemRepository extends JpaRepository<ServiceItem, Long> {
    // Get all services for a specific vendor
    List<ServiceItem> findByVendorId(Long vendorId);
    // Inside the interface
    int countByVendorId(Long vendorId);
}