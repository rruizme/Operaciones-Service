package com.nttdata.bc.models;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private Integer accountId;
    private Integer clientId;
    private DebitCard debitCard;
    private BigDecimal amount;
    private Boolean isMain;
    private Boolean isActive;
    private Instant createdAt;
    private Instant updateddAt;
}
