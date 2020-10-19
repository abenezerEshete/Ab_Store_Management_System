package model;

import Utility.FieldValues;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "selled")
public class Selled implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Integer id;

    @OneToOne(targetEntity = ProductType.class)
    private ProductType productType;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "total_price")
    private Double totalPrice;

    @OneToOne(targetEntity = Customer.class)
    private Customer customer;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "date")
    private LocalDateTime date;

    @OneToOne(targetEntity = User.class)
    private User selledBy;

    @Column(name = "unit_price")
    private Double unitPrice;

    @OneToOne(targetEntity = Broker.class)
    private Broker broker;

    @Column(name = "broker_price")
    private Double brokerPrice;

    @Column(name = "broker_payed")
    private boolean brokerPayed;

    @OneToOne(targetEntity = User.class)
    private User lastUpdatedBy = FieldValues.currentUser();

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;

}