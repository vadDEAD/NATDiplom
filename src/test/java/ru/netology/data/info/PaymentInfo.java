package ru.netology.data.info;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PaymentInfo {
    private String id;
    private String amount;
    private String created;
    private String status;
    private String transaction_id;
}


