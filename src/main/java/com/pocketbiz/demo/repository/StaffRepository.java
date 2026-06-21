package com.pocketbiz.demo.repository;
import com.pocketbiz.demo.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    List<Staff> findByVendorId(Long vendorId);
}