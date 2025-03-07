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

import static org.junit.jupiter.api.Assertions.*;

public class PaymentRepositoryTest {
    PaymentRepository paymentRepository;
    List<Payment> payments;

    @BeforeEach
    void setUp() {
        this.paymentRepository = new PaymentRepository();
        this.payments = new ArrayList<>();

        // Use new sample data values
        Map<String, String> voucherData = new HashMap<>();
        voucherData.put("voucherCode", "ESHOPNEWCODE0001");

        Map<String, String> wrongVoucherData = new HashMap<>();
        wrongVoucherData.put("voucherCode", "WRONGCODE002");

        Map<String, String> bankData = new HashMap<>();
        bankData.put("bankName", "New Bank");
        bankData.put("referenceCode", "REF2023XYZ");

        Payment payment1 = new Payment("pay-001", PaymentMethod.BY_VOUCHER, PaymentStatus.SUCCESS, voucherData);
        Payment payment2 = new Payment("pay-002", PaymentMethod.BY_VOUCHER, PaymentStatus.REJECTED, wrongVoucherData);
        Payment payment3 = new Payment("pay-003", PaymentMethod.BY_TRANSFER, PaymentStatus.SUCCESS, bankData);

        payments.add(payment1);
        payments.add(payment2);
        payments.add(payment3);
    }

    @Test
    void testSaveFromCreate() {
        Payment payment = payments.get(0);
        Payment result = paymentRepository.save(payment);

        Payment paymentResult = paymentRepository.findById(payments.get(0).getId());
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
        // Update payment status from REJECTED to SUCCESS.
        Payment modifiedPayment = new Payment(payment.getId(), payment.getMethod(), PaymentStatus.SUCCESS, payment.getPaymentData());
        Payment result = paymentRepository.save(modifiedPayment);

        Payment paymentResult = paymentRepository.findById(payment.getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), paymentResult.getId());
        assertEquals(payment.getMethod(), paymentResult.getMethod());
        assertEquals(PaymentStatus.SUCCESS, paymentResult.getStatus());
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

        Payment paymentResult = paymentRepository.findById("non-existent-id");
        assertNull(paymentResult);
    }

    @Test
    void testFindAll() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        List<Payment> paymentList = paymentRepository.findAll();
        assertEquals(payments.size(), paymentList.size());
    }
}
