package model;

import Utility.FieldValues;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "sales_audit")
@Data
public class SalesAudit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "product", insertable = false, nullable = false)
    private Integer product;

    @Column(name = "amount")
    private String amount;

    @Column(name = "date")
    private String date;

    @Column(name = "user")
    private String user;

    @Column(name = "customer")
    private String customer;

    @Column(name = "total_price")
    private String totalPrice;

    @Column(name = "unit_price")
    private String unitPrice;

    @Column(name = "action")
    private String action;

    @Column(name = "broker")
    private String broker;

    @Column(name = "broker_payed")
    private String brokerPayed;

    @Column(name = "broker_price")
    private String brokerPrice;

    @Column(name = "id")
    private Integer id;

    @OneToOne(targetEntity = User.class)
    private User lastUpdatedBy = FieldValues.currentUser();

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;
}