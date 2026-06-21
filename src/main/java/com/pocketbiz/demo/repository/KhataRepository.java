package com.pocketbiz.demo.repository;

import com.pocketbiz.demo.entity.KhataTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface KhataRepository extends JpaRepository<KhataTransaction, Long> {
    // Find all transactions for a specific vendor, ordered by newest first
    List<KhataTransaction> findByVendorIdOrderByDateDesc(Long vendorId);
}