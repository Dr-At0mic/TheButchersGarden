package butchersgarden.main.restaurants.microservices.tablereserve.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@jakarta.persistence.Table(name = "tables")

public class Table implements Serializable {
    @Serial
    private static final long serialVersionUID = -7190352602234793228L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tableId;
    @Column(name = "is_ac")
    private boolean isAc;
    @ManyToOne
    @JoinColumn(name = "table_type_id")
    private TableType tableType;
    @Column
    private int tableCount;
    @NotBlank(message = "tablePrice is Mandatory")
    @Column
    private String tablePrice;

}
