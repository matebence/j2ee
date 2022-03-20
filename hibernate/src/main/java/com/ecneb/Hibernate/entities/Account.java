package com.ecneb.Hibernate.entities;

import lombok.Setter;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;
import javax.persistence.Temporal;
import javax.persistence.TableGenerator;
import javax.persistence.TemporalType;
import javax.persistence.CascadeType;
import javax.persistence.GenerationType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ACCOUNT")
public class Account {

    @Id
    @Getter
    @Setter
    @Column(name = "ACCOUNT_ID")
    @GeneratedValue(strategy=GenerationType.TABLE, generator="account_table_generator")
    @TableGenerator(name = "account_table_generator", table="IFINACES_KEYS", pkColumnName = "PK_NAME", valueColumnName="PK_VALUE")
    private Long accountId;

    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ACCOUNT_ID")
    //if we set this to nullable false then on the transaction side we have set insertable and updatable to false
    private List<Transaction> transactions = new ArrayList<>();

    @Getter
    @Setter
    @Column(name = "NAME")
    private String name;

    @Getter
    @Setter
    @Column(name = "INITIAL_BALANCE")
    private BigDecimal initialBalance;

    @Getter
    @Setter
    @Column(name = "CURRENT_BALANCE")
    private BigDecimal currentBalance;

    @Getter
    @Setter
    @Column(name = "OPEN_DATE")
    private Date openDate;

    @Getter
    @Setter
    @Column(name = "CLOSE_DATE")
    private Date closeDate;

    @Getter
    @Setter
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_UPDATED_DATE")
    private Date lastUpdatedDate;

    @Getter
    @Setter
    @Column(name = "LAST_UPDATED_BY")
    private String lastUpdatedBy;

    @Getter
    @Setter
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Getter
    @Setter
    @Column(name = "CREATED_BY")
    private String createdBy;

    @Getter
    @Setter
    @Column(name="DIFF")
    @Formula("INITIAL_BALANCE - CURRENT_BALANCE")
    private BigDecimal diff;

    @Getter
    @Setter
    @Transient
    private boolean valid;
}
