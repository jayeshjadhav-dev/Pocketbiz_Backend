package com.pocketbiz.demo.controller;

import com.pocketbiz.demo.entity.KhataTransaction;
import com.pocketbiz.demo.repository.KhataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/khata")
@CrossOrigin(origins = "http://localhost:5173")
public class KhataController {

    @Autowired
    private KhataRepository khataRepository;

    // 1. Get All Transactions for a Vendor
    @GetMapping("/{vendorId}")
    public List<KhataTransaction> getKhata(@PathVariable Long vendorId) {
        return khataRepository.findByVendorIdOrderByDateDesc(vendorId);
    }

    // 2. Add New Entry (GAVE or GOT)
    @PostMapping("/{vendorId}")
    public KhataTransaction addEntry(@PathVariable Long vendorId, @RequestBody KhataTransaction entry) {
        entry.setVendorId(vendorId);
        return khataRepository.save(entry);
    }

    // 3. Get Summary (Total To Collect)
    @GetMapping("/{vendorId}/summary")
    public Map<String, Double> getSummary(@PathVariable Long vendorId) {
        List<KhataTransaction> all = khataRepository.findByVendorIdOrderByDateDesc(vendorId);

        double totalCreditGiven = all.stream()
                .filter(t -> "CREDIT".equals(t.getType()))
                .mapToDouble(KhataTransaction::getAmount).sum();

        double totalPaymentReceived = all.stream()
                .filter(t -> "PAYMENT".equals(t.getType()))
                .mapToDouble(KhataTransaction::getAmount).sum();

        return Map.of(
                "totalDue", totalCreditGiven - totalPaymentReceived,
                "totalGiven", totalCreditGiven,
                "totalReceived", totalPaymentReceived
        );
    }
}