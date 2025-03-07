package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

public class PaymentTest {

    private Payment payment;
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");  // Valid voucher code example
        // Use allowed values: method must be "by-voucher" and status must be "SUCCESS" or "REJECTED"
        payment = new Payment("p1", "by-voucher", "SUCCESS", paymentData);
    }

    @Test
    void testPaymentCreation() {
        assertEquals("p1", payment.getId());
        assertEquals("by-voucher", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertNotNull(payment.getPaymentData());
        assertEquals("ESHOP1234ABC5678", payment.getPaymentData().get("voucherCode"));
    }

    @Test
    void testSetAndGetStatus() {
        payment.setStatus("REJECTED");
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testSetPaymentData() {
        Map<String, String> newData = new HashMap<>();
        newData.put("bankName", "Bank XYZ");
        newData.put("referenceCode", "REF123456");
        payment.setPaymentData(newData);
        assertEquals("Bank XYZ", payment.getPaymentData().get("bankName"));
        assertEquals("REF123456", payment.getPaymentData().get("referenceCode"));
    }

    @Test
    void testInvalidStatusThrowsException() {
        // "PENDING" is not allowed as a status based on our implementation.
        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("PENDING"));
    }

    @Test
    void testInvalidMethodThrowsException() {
        // "CashOnDelivery" is not allowed as a method based on our implementation.
        assertThrows(IllegalArgumentException.class, () -> payment.setMethod("CashOnDelivery"));
    }
}