package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PaymentTest {
    private Map<String,String> paymentData;
    @BeforeEach
    void setUp() {
        this.paymentData = new HashMap<String,String>();
    }
    @Test
    void testCreatePaymentInvalidMethod() {
        paymentData.put("ewallet", "089999999999");
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment(
                    "a1234567-b89c-40de-fghi-123456789abc",
                    "by-ewallet",
                    PaymentStatus.SUCCESS.getValue(),
                    paymentData);
        });
    }

    @Test
    void testCreatePaymentValidStatus() {
        paymentData.put("voucherCode", "ESHOP12345xyz789");
        Payment payment = new Payment(
                "a1234567-b89c-40de-fghi-123456789abc",
                PaymentMethod.BY_VOUCHER.getValue(),
                PaymentStatus.SUCCESS.getValue(),
                paymentData
        );
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentInvalidStatus() {
        paymentData.put("voucherCode", "ESHOP12345xyz789");
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment(
                    "a1234567-b89c-40de-fghi-123456789abc",
                    PaymentMethod.BY_VOUCHER.getValue(),
                    "INVALID_STATUS",
                    paymentData
            );
        });
    }

    @Test
    void testCreatePaymentValidMethod() {
        paymentData.put("voucherCode", "ESHOP12345xyz789");
        Payment payment = new Payment(
                "a1234567-b89c-40de-fghi-123456789abc",
                PaymentMethod.BY_VOUCHER.getValue(),
                PaymentStatus.SUCCESS.getValue(),
                paymentData
        );
        assertEquals(PaymentMethod.BY_VOUCHER.getValue(), payment.getMethod());
    }

    @Test
    void testSetStatusInvalid() {
        paymentData.put("voucherCode", "ESHOP12345xyz789");
        Payment payment = new Payment(
                "a1234567-b89c-40de-fghi-123456789abc",
                PaymentMethod.BY_VOUCHER.getValue(),
                PaymentStatus.SUCCESS.getValue(),
                paymentData
        );
        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("UNKNOWN_STATUS"));
    }

    @Test
    void testSetStatusToRejected() {
        paymentData.put("voucherCode", "ESHOP12345xyz789");
        Payment payment = new Payment(
                "a1234567-b89c-40de-fghi-123456789abc",
                PaymentMethod.BY_VOUCHER.getValue(),
                PaymentStatus.SUCCESS.getValue(),
                paymentData
        );
        payment.setStatus(PaymentStatus.REJECTED.getValue());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreateAllValidArguments() {
        paymentData.put("voucherCode", "ESHOP12345xyz789");
        Payment payment = new Payment(
                "a1234567-b89c-40de-fghi-123456789abc",
                PaymentMethod.BY_VOUCHER.getValue(),
                PaymentStatus.SUCCESS.getValue(),
                paymentData
        );
        assertEquals("a1234567-b89c-40de-fghi-123456789abc", payment.getId());
        assertEquals(PaymentMethod.BY_VOUCHER.getValue(), payment.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        assertEquals(paymentData, payment.getPaymentData());
    }
}
