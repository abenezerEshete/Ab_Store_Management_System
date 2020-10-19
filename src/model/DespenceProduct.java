package model;

import Utility.FieldValues;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "despence_product")
@Data
public class DespenceProduct implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Integer id;

    @OneToOne(targetEntity = ProductType.class)
    private ProductType productType;

    @Column(name = "amount")
    private double amount;

    @OneToOne(targetEntity = User.class)
    private User lastUpdatedBy = FieldValues.currentUser();

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;
}