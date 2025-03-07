package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaymentServiceImplTest {
    private PaymentRepository paymentRepository;
    private PaymentServiceImpl paymentService;

    @BeforeEach
    void setUp() {
        paymentRepository = mock(PaymentRepository.class);
        paymentService = new PaymentServiceImpl(paymentRepository);
    }

    @Test
    void testAddPaymentByValidVoucherCode() {
        Order order = new Order("order-123", "pending");
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP12345678XYZ");

        Payment payment = paymentService.addPayment(order, PaymentMethod.BY_VOUCHER, paymentData);

        assertEquals(PaymentStatus.SUCCESS, payment.getStatus());
        assertEquals(PaymentMethod.BY_VOUCHER, payment.getMethod());
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testAddPaymentByInvalidVoucherCode() {
        Order order = new Order("order-456", "pending");
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "INVALIDCODE123");

        Payment payment = paymentService.addPayment(order, PaymentMethod.BY_VOUCHER, paymentData);

        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testSetPaymentStatusToSuccess() {
        Payment payment = new Payment("payment-789", PaymentMethod.BY_TRANSFER, PaymentStatus.REJECTED, new HashMap<>());
        Order order = new Order("order-789", "pending");
        payment.setOrder(order);

        paymentService.setStatus(payment, PaymentStatus.SUCCESS);

        assertEquals(PaymentStatus.SUCCESS, payment.getStatus());
        assertEquals("SUCCESS", order.getStatus());
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testFindPaymentById() {
        Payment payment = new Payment("payment-101", PaymentMethod.BY_TRANSFER, PaymentStatus.SUCCESS, new HashMap<>());
        when(paymentRepository.findById("payment-101")).thenReturn(payment);

        Payment foundPayment = paymentService.getPayment("payment-101");

        assertNotNull(foundPayment);
        assertEquals("payment-101", foundPayment.getId());
        verify(paymentRepository, times(1)).findById("payment-101");
    }
}
