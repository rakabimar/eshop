package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Map;

@Getter
@Setter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;

    // Full-argument constructor with validation for status and method.
    public Payment(String id, String method, String status, Map<String, String> paymentData) {
        this.id = id;
        this.paymentData = paymentData;
        setStatus(status);
        setMethod(method);
    }

    // Optional no-argument constructor.
    public Payment() {
    }

    // Validates that status is either "SUCCESS" or "REJECTED"
    public void setStatus(String status) {
        String[] validStatuses = {"SUCCESS", "REJECTED"};
        if (Arrays.stream(validStatuses).noneMatch(s -> s.equals(status))) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
        this.status = status;
    }

    // Validates that method is either "by-voucher" or "by-transfer"
    public void setMethod(String method) {
        String[] validMethods = {"by-voucher", "by-transfer"};
        if (Arrays.stream(validMethods).noneMatch(m -> m.equals(method))) {
            throw new IllegalArgumentException("Invalid method: " + method);
        }
        this.method = method;
    }
}
