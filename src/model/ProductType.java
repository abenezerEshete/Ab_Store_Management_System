package model;

import Utility.FieldValues;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "product_type")
@Data
@Entity
public class ProductType implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "grade")
    private String grade;

    @Column(name = "model")
    private String model;

    @Column(name = "size")
    private String size;

    @Column(name = "brand")
    private String brand;

    @Column(name = "selling_price")
    private Double sellingPrice;

    @Column(name = "selling_measurment")
    private String sellingMeasurment;

    @Column(name = "selling_amount_ratio")
    private double sellingAmountRatio;

    @Column(name = "measure_price")
    private double measurePrice;

    @Column(name = "has_broker")
    private boolean hasBroker;

    @OneToOne(targetEntity = User.class)
    private User lastUpdatedBy = FieldValues.currentUser();

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;
}