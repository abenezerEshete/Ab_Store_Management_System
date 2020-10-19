package model;

import Utility.FieldValues;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Table(name = "purchase")
@Entity
public class Purchase implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Integer id;

    @OneToOne(targetEntity = ProductType.class)
    private ProductType productType;

    @Column(name = "amount")
    private double amount;

    @Column(name = "unit_cost_price")
    private double unitCostPrice;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "date")
    private LocalDateTime date = LocalDateTime.now();

    @OneToOne(targetEntity = Supplier.class)
    private Supplier supplier;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @OneToOne(targetEntity = User.class)
    private User purchasedBy = FieldValues.currentUser();

    @OneToOne(targetEntity = User.class)
    private User lastUpdatedBy = FieldValues.currentUser();

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;
}