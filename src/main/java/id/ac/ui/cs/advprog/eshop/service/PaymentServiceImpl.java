package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment addPayment(Order order, PaymentMethod method, Map<String, String> paymentData) {
        // TODO: Validate paymentData based on method (voucher or bank transfer).
        // TODO: Determine the PaymentStatus (SUCCESS/REJECTED) based on business rules.
        // TODO: Generate a new payment id using UUID.
        // TODO: Create a new Payment object with the generated id, provided method, status, and paymentData.
        // TODO: Associate the Payment with the Order and save it using paymentRepository.
        return null;
    }

    @Override
    public Payment setStatus(Payment payment, PaymentStatus status) {
        // TODO: Update the Payment's status.
        // TODO: If status is SUCCESS or REJECTED, update the related Order's status accordingly.
        // TODO: Save and return the updated Payment using paymentRepository.
        return null;
    }

    @Override
    public Payment getPayment(String paymentId) {
        // TODO: Retrieve and return the Payment by its id from paymentRepository.
        return null;
    }

    @Override
    public List<Payment> getAllPayments() {
        // TODO: Retrieve and return all Payment objects from paymentRepository.
        return null;
    }
}
