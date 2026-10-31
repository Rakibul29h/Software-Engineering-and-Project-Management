package com.mediflow.Backend.entity;


import com.mediflow.Backend.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;
import tools.jackson.databind.JsonNode;

import java.math.BigDecimal;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status = PaymentStatus.UNPAID;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private String currency = "BDT";

    @Column(nullable = false)
    private String paymentGateway = "bKash";

    @Column(nullable = false, unique = true)
    private String merchantInvoiceNumber;

    @Column(unique = true)
    private String bkashPaymentId;

    private String bkashTransactionId;
    private String payerReference;

    private String paidAt;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private JsonNode gatewayResponse;

    private String refundTransactionId;

    @Column(precision = 10, scale = 2)
    private BigDecimal refundAmount;

    private String refundReason;

    private String refundedat;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "appointmentId",
            nullable = false,
            unique = true
    )
    private Appointment appointment;
}