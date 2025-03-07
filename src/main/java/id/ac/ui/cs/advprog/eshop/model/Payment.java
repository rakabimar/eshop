package id.ac.ui.cs.advprog.eshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;
}
