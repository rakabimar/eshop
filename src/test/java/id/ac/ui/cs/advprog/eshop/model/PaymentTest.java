package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
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
        // Set up valid payment data, for example using a voucher code.
        paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        // Create a Payment object using enums.
        payment = new Payment("p1", PaymentMethod.BY_VOUCHER, PaymentStatus.SUCCESS, paymentData);
    }

    @Test
    void testPaymentCreation() {
        // Verify that the Payment object is created correctly.
        assertEquals("p1", payment.getId());
        assertEquals(PaymentMethod.BY_VOUCHER, payment.getMethod());
        assertEquals(PaymentStatus.SUCCESS, payment.getStatus());
        assertNotNull(payment.getPaymentData());
        assertEquals("ESHOP1234ABC5678", payment.getPaymentData().get("voucherCode"));
    }

    @Test
    void testSetAndGetStatus() {
        // Change status to REJECTED and verify.
        payment.setStatus(PaymentStatus.REJECTED);
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

    @Test
    void testSetPaymentData() {
        // Update the paymentData map and verify.
        Map<String, String> newData = new HashMap<>();
        newData.put("bankName", "Bank XYZ");
        newData.put("referenceCode", "REF123456");
        payment.setPaymentData(newData);
        assertEquals("Bank XYZ", payment.getPaymentData().get("bankName"));
        assertEquals("REF123456", payment.getPaymentData().get("referenceCode"));
    }

    @Test
    void testSetInvalidStatusThrowsException() {
        // Passing null should throw an exception.
        Exception exception = assertThrows(IllegalArgumentException.class, () -> payment.setStatus(null));
        assertTrue(exception.getMessage().contains("Status cannot be null"));
    }

    @Test
    void testSetInvalidMethodThrowsException() {
        // Passing null should throw an exception.
        Exception exception = assertThrows(IllegalArgumentException.class, () -> payment.setMethod(null));
        assertTrue(exception.getMessage().contains("Method cannot be null"));
    }
}
