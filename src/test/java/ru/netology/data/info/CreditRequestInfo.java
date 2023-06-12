package ru.netology.data.info;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreditRequestInfo {
    private String id;
    private String bank_id;
    private String created;
    private String status;
}

