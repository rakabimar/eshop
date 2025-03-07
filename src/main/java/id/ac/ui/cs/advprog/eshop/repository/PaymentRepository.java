package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class PaymentRepository {

    // In-memory storage for Payment objects.

    // Save a Payment object. If a Payment with the same id exists, update it.
    public Payment save(Payment payment) {
        // TODO: Implement saving logic.
        return null;
    }

    // Find a Payment object by its id. Returns null if not found.
    public Payment findById(String id) {
        // TODO: Implement search by id.
        return null;
    }

    // Retrieve all Payment objects.
    public List<Payment> findAll() {
        // TODO: Implement retrieval of all payments.
        return null;
    }
}
