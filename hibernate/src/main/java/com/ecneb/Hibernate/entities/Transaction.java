package com.ecneb.Hibernate.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Setter;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.GenerationType;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TRANSACTION")
public class Transaction {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSACTION_ID")
    private Long transactionId;

    @Getter
    @Setter
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="ACCOUNT_ID")
    private Account account;

    @Getter
    @Setter
    @Column(name = "TRANSACTION_TYPE")
    private String transactionType;

    @Getter
    @Setter
    @Column(name = "TITLE")
    private String title;

    @Getter
    @Setter
    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Getter
    @Setter
    @Column(name = "INITIAL_BALANCE")
    private BigDecimal initialBalance;

    @Getter
    @Setter
    @Column(name = "CLOSING_BALANCE")
    private BigDecimal closingBalance;

    @Getter
    @Setter
    @Column(name = "NOTES")
    private String notes;

    @Getter
    @Setter
    @Column(name = "LAST_UPDATED_DATE")
    private Date lastUpdatedDate;

    @Getter
    @Setter
    @Column(name = "LAST_UPDATED_BY")
    private String lastUpdatedBy;

    @Getter
    @Setter
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Getter
    @Setter
    @Column(name = "CREATED_BY")
    private String createdBy;
}
