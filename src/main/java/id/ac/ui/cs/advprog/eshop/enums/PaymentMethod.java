package id.ac.ui.cs.advprog.eshop.enums;

public enum PaymentMethod {
    BY_VOUCHER("by-voucher"),
    BY_TRANSFER("by-transfer");

    private final String method;

    PaymentMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}
