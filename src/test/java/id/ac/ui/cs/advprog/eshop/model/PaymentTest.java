package id.ac.ui.cs.advprog.eshop.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class PaymentTest {

    private Payment payment;
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        // Set up valid payment data; e.g., for voucher code scenario
        paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");  // A valid voucher code example

        // Construct a Payment object with initial values.
        // Assuming a constructor Payment(String id, String method, String status, Map<String, String> paymentData)
        payment = new Payment("p1", "CashOnDelivery", "PENDING", paymentData);
    }

    @Test
    void testPaymentCreation() {
        // Verify that all attributes are set as expected.
        assertEquals("p1", payment.getId());
        assertEquals("CashOnDelivery", payment.getMethod());
        assertEquals("PENDING", payment.getStatus());
        assertNotNull(payment.getPaymentData());
        assertEquals("ESHOP1234ABC5678", payment.getPaymentData().get("voucherCode"));
    }

    @Test
    void testSetAndGetStatus() {
        // Test that setting the status works correctly.
        payment.setStatus("SUCCESS");
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testSetPaymentData() {
        // Test that you can update the paymentData map.
        Map<String, String> newData = new HashMap<>();
        newData.put("bankName", "Bank XYZ");
        newData.put("referenceCode", "REF123456");
        payment.setPaymentData(newData);
        assertEquals("Bank XYZ", payment.getPaymentData().get("bankName"));
        assertEquals("REF123456", payment.getPaymentData().get("referenceCode"));
    }
}
