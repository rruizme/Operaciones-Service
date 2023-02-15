package com.nttdata.bc.models;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Operations")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operationId")
    private Integer operationId;

    @Column(name = "accountId")
    private Integer accountId;

    @Column(name = "debitCardId")
    private Integer debitCardId;

    @Column(name = "creditCardId")
    private Integer creditCardId;

    @Column(name = "creditId")
    private Integer creditId;

    @NotEmpty(message = "El campo operationType es requerido.")
    @Pattern(regexp = "^DE$|^RE$|^TR$|^PT$|^PC$", message = "El campo operationType debe tener uno de estos valores: [DE, RE, TR, PT, PC].")
    @Column(name = "operationType", nullable = false, length = 2)
    private String operationType;

    @DecimalMin(value = "1.0", message = "El campo amount debe tener un valor mínimo de '1.0'.")
    @Digits(integer = 10, fraction = 3, message = "El campo amount tiene un formato no válido (#####.000).")
    @NotNull(message = "El campo amount es requerido.")
    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "createdAt")
    private Instant createdAt;
}
