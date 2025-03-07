package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;

import java.util.List;
import java.util.Map;

public interface PaymentService {
    // Creates a new Payment for the given Order using the provided method and payment data.
    Payment addPayment(Order order, PaymentMethod method, Map<String, String> paymentData);

    // Sets the status of the given Payment; if status is SUCCESS or REJECTED, the related Order’s status is updated.
    Payment setStatus(Payment payment, PaymentStatus status);

    // Retrieves a Payment by its id.
    Payment getPayment(String paymentId);

    // Returns all Payment objects.
    List<Payment> getAllPayments();
}
