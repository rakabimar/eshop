package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter
@Setter
public class Payment {
    private String id;
    private PaymentMethod method;
    private PaymentStatus status;
    private Map<String, String> paymentData;

    // Full-argument constructor using enums.
    public Payment(String id, PaymentMethod method, PaymentStatus status, Map<String, String> paymentData) {
        this.id = id;
        this.paymentData = paymentData;
        setStatus(status);
        setMethod(method);
    }

    // No-argument constructor.
    public Payment() {}

    // Setter with validation using enums.
    public void setStatus(PaymentStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null.");
        }
        this.status = status;
    }

    public void setMethod(PaymentMethod method) {
        if (method == null) {
            throw new IllegalArgumentException("Method cannot be null.");
        }
        this.method = method;
    }
}
