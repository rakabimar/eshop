package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PaymentRepositoryTest {
    PaymentRepository paymentRepository;
    List<Payment> payments;

    @BeforeEach
    void setUp() {
        this.paymentRepository = new PaymentRepository();
        this.payments = new ArrayList<>();
        Map<String, String> validVoucherData = new HashMap<>();
        validVoucherData.put("voucherCode", "SHOP123XYZ");

        Map<String, String> invalidVoucherData = new HashMap<>();
        invalidVoucherData.put("voucherCode", "INVALID_VOUCHER_CODE_999");

        Map<String, String> validBankData = new HashMap<>();
        validBankData.put("bankName", "Bank CyberSafe");
        validBankData.put("referenceCode", "REF987654321");

        Payment payment1 = new Payment("pmt-001", PaymentMethod.BY_VOUCHER.getValue(), PaymentStatus.SUCCESS.getValue(), validVoucherData);
        Payment payment2 = new Payment("pmt-002", PaymentMethod.BY_VOUCHER.getValue(), PaymentStatus.REJECTED.getValue(), invalidVoucherData);
        Payment payment3 = new Payment("pmt-003", PaymentMethod.BY_TRANSFER.getValue(), PaymentStatus.SUCCESS.getValue(), validBankData);

        payments.add(payment1);
        payments.add(payment2);
        payments.add(payment3);
    }

    @Test
    void testSaveFromCreate() {
        Payment payment = payments.getFirst();
        Payment result = paymentRepository.save(payment);

        Payment paymentResult = paymentRepository.findById(payments.getFirst().getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), paymentResult.getId());
        assertEquals(payment.getMethod(), paymentResult.getMethod());
        assertEquals(payment.getStatus(), paymentResult.getStatus());
        assertEquals(payment.getPaymentData(), paymentResult.getPaymentData());
    }

    @Test
    void testSaveFromEdit() {
        Payment payment = payments.get(1);
        paymentRepository.save(payment);
        Payment modifiedPayment = new Payment(payment.getId(), payment.getMethod(), PaymentStatus.SUCCESS.getValue(), payment.getPaymentData());
        Payment result = paymentRepository.save(modifiedPayment);

        Payment paymentResult = paymentRepository.findById(payments.get(1).getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), paymentResult.getId());
        assertEquals(payment.getMethod(), paymentResult.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), paymentResult.getStatus());
        assertEquals(payment.getPaymentData(), paymentResult.getPaymentData());
    }

    @Test
    void testFindByValidId() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        Payment paymentResult = paymentRepository.findById(payments.get(2).getId());
        assertEquals(payments.get(2).getId(), paymentResult.getId());
        assertEquals(payments.get(2).getMethod(), paymentResult.getMethod());
        assertEquals(payments.get(2).getStatus(), paymentResult.getStatus());
        assertEquals(payments.get(2).getPaymentData(), paymentResult.getPaymentData());
    }

    @Test
    void testFindByNonexistentID() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        Payment paymentResult = paymentRepository.findById("nonexistent-123");
        assertNull(paymentResult);
    }

    @Test
    void testFindAll() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        List<Payment> paymentList = paymentRepository.findAll();
        assertEquals(paymentList.size(), payments.size());
    }
}
